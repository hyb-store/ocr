[java使用百度文字识别服务](https://ai.baidu.com/ai-doc/OCR/vkibizxjr)

[百度通用文字识别](https://ai.baidu.com/tech/ocr/general) 获取api key，app id， sercret key

maven依赖

```xml
<dependency>
    <groupId>com.baidu.aip</groupId>
    <artifactId>java-sdk</artifactId>
    <version>${version}</version>
</dependency>
```

样例

```java
//设置APPID/AK/SK
public static final String APP_ID = "你的 App ID";
public static final String API_KEY = "你的 Api Key";
public static final String SECRET_KEY = "你的 Secret Key";

@Test
void contextLoads1() throws JSONException {
    // 初始化一个AipOcr
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(2000);//建立连接的超时时间（单位：毫秒）
    client.setSocketTimeoutInMillis(60000);//通过打开的连接传输数据的超时时间（单位：毫秒）

    // 传入可选参数调用接口
    HashMap<String, String> options = new HashMap<String, String>();
    options.put("language_type", "CHN_ENG");//中英文混合
    options.put("detect_direction", "true");//检测图像朝向
    options.put("detect_language", "true");//检测语言
    options.put("probability", "true");//返回识别结果中每一行的置信度

    // 调用接口
    // 参数为本地图片路径
    //String path = "src/深入理解java虚拟机.png";
    String path = "src/test.jpg";
    JSONObject res = client.basicGeneral(path, options);
    System.out.println(res.toString(2));
}
```

输出结果

```json
{
  "words_result": [
    {
      "probability": {              //行置信度信息；如果输入参数 probability = true 则输出
        "average": 0.9589245915,    //行置信度平均值
        "min": 0.6153715253,        //行置信度最小值
        "variance": 0.005784611683  //行置信度方差
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
  "log_id": 1502221663801772396,     //唯一的log id，用于问题定位
  "words_result_num": 9,   	         //识别结果数，表示words_result的元素个数
  "language": 3,
  "direction": 0                     //- 0:正向
}
```



配置AipOcr

| 接口                         | 说明                                                         |
| ---------------------------- | ------------------------------------------------------------ |
| setConnectionTimeoutInMillis | 建立连接的超时时间（单位：毫秒）                             |
| setSocketTimeoutInMillis     | 通过打开的连接传输数据的超时时间（单位：毫秒）               |
| setHttpProxy                 | 设置http代理服务器                                           |
| setSocketProxy               | 设置socket代理服务器 （http和socket类型代理服务器只能二选一） |

请求参数

| 参数名称         | 是否必选 | 类型   | 可选值范围                                  | 默认值  | 说明                                                         |
| ---------------- | -------- | ------ | ------------------------------------------- | ------- | ------------------------------------------------------------ |
| image            | 是       | mixed  |                                             |         | 本地图片路径或者图片二进制数据                               |
| url              | 是       | String |                                             |         | 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效 |
| language_type    | 否       | String | CHN_ENG ENG POR FRE GER ITA SPA RUS JAP KOR | CHN_ENG | 识别语言类型，默认为CHN_ENG。可选值包括： - CHN_ENG：中英文混合； - ENG：英文； - POR：葡萄牙语； - FRE：法语； - GER：德语； - ITA：意大利语； - SPA：西班牙语； - RUS：俄语； - JAP：日语； - KOR：韩语； |
| detect_direction | 否       | String | true false                                  | false   | 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括: - true：检测朝向； - false：不检测朝向。 |
| detect_language  | 否       | String | true false                                  | false   | 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语） |
| probability      | 否       | String | true false                                  |         | 是否返回识别结果中每一行的置信度                             |

返回数据

| 字段             | 必选 | 类型   | 说明                                                         |
| :--------------- | ---- | ------ | ------------------------------------------------------------ |
| direction        | 否   | number | 图像方向，当detect_direction=true时存在。 - -1:未定义， - 0:正向， - 1: 逆时针90度， - 2:逆时针180度， - 3:逆时针270度 |
| log_id           | 是   | number | 唯一的log id，用于问题定位                                   |
| words_result_num | 是   | number | 识别结果数，表示words_result的元素个数                       |
| words_result     | 是   | array  | 定位和识别结果数组                                           |
| +words           | 否   | string | 识别结果字符串                                               |
| probability      | 否   | object | 行置信度信息；如果输入参数 probability = true 则输出         |
| +average         | 否   | number | 行置信度平均值                                               |
| +variance        | 否   | number | 行置信度方差                                                 |
| +min             | 否   | number | 行置信度最小值                                               |
