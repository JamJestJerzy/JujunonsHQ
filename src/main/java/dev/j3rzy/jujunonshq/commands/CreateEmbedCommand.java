package dev.j3rzy.jujunonshq.commands;

import com.google.gson.JsonParseException;
import dev.j3rzy.jujunonshq.utils.JSONUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CreateEmbedCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("embed")) return;
        if (event.getOption("json") == null) return;

        event.deferReply().queue();

        try {
            MessageEmbed emb = JSONUtils.getEmbedFromJSON(event.getOption("json").getAsString());
            event.getHook().sendMessage("").addEmbeds(emb).queue();
        } catch (JsonParseException | IllegalStateException | NumberFormatException e) {
            String error = e.getMessage()/*.replaceFirst("^.*: U", "U")*/.replaceFirst(".at.*$", "");
            MessageEmbed emb = new EmbedBuilder().setColor(Color.RED).setTitle(error).build();
            event.getHook().sendMessage("").addEmbeds(emb).queue(message -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}