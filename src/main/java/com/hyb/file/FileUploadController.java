package com.hyb.file;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class FileUploadController {

    //设置APPID/AK/SK
    @Value("${ocr.APP_ID}")
    public static String APP_ID;
    @Value("${ocr.API_KEY}")
    public String API_KEY;
    @Value("${ocr.SECRET_KEY}")
    public String SECRET_KEY;

    @RequestMapping("/index")
    public String index() {
        System.out.println(APP_ID);
        return "upload";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String fileUpload(MultipartFile file) throws IOException {

        System.err.println("文件是否为空 ： " + file.isEmpty());
        System.err.println("文件的大小为 ：" + file.getSize());
        System.err.println("文件的媒体类型为 ： " + file.getContentType());
        System.err.println("文件的名字： " + file.getName());
        System.err.println("文件的originName为： " + file.getOriginalFilename());

        byte[] bytes = file.getBytes();

        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");//中英文混合
        options.put("detect_direction", "true");//检测图像朝向
        options.put("detect_language", "true");//检测语言
        options.put("probability", "true");//返回识别结果中每一行的置信度

        JSONObject res = client.basicGeneral(bytes, options);

        //ModelAndView mv = new ModelAndView();
        //mv.setViewName("upload");
        //mv.addObject("key", res.toString(2));

        System.out.println(res.toString(2));
        return res.toString();
    }
}
