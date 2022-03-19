package com.hyb;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

//  https://ai.baidu.com/ai-doc/OCR/vkibizxjr
@SpringBootTest
class OcrApplicationTests {

    //设置APPID/AK/SK
    public static final String APP_ID = "25750682";
    public static final String API_KEY = "TPEbBaMi35VAbal27KYHqnGQ";
    public static final String SECRET_KEY = "3CHDc29B9QkMhDMfibwogdaaPfcyvv6f";

    @Test
    void contextLoads() throws JSONException {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "log4j.properties");

        // 调用接口
        String path = "src/test.jpg";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));
    }

    @Test
    void contextLoads1() throws JSONException {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");//中英文混合
        options.put("detect_direction", "true");//是否检测图像朝向，默认不检测，即：false
        options.put("detect_language", "true");//是否检测语言，默认不检测
        options.put("probability", "true");//是否返回识别结果中每一行的置信度

        // 调用接口
        // 参数为本地图片路径
        //String path = "src/深入理解java虚拟机.png";
        String path = "src/test.jpg";
        JSONObject res = client.basicGeneral(path, options);
        System.out.println(res.toString(2));
/*返回数据详情
{
  "words_result": [
    {
      "probability": {    行置信度信息；如果输入参数 probability = true 则输出
        "average": 0.9589245915,   行置信度平均值
        "min": 0.6153715253,       行置信度最小值
        "variance": 0.005784611683  行置信度方差
      },
      "words": "先帝创小业未半而中道崩殂，今天下三分，益州疲弊，此诚危急存亡之秋也。然侍卫之臣不"
    },
    。。。。。。
    {
      "probability": {
        "average": 0.9557970166,
        "min": 0.5947368741,
        "variance": 0.01095876936
      },
      "words": "为营中之事，悉以咨之，必能使行阵和睦，优劣得所。"
    }
  ],
  "log_id": 1502221663801772396, 唯一的log id，用于问题定位
  "words_result_num": 9,   	识别结果数，表示words_result的元素个数
  "language": 3,
  "direction": 0    - 0:正向
}
 */
    }
    @Test
    void contextLoads2(){
        String a = "hello";
        String b = new String("hello");
        String c = new String("hello");
        String d = b.intern();
        String e = c.intern();
        System.out.println(a == b);
        System.out.println(c == b);
        System.out.println(a == d);
        System.out.println(b == d);
        System.out.println(d == e);
        File file = new File("pom.xml");
        System.out.println(file.getAbsolutePath());
    }

}
