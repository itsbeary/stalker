package net.karlite.commands.impl;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.karlite.commands.BaseCommand;
import net.karlite.services.BattleMetricsService;
import net.karlite.utility.TrackedFile;
import net.karlite.utility.player.Player;
import net.karlite.utility.player.PlayerState;

import java.util.Objects;

public class TrackPlayerCommand extends BaseCommand {

    private final BattleMetricsService battleMetrics;

    public TrackPlayerCommand(BattleMetricsService battleMetrics) {
        super("track", "Track a player connection on a server");
        this.battleMetrics = battleMetrics;
    }

    @Override
    public SlashCommandData buildCommandData() {
        return Commands.slash(getName(), getDescription())
                .addOption(OptionType.STRING, "name", "the player you want to track", true);
    }

    @Override
    public void onCommandExecute(SlashCommandInteractionEvent event) {
        String name = Objects.requireNonNull(event.getOption("name")).getAsString();

        battleMetrics.getTrackedPlayers().add(new Player(name, event.getChannelId(), PlayerState.UNKNOWN));
        TrackedFile.add(name + "|" + event.getChannelId());

        event.reply("You are now tracking the player: " + name).queue();

    }
}
