package net.karlite.commands;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.logging.Logger;

@Getter
public abstract class BaseCommand {

    private final String name;
    private final String description;

    public BaseCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // override for options
    public SlashCommandData buildCommandData() {
        return Commands.slash(name, description);
    }

    public abstract void onCommandExecute(SlashCommandInteractionEvent event);

}
