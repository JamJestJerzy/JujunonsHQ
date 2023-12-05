package dev.j3rzy.discord.commands.commands;

import dev.j3rzy.discord.commands.SlashCommand;
import dev.j3rzy.discord.commands.SlashCommandManager;
import dev.j3rzy.discord.commands.SlashCommandOption;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.awt.*;

public class HelpCommand extends SlashCommand {
    public HelpCommand() {
        super("help", "Displays list of all available commands");
    }

    @Override
    public void onCommand(SlashCommandInteractionEvent event) {
        event.deferReply().complete();

        EmbedBuilder emb = new EmbedBuilder()
                .setTitle("Available commands:")
                .setFooter("by Jerzy (dash1e)", "https://j3rzy.dev/files/Koa_OC_volt.fm.png")
                .setColor(new Color(66, 117, 149));

        for (SlashCommand slashCommand : SlashCommandManager.INSTANCE.getCommands()) {
            String options = "";
            for (SlashCommandOption option : slashCommand.getOptions()) {
                options += "> " + option.getName() + ": " + option.getDescription() + "\n";
            }
            emb.addField(
                '/' + slashCommand.getName(),
                    String.format(
                            "`%s`\n%s", slashCommand.getDescription(), options
                    ),
                    false);
        }

        emb.addField("Additional Info", "Pinging bot returns its status (API latency, Source Code link, etc..)", false);
        event.getHook().sendMessage("").addEmbeds(emb.build()).queue();
    }
}
