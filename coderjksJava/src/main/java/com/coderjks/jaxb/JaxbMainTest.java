package com.coderjks.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JaxbMainTest {
    public static void main(String[] args) {
        JAXBContext context = null;
        HttpURLConnection connection = null;
        OutputStream os = null;
        InputStream is = null;

        XmlRequestVO requestVO = makeRequestVO();

        try {
            context = JAXBContext.newInstance(XmlRequestVO.class);
            StringWriter writer = new StringWriter();
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(requestVO, writer);

            URL url = new URL("127.0.0.1");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");

            os = connection.getOutputStream();

            System.out.println("보내는 XML 전문: \n" + writer.toString());

            os.write(writer.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            is = connection.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line = null;

            System.out.println("Response 출력 시작 \n");
            while((line = in.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("Response 출력 끝 \n");

            JAXBContext jc = JAXBContext.newInstance();
            XmlResponseVO responseVO = (XmlResponseVO) jc.createUnmarshaller().unmarshal(is);

            System.out.println("전달받은 MSG: " + responseVO.getXmlRequestData().getMsg());
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static XmlRequestVO makeRequestVO() {
        XmlRequestVO.XmlRequestData xmlRequestData = new XmlRequestVO.XmlRequestData();

        xmlRequestData.setId("myId");
        xmlRequestData.setPw("myPw");

        XmlRequestVO vo = new XmlRequestVO();
        vo.setXmlRequestData(xmlRequestData);

        return vo;
    }
}
