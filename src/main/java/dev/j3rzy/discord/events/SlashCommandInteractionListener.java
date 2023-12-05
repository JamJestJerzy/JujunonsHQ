package dev.j3rzy.discord.events;

import dev.j3rzy.discord.commands.SlashCommand;
import dev.j3rzy.discord.commands.SlashCommandManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static dev.j3rzy.discord.utils.ConsoleUtils.log;

public class SlashCommandInteractionListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (SlashCommand slashCommand : SlashCommandManager.INSTANCE.getCommands()) {
            if (event.getName().equals(slashCommand.getName())) slashCommand.onCommand(event);
        }

        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now());
        log.info("[" + date + "] " + event.getUser().getName() + " used command " + event.getName() + "!");
    }
}
