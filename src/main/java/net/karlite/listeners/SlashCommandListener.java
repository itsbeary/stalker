package net.karlite.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.karlite.commands.BaseCommand;
import net.karlite.registry.CommandRegistry;

public class SlashCommandListener extends ListenerAdapter {

    private final CommandRegistry commandRegistry;

    public SlashCommandListener(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        BaseCommand command = commandRegistry.getCommand(event.getName());

        if(command != null) {
            command.onCommandExecute(event);
        }
    }
}
