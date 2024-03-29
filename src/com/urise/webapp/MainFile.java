package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dir = new File("C:\\Java\\projects\\basejava\\src");
        listFiles(dir);
    }

    public static void listFiles(File file) {
        if (file.isDirectory()) {
            System.out.println("Package: " + file.getAbsolutePath());
            for (File dir : file.listFiles()) {
                if (dir.isFile()) {
                    System.out.println("   File: " + dir.getAbsolutePath());
                } else {
                    listFiles(dir);
                }
            }
        }
    }
}
