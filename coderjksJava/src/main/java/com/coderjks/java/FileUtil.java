package com.coderjks.java;

import org.apache.http.client.utils.DateUtils;
import org.junit.Test;
import sun.security.tools.policytool.Resources_ko;

import javax.annotation.Resources;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class FileUtil {

    @Test
    public void readProperties() {
        Properties properties = new Properties();

        try {
            InputStream is = FileUtil.class.getResourceAsStream("config.properties");
            properties.load(is);

            String srcFolder = properties.getProperty("src_folder");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileMv() {
        String srcFolder = "";
        Properties properties = new Properties();

        try {
            InputStream is = FileUtil.class.getResourceAsStream("config.properties");
            properties.load(is);

            srcFolder = properties.getProperty("src_folder");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            List<Path> result = Files.walk(Paths.get(srcFolder))
                .filter(Files::isRegularFile)
                .filter(f -> {
                    String dstFiles = properties.getProperty("dst_files");
                    String dstFolder = properties.getProperty("dst_folder");
                    File fi = f.toFile();
                    if ((fi.getName()).indexOf(dstFiles) > -1 && fi.getName().indexOf(".class") > -1) {
                        try {
                            Path newFile = Paths.get(dstFolder).resolve(f.getFileName());
                            if (Files.exists(newFile)) {
                                Files.delete(newFile);
                            }
                            Files.copy(f, newFile);
                            return true;
                        } catch (FileAlreadyExistsException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                        return false;
                })
                .collect(Collectors.toList())
//                    .forEach(System.out::println)
                ;

            System.out.println(result.toString());
//            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
