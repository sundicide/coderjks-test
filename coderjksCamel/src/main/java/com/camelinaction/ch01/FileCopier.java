package com.camelinaction.ch01;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class FileCopier {
    public static void main(String args[]) throws Exception {
        System.out.println(new File("./").getPath());
        File inboxDirectory = new File("data/inbox");
//        File inboxDirectory = new File(FileCopier.class.getResource("data/inbox").);
        File outboxDirectory = new File("data/outbox");

        outboxDirectory.mkdir();

        File[] files = inboxDirectory.listFiles();
        if (files != null && files.length > 0) {
            for (File source : files) {
                if (source.isFile()) {
                    File dest = new File(
                            outboxDirectory.getPath()
                                    + File.separator
                                    + source.getName());
                    copyFile(source, dest);
                }
            }
        }
    }

    private static void copyFile(File source, File dest) throws IOException {
        OutputStream out = new FileOutputStream(dest);
        byte[] buffer = new byte[(int) source.length()];
        FileInputStream in = new FileInputStream(source);
        in.read(buffer);
        try {
            out.write(buffer);
        } finally {
            out.close();
            in.close();
        }
    }
}
