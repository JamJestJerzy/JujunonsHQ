package dev.j3rzy.discord.commands.commands.util;

import com.google.gson.JsonParseException;
import dev.j3rzy.discord.commands.SlashCommand;
import dev.j3rzy.discord.commands.SlashCommandOption;
import dev.j3rzy.discord.utils.JSONUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static dev.j3rzy.discord.utils.ConsoleUtils.replaceLast;

public class EmbedCommand extends SlashCommand {
    public EmbedCommand() {
        super(
                "embed",
                "Creates embed from provided JSON",
                new SlashCommandOption(OptionType.STRING, "json", "JSON to make embed of", true)
        );
    }

    @Override
    public void onCommand(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        try {
            MessageEmbed emb = JSONUtils.getEmbedFromJSON(event.getOption("json").getAsString());
            event.getHook().sendMessage("").addEmbeds(emb).queue();
        } catch (JsonParseException | IllegalStateException | NumberFormatException e) {
            String error = replaceLast(e.getMessage(), ".at.*$", "");
            MessageEmbed emb = new EmbedBuilder().setColor(Color.RED).setTitle(error).build();
            event.getHook().sendMessage("").addEmbeds(emb).queue(message -> {
                message.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}