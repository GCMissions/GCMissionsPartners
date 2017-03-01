package com.hengtiansoft.task.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MsmUtil {
    private static String msgUrl = "http://web.1xinxi.cn/asmx/smsservice.aspx?";
    private static String msgUserName = "13738105526";
    private static String msgPassword = "392CB3E9A1304DFAD0311880C8E9";
    private static String request = "POST";
    
    
    public static String sender(String mobile, String content, String sign) throws IOException {
        // 创建StringBuffer对象用来操作字符串
        StringBuffer sb = new StringBuffer(msgUrl);

        // 向StringBuffer追加用户名
        sb.append("name="+msgUserName);

        // 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
        sb.append("&pwd="+msgPassword);

        // 向StringBuffer追加手机号码
        sb.append("&mobile=" + mobile);

        // 向StringBuffer追加消息内容转URL标准码
        sb.append("&content="+URLEncoder.encode(content,"UTF-8"));
        
        //追加发送时间，可为空，为空为及时发送
        sb.append("&stime=");
        
        //加签名
        if (sign != null) {
            sb.append("&sign="+URLEncoder.encode(sign,"UTF-8"));
        }
        
        //type为固定值pt  extno为扩展码，必须为数字 可为空
        sb.append("&type=pt&extno=fdsa");
        // 创建url对象
        URL url = new URL(sb.toString());

        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod(request);

        // 发送
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        // 返回发送结果
        String inputline = in.readLine();

        // 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
        System.out.println(inputline);
        return inputline.substring(0,inputline.indexOf(","));
    }
    
}
