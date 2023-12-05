package dev.j3rzy.discord.objects;

import dev.j3rzy.discord.JujunonsHQ;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMessage {
    private final JDA jda;
    private String guildId = null;
    private String channelId = null;
    private String messageId = null;
    private String authorId = null;
    private String content = null;
    private String modifiedContent = null;
    private final List<String> attachments = new ArrayList<>();

    public CMessage(String guildId, String channelId, String messageId, String authorId, String content, String modifiedContent, String attachments) {
        jda = JujunonsHQ.jda;

        this.guildId = guildId;
        this.channelId = channelId;
        this.messageId = messageId;
        this.authorId = authorId;
        this.content = content;
        this.modifiedContent = modifiedContent;

        if (!(attachments == null)) this.attachments.addAll(Arrays.asList(attachments.split("\n")));
    }

    public Guild getGuild() {
        return jda.getGuildById(guildId);
    }

    public TextChannel getChannel() {
        return jda.getChannelById(TextChannel.class, channelId);
    }

    public String getMessageId() {
        return messageId;
    }

    public User getAuthor() {
        return jda.getUserById(authorId);
    }

    public String getContent() {
        return content;
    }

    public String getModifiedContent() {
        return modifiedContent;
    }

    public List<String> getAttachments() {
        return attachments;
    }
}
