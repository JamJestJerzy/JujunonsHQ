package dev.j3rzy.discord.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public class SlashCommandOption {
    private final OptionType type;
    private final String name;
    private final String description;
    private final boolean required;
    private final boolean autoComplete;

    public SlashCommandOption(OptionType type, String name, String description, boolean required, boolean autoComplete) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
        this.autoComplete = autoComplete;
    }

    public SlashCommandOption(OptionType type, String name, String description, boolean required) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
        this.autoComplete = false;
    }

    public SlashCommandOption(OptionType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = false;
        this.autoComplete = false;
    }

    public OptionType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isAutoComplete() {
        return autoComplete;
    }
}
