package dev.j3rzy.discord.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import static dev.j3rzy.discord.utils.ConsoleUtils.log;

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

    public static boolean getBoolean(String key) {
        try (Reader reader = new FileReader(getConfigFile())) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            if (jsonObject.get(key) == null) log.error("JSON object with key: " + key + " wasn't found in " + configFileName + ".");
            return jsonObject.get(key) != null && (boolean) jsonObject.get(key);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessageEmbed getEmbedFromJSON(String jsonString) {
        DataObject dataObject = DataObject.fromJson(jsonString);
        EmbedBuilder embedBuilder = EmbedBuilder.fromData(dataObject);
        return embedBuilder.build();
    }

    public static JSONObject getJSONFromString(String jsonString) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(jsonString);
    }
}
