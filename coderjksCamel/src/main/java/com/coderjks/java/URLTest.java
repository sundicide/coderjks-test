package com.coderjks.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class URLTest {

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
