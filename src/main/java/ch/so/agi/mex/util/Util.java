package ch.so.agi.mex.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {

    public static String loadUtf8TextFileContent(String fileName){

        String res = null;

        ClassLoader classLoader = Util.class.getClassLoader();

        try(InputStream inputStream = classLoader.getResourceAsStream(fileName)){
            res = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

}
