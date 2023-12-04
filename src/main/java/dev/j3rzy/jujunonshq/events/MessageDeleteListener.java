package dev.j3rzy.jujunonshq.events;

import dev.j3rzy.jujunonshq.objects.CMessage;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static dev.j3rzy.jujunonshq.utils.ConsoleUtils.log;
import static dev.j3rzy.jujunonshq.utils.SQLUtils.getMessage;

public class MessageDeleteListener extends ListenerAdapter {
    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent event) {
        CMessage cMessage = null;
        try {
            cMessage = getMessage(event.getMessageId());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        if (cMessage == null) return;

        log.info(cMessage.getContent());
    }
}
