package net.karlite.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import net.karlite.utility.TrackedFile;
import net.karlite.utility.player.Player;
import net.karlite.utility.player.PlayerState;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class BattleMetricsService {

    private static final String BASE_URL = "https://api.battlemetrics.com";
    private final HttpClient client = HttpClient.newHttpClient();
    private final String token; // can be null

    private final List<Player> trackedPlayers = new ArrayList<>();

    public BattleMetricsService(String token) {
        this.token = token;

        for(var player : TrackedFile.get()) {
            if(player.isEmpty())
                continue;
            String name = player.split("\\|")[0];
            String channel = player.split("\\|")[1];

            trackedPlayers.add(new Player(name, channel, PlayerState.UNKNOWN));
        }
    }

    public List<String> getOnlinePlayers(String serverId) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/servers/" + serverId + "?include=player"))
                .GET();

        if (token != null) {
            requestBuilder.header("Authorization", "Bearer " + token);
        }

        HttpResponse<String> response;
        try {
            response = client.send(
                    requestBuilder.build(),
                    HttpResponse.BodyHandlers.ofString()
            );
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return parsePlayers(response.body());
    }


    private List<String> parsePlayers(String json) {
        List<String> players = new ArrayList<>();

        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonArray included = root.getAsJsonArray("included");

        if (included == null) return players;

        for (JsonElement element : included) {
            JsonObject obj = element.getAsJsonObject();

            // Only grab player entries, not other included types
            if (!obj.get("type").getAsString().equals("player"))
                continue;

            String name = obj.getAsJsonObject("attributes")
                    .get("name")
                    .getAsString();
            players.add(name);
        }

        return players;
    }
}
