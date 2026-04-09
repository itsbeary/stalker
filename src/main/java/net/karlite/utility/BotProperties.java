package net.karlite.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public record BotProperties(String botToken, String serverId) {

    private static final Path PATH = Path.of("bot.properties");

    public static BotProperties load() {
        try {
            if (Files.notExists(PATH))
                createDefaultFile();

            try (FileInputStream input = new FileInputStream(PATH.toFile())) {

                Properties properties = new Properties();
                properties.load(input);

                String botToken = properties.getProperty("botToken");
                String serverId = properties.getProperty("serverId");

                return new BotProperties(botToken, serverId);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load bot.properties", e);
        }
    }

    private static void createDefaultFile() throws IOException {
        Properties defaults = new Properties();

        defaults.setProperty("botToken", "PUT_YOUR_TOKEN_HERE");
        defaults.setProperty("serverId", "PUT_BATTLEMETRICS_SERVER_ID_HERE");

        try (FileOutputStream out = new FileOutputStream(PATH.toFile())) {
            defaults.store(out, "Discord Bot Configuration");
        }

        System.out.println("Created bot.properties - please fill in your values.");
    }
}
