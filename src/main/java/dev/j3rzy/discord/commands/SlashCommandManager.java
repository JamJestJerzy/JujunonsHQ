package dev.j3rzy.discord.commands;

import dev.j3rzy.discord.commands.commands.util.EmbedCommand;

import java.util.ArrayList;
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

    /* Here register all commands */
    private void registerCommands() {
        commands.add(new EmbedCommand());
    }
}
