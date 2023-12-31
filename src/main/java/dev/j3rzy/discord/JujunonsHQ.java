package dev.j3rzy.discord;

import dev.j3rzy.discord.commands.SlashCommand;
import dev.j3rzy.discord.commands.SlashCommandManager;
import dev.j3rzy.discord.commands.SlashCommandOption;
import dev.j3rzy.discord.events.*;
import dev.j3rzy.discord.utils.JSONUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static dev.j3rzy.discord.utils.ConsoleUtils.log;
import static dev.j3rzy.discord.utils.SQLUtils.checkForTables;

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

        jda = builder.build();

        /* -------- [ Commands ] -------- */
        jda.updateCommands().addCommands(SlashCommandManager.INSTANCE.getSlashCommands()).queue();
        /* -------- [ Commands ] -------- */


        try {
            checkForTables();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}