package net.karlite.registry;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.karlite.commands.BaseCommand;
import net.karlite.listeners.SlashCommandListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandRegistry {

    private final HashMap<String, BaseCommand> commands = new HashMap<>();
    private final JDA jda;

    public CommandRegistry(JDA jda) {
        this.jda = jda;

        jda.addEventListener(new SlashCommandListener(this));
    }

    public void Register(BaseCommand command) {
        commands.put(command.getName(), command);

        System.out.println("Added command: " + command.getName());
    }

    // called after commands are made
    public void push() {
        CommandListUpdateAction updateAction = jda.updateCommands();

        List<SlashCommandData> commandData = new ArrayList<>();
        for(BaseCommand command : commands.values()) {
            commandData.add(command.buildCommandData());
        }

        updateAction.addCommands(commandData).queue();
    }

    public BaseCommand getCommand(String name) {
        return commands.get(name);
    }

}
