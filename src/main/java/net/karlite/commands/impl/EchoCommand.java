package net.karlite.commands.impl;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.karlite.commands.BaseCommand;

// test command
public class EchoCommand extends BaseCommand {

    public EchoCommand() {
        super("echo", "A echo command");
    }

    @Override
    public void onCommandExecute(SlashCommandInteractionEvent event) {
        String message = event.getOption("message").getAsString();
        event.reply(message).queue();
    }

    @Override
    public SlashCommandData buildCommandData() {
        return Commands.slash(getName(), getDescription())
                .addOption(OptionType.STRING, "message", "The message to echo", true);
    }
}
