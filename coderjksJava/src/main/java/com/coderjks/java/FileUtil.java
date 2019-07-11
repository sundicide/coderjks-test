package com.coderjks.java;

import org.apache.http.client.utils.DateUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {

    @Test
    public void fileMv() {
        String origPath = "d:/1.GIT/WatchAll_ETLAgent/source/WatchAll_ETLAgent/v10/java";

        try {
            List<Path> result = Files.walk(Paths.get(origPath))
                    .filter(Files::isRegularFile)
                    .filter(f -> {
                        File fi = f.toFile();
                        Long lastModifiedTime = fi.lastModified();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(lastModifiedTime);


                        System.out.println((System.currentTimeMillis() - lastModifiedTime) / 1000L);
                        return true;
                    })
                    .collect(Collectors.toList())
//                    .forEach(System.out::println)
                    ;

            System.out.println(result.get(0));
//            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
