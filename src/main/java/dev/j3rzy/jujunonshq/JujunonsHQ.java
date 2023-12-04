package dev.j3rzy.jujunonshq;

import dev.j3rzy.jujunonshq.commands.CreateEmbedCommand;
import dev.j3rzy.jujunonshq.events.MessageDeleteListener;
import dev.j3rzy.jujunonshq.events.MessageReceivedListener;
import dev.j3rzy.jujunonshq.events.ReadyListener;
import dev.j3rzy.jujunonshq.utils.JSONUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Objects;

public class JujunonsHQ {
    public static JDA jda;

    public static void main(String[] args) throws Exception {
        if (Objects.equals(JSONUtils.getString("token"), "")) {
            throw new Exception("You need to fill at least `token` field in config.json!");
        }

        JDABuilder builder = JDABuilder.createDefault(JSONUtils.getString("token")); /* Builder itself */
        builder.setActivity(Activity.listening("Koa <3")); /* Sets activity */
        for (GatewayIntent gatewayIntent : GatewayIntent.values()) builder.enableIntents(gatewayIntent); /* Enable every possible intent */

        /* -------- [ Event Listeners ] -------- */
        builder.addEventListeners(new ReadyListener());
        builder.addEventListeners(new MessageDeleteListener());
        builder.addEventListeners(new MessageReceivedListener());
        /* -------- [ Event Listeners ] -------- */

        /* -------- [ Event Listeners for Commands ] -------- */
        builder.addEventListeners(new CreateEmbedCommand());
        /* -------- [ Event Listeners for Commands ] -------- */

        jda = builder.build();

        /* -------- [ Commands ] -------- */
        jda.updateCommands().addCommands(
            Commands.slash("embed", "Creates embed from provided JSON")
                .addOption(OptionType.STRING, "json", "JSON to make embed of", true)
        ).queue();
        /* -------- [ Commands ] -------- */
    }
}