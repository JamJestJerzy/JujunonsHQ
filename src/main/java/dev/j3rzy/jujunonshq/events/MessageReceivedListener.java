package dev.j3rzy.jujunonshq.events;

import dev.j3rzy.jujunonshq.utils.JSONUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static dev.j3rzy.jujunonshq.utils.ConsoleUtils.*;
import static dev.j3rzy.jujunonshq.utils.SQLUtils.saveMessage;

public class MessageReceivedListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        User author = message.getAuthor();

        if (author.isBot()) return; // Avoids logging bots messages

        SelfUser selfUser = event.getJDA().getSelfUser();
        User dash1e = event.getJDA().getUserById("719890634294427669");

        if (JSONUtils.getBoolean("log-messages")) {
            try { saveMessage(message); }
            catch (SQLException e) { log.error(e.getMessage()); }
        }

        /* Status on ping */
        if (message.getContentRaw().contains("<@"+selfUser.getId()+">")) {
            if (message.getAuthor().isBot()) return;

            log.info(author.getName() + " pinged bot");

            String iconUrl = "https://j3rzy.dev/images/Java.png";

            EmbedBuilder emb = new EmbedBuilder()
                .setTitle("Welcome!")
                .setDescription(
                        "**Ping: **" + event.getJDA().getGatewayPing() + "ms" + '\n'
                        + "**Uptime: **" + getUptime() + '\n'
                        + "**RAM Usage: **" + getMemoryUsage() + '\n'
                        + "**JDA Version: **" + (getPackageVersion(JDA.class) != null ? getPackageVersion(JDA.class) : "5.0.0-beta.18") + '\n'
                        + "**JRE Version: **" + System.getProperty("java.version") + '\n'
                        + "[Source Code (Java)](https://github.com/JamJestJerzy/JujunonsHQ)"
                )
                .setFooter("by Jerzy (dash1e)", iconUrl)
                .setColor(new Color(66, 117, 149));

            message.reply("").setEmbeds(emb.build()).mentionRepliedUser(false).queue();
        }
    }
}
