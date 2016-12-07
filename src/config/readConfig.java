package config;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class readConfig {

    private static String readFile(String fileName) throws IOException {

        String line = null;
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            return stringBuilder.toString();
        }
    }

    public static config parceConfig(String fileName) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(readFile(fileName), config.class);
    }
}


