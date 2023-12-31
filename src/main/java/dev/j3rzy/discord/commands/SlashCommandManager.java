package dev.j3rzy.discord.commands;

import dev.j3rzy.discord.commands.commands.HelpCommand;
import dev.j3rzy.discord.commands.commands.util.EmbedCommand;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

public class SlashCommandManager {
    public static final SlashCommandManager INSTANCE = new SlashCommandManager();

    private final List<SlashCommand> commands = new ArrayList<>();

    public SlashCommandManager() {
        registerCommands();
    }

    public List<SlashCommand> getCommands() {
        return commands;
    }

    public Collection<CommandData> getSlashCommands() {
        Collection<CommandData> commandsData = new ArrayList<>();
        for (SlashCommand slashCommand : SlashCommandManager.INSTANCE.getCommands())
            commandsData.add(slashCommand.getSlashCommand());
        return commandsData;
    }

    /* Here register all commands */
    private void registerCommands() {
        commands.add(new EmbedCommand());
        commands.add(new HelpCommand());
    }
}