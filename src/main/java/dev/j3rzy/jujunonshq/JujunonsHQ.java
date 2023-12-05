package dev.j3rzy.jujunonshq;

import dev.j3rzy.jujunonshq.commands.skyblock.MayorCommand;
import dev.j3rzy.jujunonshq.commands.util.EmbedCommand;
import dev.j3rzy.jujunonshq.events.*;
import dev.j3rzy.jujunonshq.utils.JSONUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.sql.SQLException;
import java.util.Objects;

import static dev.j3rzy.jujunonshq.utils.ConsoleUtils.log;
import static dev.j3rzy.jujunonshq.utils.SQLUtils.checkForTables;

public class JujunonsHQ {
    public static JDA jda;

    public static void main(String[] args) throws Exception {
        if (Objects.equals(JSONUtils.getString("token"), "")) {
            throw new Exception("You need to fill at least `token` field in config.json!");
        }

        JDABuilder builder = JDABuilder.createDefault(JSONUtils.getString("token")); /* Builder itself */
        builder.setStatus(OnlineStatus.IDLE);
        builder.setActivity(Activity.listening("Koa <3")); /* Sets activity */
        for (GatewayIntent gatewayIntent : GatewayIntent.values()) builder.enableIntents(gatewayIntent); /* Enable every possible intent */

        /* -------- [ Event Listeners ] -------- */
        builder.addEventListeners(new ReadyListener());
        builder.addEventListeners(new MessageDeleteListener());
        builder.addEventListeners(new MessageReceivedListener());
        builder.addEventListeners(new SlashCommandInteractionListener());
        builder.addEventListeners(new MessageReactionAddListener());
        /* -------- [ Event Listeners ] -------- */

        /* -------- [ Event Listeners for Commands ] -------- */
        builder.addEventListeners(new EmbedCommand());
        builder.addEventListeners(new MayorCommand());
        /* -------- [ Event Listeners for Commands ] -------- */

        jda = builder.build();

        /* -------- [ Commands ] -------- */
        jda.updateCommands().addCommands(
            Commands.slash("embed", "Creates embed from provided JSON")
                .addOption(OptionType.STRING, "json", "JSON to make embed of", true),
            Commands.slash("mayor", "Provides info about SkyBlock's mayor elections")
        ).queue();
        /* -------- [ Commands ] -------- */


        try {
            checkForTables();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}