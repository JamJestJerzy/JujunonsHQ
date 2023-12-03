package dev.j3rzy.jujunonshq;

import dev.j3rzy.jujunonshq.events.MessageDeleteListener;
import dev.j3rzy.jujunonshq.events.MessageReceivedListener;
import dev.j3rzy.jujunonshq.events.ReadyListener;
import dev.j3rzy.jujunonshq.utils.JSONUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;
import java.util.Objects;

import static dev.j3rzy.jujunonshq.utils.ConsoleUtils.log;

public class Main {
    public static JDA jda;

    public static void main(String[] args) throws Exception {
        if (Objects.equals(JSONUtils.getString("token"), "")) {
            throw new Exception("You need to fill `token` field in config.json!");
        }

        JDABuilder builder = JDABuilder.createDefault(JSONUtils.getString("token")); /* Builder itself */
        builder.setActivity(Activity.listening("Koa <3")); /* Sets activity */
        for (GatewayIntent gatewayIntent : GatewayIntent.values()) builder.enableIntents(gatewayIntent); /* Enable every possible intent */

        /* -------- [ Event Listeners ] -------- */
        builder.addEventListeners(new ReadyListener());
        builder.addEventListeners(new MessageDeleteListener());
        builder.addEventListeners(new MessageReceivedListener());
        /* -------- [ Event Listeners ] -------- */

        jda = builder.build();
    }
}