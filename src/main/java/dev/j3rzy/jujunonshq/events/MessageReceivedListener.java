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
        SelfUser selfUser = event.getJDA().getSelfUser();
        Message message = event.getMessage();
        User author = message.getAuthor();
        User dash1e = event.getJDA().getUserById("719890634294427669");

        try { saveMessage(message); }
        catch (SQLException e) { throw new RuntimeException(e); }

        /* Status on ping */
        if (message.getContentRaw().contains("<@"+selfUser.getId()+">")) {
            if (message.getAuthor().isBot()) return;

            log.info(author.getName() + " pinged bot");

            String iconUrl = null;
            try {
                String fromJSON = JSONUtils.getString("footer-icon-url");
                iconUrl = (fromJSON.isEmpty() ? (dash1e == null ? "https://j3rzy.dev/images/fluttershy.png" : dash1e.getAvatarUrl()) : fromJSON);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            EmbedBuilder emb = new EmbedBuilder()
                .setTitle("Welcome!")
                .setDescription(
                        "**Ping: **" + event.getJDA().getGatewayPing() + "ms" + '\n'
                        + "**Uptime: **" + getUptime() + '\n'
                        + "**RAM Usage: **" + getMemoryUsage() + '\n'
                        + "**JDA Version: **" + getPackageVersion(JDA.class) + '\n'
                        + "**JRE Version: **" + System.getProperty("java.version")
                )
                .setFooter("in Java, by dash1e", iconUrl)
                .setColor(new Color(252, 244, 164));

            message.reply("").setEmbeds(emb.build()).mentionRepliedUser(false).queue();
        }
    }
}
