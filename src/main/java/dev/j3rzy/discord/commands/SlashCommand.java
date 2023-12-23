package dev.j3rzy.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SlashCommand {
    private final String name;
    private final String description;
    private final List<SlashCommandOption> options = new ArrayList<>();

    public SlashCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SlashCommand(String name, String description, List<SlashCommandOption> options) {
        this.name = name;
        this.description = description;

        this.options.addAll(options);
    }

    public SlashCommand(String name, String description, SlashCommandOption... options) {
        this.name = name;
        this.description = description;

        this.options.addAll(Arrays.asList(options));
    }

    public SlashCommand(String name, String description, SlashCommandOption option) {
        this.name = name;
        this.description = description;

        options.add(option);
    }

    public abstract void onCommand(SlashCommandInteractionEvent event);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean haveOptions() {
        return !options.isEmpty();
    }

    public List<SlashCommandOption> getOptions() {
        return options;
    }

    public SlashCommandData getSlashCommand() {
        SlashCommandData command = Commands.slash(name, description);
        if (haveOptions())
            for (SlashCommandOption option : options)
                command.addOption(option.getType(), option.getName(), option.getDescription(), option.isRequired(), option.isAutoComplete());
        return command;
    }
}
