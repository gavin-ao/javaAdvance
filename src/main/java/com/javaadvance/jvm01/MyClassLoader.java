package com.javaadvance.jvm01;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aoyonggang on 2022/2/28.
 */
public class MyClassLoader extends ClassLoader {

    private String path;
    private int offSize = 255;

    public MyClassLoader(String path) {
        super(null);
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] data = new byte[0];
        try {
            data = readByteCodeFileReturnBytes(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, data, 0, data.length);
    }


    private byte[] readByteCodeFileReturnBytes(String name) throws IOException {
        byte[] result = null;
        InputStream fis = null;
        String classFilePath = path + name + ".xlass";
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            fis = new FileInputStream(classFilePath);
            byte[] bytes = new byte[fis.available()];
            int length;
            while ((length = fis.read(bytes)) != -1) {
                bos.write(bytes, 0, length);
            }
            result = bos.toByteArray();

        } finally {
            if (fis != null) {
                fis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (offSize - result[i]);
        }

        return result;
    }
}
