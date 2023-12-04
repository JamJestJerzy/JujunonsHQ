package dev.j3rzy.jujunonshq.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.j3rzy.jujunonshq.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
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

    public static MessageEmbed jsonToEmbed(JsonObject json) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        JsonPrimitive titleObj = json.getAsJsonPrimitive("title");
        if (titleObj != null) {
            embedBuilder.setTitle(titleObj.getAsString());
        }

        JsonObject authorObj = json.getAsJsonObject("author");
        if (authorObj != null) {
            String authorName = authorObj.get("name").getAsString();
            String authorIconUrl = authorObj.get("icon_url").getAsString();
            embedBuilder.setAuthor(authorName, null, authorIconUrl); // Assuming null for URL as the method only takes name and URL.
        }

        JsonPrimitive descObj = json.getAsJsonPrimitive("description");
        if (descObj != null) {
            embedBuilder.setDescription(descObj.getAsString());
        }

        JsonPrimitive colorObj = json.getAsJsonPrimitive("color");
        if (colorObj != null) {
            Color color = new Color(colorObj.getAsInt());
            embedBuilder.setColor(color);
        }

        JsonArray fieldsArray = json.getAsJsonArray("fields");
        if (fieldsArray != null) {
            fieldsArray.forEach(ele -> {
                String name = ele.getAsJsonObject().get("name").getAsString();
                String content = ele.getAsJsonObject().get("value").getAsString();
                boolean inline = ele.getAsJsonObject().get("inline").getAsBoolean();
                embedBuilder.addField(name, content, inline);
            });
        }

        JsonObject thumbnailObj = json.getAsJsonObject("thumbnail");
        if (thumbnailObj != null) {
            String thumbnailUrl = thumbnailObj.get("url").getAsString();
            embedBuilder.setThumbnail(thumbnailUrl);
        }

        JsonObject footerObj = json.getAsJsonObject("footer");
        if (footerObj != null) {
            String content = footerObj.get("text").getAsString();
            String footerIconUrl = footerObj.get("icon_url").getAsString();
            embedBuilder.setFooter(content, footerIconUrl);
        }

        return embedBuilder.build();
    }
}
