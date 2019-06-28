package com.coderjks.snippet.java;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class URLSnippet {

    /**
     * SOURCE: https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
     * @throws Exception
     */
    public void sednPost() throws Exception {
        final String USER_AGENT = "Mozilla/5.0";
        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }
    public void sendURLGET() {
        HttpURLConnection connection;
        BufferedReader in;

        Map<String, Object> testMap = new HashMap<>();
        testMap.put("key1", "value1");
        String sendUrl = makeURLUTF8("127.0.0.1", testMap);

        try {
            URL url = new URL(sendUrl);
            connection = (HttpURLConnection) url.openConnection();

            // optional default is GET
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("responseCode: " + responseCode);

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("response" + response.toString());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 전달받은 URL 뒤에 param들을 붙인 결과를 리턴한다. 인코딩 타입은 UTF-8
     */
    public String makeURLUTF8(String defaultURL, Map<String, Object> paramMap) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("key1", paramMap.get("key1"));

        StringBuilder sb = new StringBuilder();
        sb.append(defaultURL);

        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            try {
                sb.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                sb.append("=");
                if (param.getValue() != null) {
                    sb.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("result url: " + sb.toString());
        return sb.toString();
    }
}
