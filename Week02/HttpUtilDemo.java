package org.example.week02;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
public class HttpUtilDemo {

    public static void main(String[] args) throws Exception {
        String result = sendGetByUrl("http://localhost:8801/");
        System.out.println(result);
    }
    public static String sendGetByUrl(String url) throws Exception{

        // get请求返回结果
        String result = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response =null;
        try {
             response = client.execute(request);

            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "utf-8");
            } else {
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            response.close();
            request.releaseConnection();
            client.close();

        }
        return result;
    }
}
