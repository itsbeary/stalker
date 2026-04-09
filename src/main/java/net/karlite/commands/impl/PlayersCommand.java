package net.karlite.commands.impl;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.karlite.commands.BaseCommand;
import net.karlite.services.BattleMetricsService;

import java.util.List;

public class PlayersCommand extends BaseCommand {

    private static final String SERVER_ID = "1720719";
    private final BattleMetricsService battleMetrics;

    public PlayersCommand(BattleMetricsService battleMetrics) {
        super("players", "List online players on AU Long");
        this.battleMetrics = battleMetrics;
    }

    @Override
    public void onCommandExecute(SlashCommandInteractionEvent event) {
        // Defer immediately — API call may take > 3 seconds
        event.deferReply().queue();

        try {
            List<String> players = battleMetrics.getOnlinePlayers(SERVER_ID);

            if (players.isEmpty()) {
                event.getHook().sendMessage("No players online right now.").queue();
                return;
            }

            String playerList = String.join("\n", players);
            event.getHook().sendMessage(
                    "**Online Players (" + players.size() + "):**\n```\n" + playerList + "\n```"
            ).queue();

        } catch (Exception e) {
            event.getHook().sendMessage("Failed to fetch player list: " + e.getMessage()).queue();
        }
    }
}
