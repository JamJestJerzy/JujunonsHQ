package dev.j3rzy.discord.events;

import dev.j3rzy.discord.objects.CMessage;
import dev.j3rzy.discord.utils.JSONUtils;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static dev.j3rzy.discord.utils.ConsoleUtils.log;
import static dev.j3rzy.discord.utils.SQLUtils.getMessage;

public class MessageDeleteListener extends ListenerAdapter {
    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent event) {
        if (JSONUtils.getBoolean("log-messages")) {
            CMessage cMessage = null;
            try {
                cMessage = getMessage(event.getMessageId());
            } catch (SQLException e) {
                log.error(e.getMessage());
            }

            if (cMessage == null) return;
        }
    }
}
