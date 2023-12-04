package dev.j3rzy.jujunonshq.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static dev.j3rzy.jujunonshq.utils.JSONUtils.jsonToEmbed;

public class CreateEmbedCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("embed")) return;
        if (event.getOption("json") == null) return;

        String jsonString = event.getOption("json").getAsString();

        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            event.reply("").addEmbeds(jsonToEmbed(json)).queue();
        } catch (JsonSyntaxException | IllegalStateException e) {
            event.reply("The provided input is not a valid JSON object.").queue();
        }
    }
}

