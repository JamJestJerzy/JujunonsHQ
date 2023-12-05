package dev.j3rzy.jujunonshq.events;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MessageReactionAddListener extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser();
        Message message = event.retrieveMessage().complete();
        MessageReaction reaction = event.getReaction();

        if (Objects.equals(reaction.getEmoji().toString(), "UnicodeEmoji(codepoints=U+274c)")) {
            Message.Interaction interaction = message.getInteraction();

            if (interaction == null) return;

            if (interaction.getUser() != user) {
                reaction.removeReaction().complete();
                return;
            }

            message.delete().complete();
        }
    }
}
