package com.tangu.tcmts.util;

import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class HttpRequest {
    public static String httpRequest(String urls,String param,String contentType){
        URL url = null;
        HttpURLConnection http = null;
        String result="";
        try {
            url = new URL(urls);
            System.out.println("urls="+urls);
            System.out.println("param="+param);
            http = (HttpURLConnection) url.openConnection();
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setUseCaches(false);
            http.setConnectTimeout(50000);//设置连接超时
            //如果在建立连接之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            http.setReadTimeout(50000);//设置读取超时
            //如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            http.setRequestMethod("POST");
            // http.setRequestProperty("Content-Type","text/xml; charset=UTF-8");
//		    http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setRequestProperty("Content-Type",contentType);
            http.connect();


            OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "utf-8");
            osw.write(param);
            osw.flush();
            osw.close();
            System.out.println("ResponseCode:"+http.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (http != null){
                http.disconnect();
            }
        }
        return result;
    }

    public static String WebserviceRequest(String url,String nameSpace,String medthod,Object[] args){
        RPCServiceClient serviceClient = null;
        QName opAddEntry = null;
        String result=null;
        try {
            serviceClient = new RPCServiceClient();
            Options options = serviceClient.getOptions();
            EndpointReference targetEPR = new EndpointReference(url);
            options.setTo(targetEPR);
            // 指定要调用的计算机器中的方法及WSDL文件的"命名空间" 和 "接口"。
            opAddEntry= new QName(nameSpace,medthod);//添加
        } catch (AxisFault e1) {
            e1.printStackTrace();
        }
        try {
            result=serviceClient.invokeBlocking(opAddEntry,args).toString();
        } catch (AxisFault e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String [] args){
        String result=httpRequest("http://121.40.68.152:8080/sfSearchs/sfSearch?code=615841973611%2525ZJZYYDXZYRPYXGS%25250zTeMnp6CKG6MpyLerqwWcuDmrR8WaAd","",Constants.FORM_CONTENT_TYPE);
        System.out.println(result);

    }

    public static Map<String, List<Map<String, String>>> getTestData(){
        Map<String, List<Map<String, String>>> resultmap = null;
        List<Map<String, String>> list3 = new ArrayList<Map<String, String>>();
        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        resultmap = new HashMap<String, List<Map<String, String>>>();
        Map<String,String> map = new HashMap<String, String>();
        map.put("time", "2017-05-02 19:21");
        map.put("state", "已签收,感谢使用顺丰,期待再次为您服务");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 19:04");
        map.put("state", "快件交给刘兵，正在派送途中（联系电话：17682309158）");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 17:21");
        map.put("state", "正在派送途中,请您准备签收(派件人:刘兵,电话:17682309158)");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 17:11");
        map.put("state", "快件到达 【杭州拱墅美都广场营业部】");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 15:21");
        map.put("state", "快件在【杭州下沙中转场】已装车，准备发往 【杭州拱墅美都广场营业部】");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 15:03");
        map.put("state", "快件到达 【杭州下沙中转场】");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 15:03");
        map.put("state", "快件在【杭州下沙中转场】已装车，准备发往 【杭州拱墅美都广场营业部】");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 13:08");
        map.put("state", "顺丰速运 已收取快件");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 10:55");
        map.put("state", "已打包");
        list3.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 10:52");
        map.put("state", "已结束煎煮");
        list3.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 09:50");
        map.put("state", "已开始煎煮");
        list3.add(map);
//		map = new HashMap<String, String>();
//		map.put("time", "2017-05-02 09:18");
//		map.put("state", "已浸泡");
//		list3.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 09:15");
        map.put("img","http://opthjs8t2.bkt.clouddn.com/TEST_CFGJ.png");
        map.put("state","已复核");
        list3.add(map);
        map = new HashMap<String, String>();
        map.put("time", "2017-05-02 09:11");
        map.put("state", "已配药");
        list3.add(map);
        resultmap.put("logisticsState", list);
        resultmap.put("mechineState", list3);
        return resultmap;
    }
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    public static String get(String urls,String param,String Code)
    {
        URL url = null;
        HttpURLConnection http = null;
        String result="";
        try {
            url = new URL(urls);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            http.setDoOutput(false);
            http.setDoInput(true);
            //设置连接超时时间和读取超时时间
            http.setConnectTimeout(10000);
            http.setReadTimeout(10000);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //连接服务器
            http.connect();

		    /*OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), Code);
		    osw.flush();
		    osw.close();  */
            System.out.println("ResponseCode:"+http.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(),"utf-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (http != null) {
                http.disconnect();
            }
        }
        return result;
    }
}
