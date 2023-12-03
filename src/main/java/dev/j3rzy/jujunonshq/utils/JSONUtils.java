package dev.j3rzy.jujunonshq.utils;

import dev.j3rzy.jujunonshq.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import static dev.j3rzy.jujunonshq.utils.ConsoleUtils.log;

public class JSONUtils {
    private static String configFileName = "config.json";

    public static void setConfigFileName(String configFileName) {
        JSONUtils.configFileName = configFileName;
    }

    public static File getConfigFile() {
        String jarDir = System.getProperty("user.dir");
        File configFile = new File(jarDir + File.separator + configFileName);

        if (!configFile.exists()) {
            try {
                InputStream inputStream = JSONUtils.class.getResourceAsStream("/" + configFileName);
                if (inputStream != null) {
                    OutputStream outputStream = new FileOutputStream(configFile);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.close();
                    inputStream.close();
                } else {
                    throw new FileNotFoundException("Resource not found: " + configFileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configFile;
    }

    public static String getString(String key) throws IOException {
        try (Reader reader = new FileReader(getConfigFile())) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            if (jsonObject.get(key) == null) log.error("JSON object with key: " + key + " wasn't found in " + configFileName + ".");
            return (jsonObject.get(key) == null) ? "" : (String) jsonObject.get(key);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}