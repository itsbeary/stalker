package net.karlite;

import net.karlite.commands.impl.PlayersCommand;
import net.karlite.commands.impl.TrackPlayerCommand;
import net.karlite.services.BattleMetricsService;
import net.karlite.utility.player.Player;
import net.karlite.utility.player.PlayerState;

public class StalkerBot extends DiscordBot {

    private static final double API_UPDATE_TIME = 5; // update every seconds
    private BattleMetricsService battleMetricsService;

    private double lastApiMessage = 0;

    @Override
    public void enable() {
        battleMetricsService = new BattleMetricsService(null);

        commandRegistry.Register(new PlayersCommand(battleMetricsService));
        commandRegistry.Register(new TrackPlayerCommand(battleMetricsService));
    }

    @Override
    public void update(double delta) {
        lastApiMessage += delta;

        if(lastApiMessage < API_UPDATE_TIME)
            return;

        var players = battleMetricsService.getOnlinePlayers(properties.serverId());
        for(Player player : battleMetricsService.getTrackedPlayers()) {

            PlayerState previous = player.getState();
            PlayerState now = players.contains(player.getName()) ? PlayerState.CONNECTED : PlayerState.DISCONNECTED;

            // disconnected event
            if (now == PlayerState.DISCONNECTED &&
                    (previous == PlayerState.CONNECTED || previous == PlayerState.UNKNOWN)) {

                var channel = jda.getTextChannelById(player.getChannelTrackedIn());
                if(channel != null) {
                    channel.sendMessage("@everyone - Player " + player.getName() + " has disconnected").queue();
                }
            }

            // connected event
            if (now == PlayerState.CONNECTED &&
                    (previous == PlayerState.DISCONNECTED || previous == PlayerState.UNKNOWN)) {

                var channel = jda.getTextChannelById(player.getChannelTrackedIn());
                if(channel != null) {
                    channel.sendMessage("@everyone - Player " + player.getName() + " has connected").queue();
                }
            }
            player.setState(now);
        }

        lastApiMessage = 0; // reset
    }
}
