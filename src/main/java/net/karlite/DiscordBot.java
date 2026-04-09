package net.karlite;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.karlite.registry.CommandRegistry;
import net.karlite.utility.BotProperties;

@Getter
@Setter
public abstract class DiscordBot {

    protected final CommandRegistry commandRegistry;
    protected final JDA jda;
    protected final BotProperties properties = BotProperties.load();

    private boolean running = true;
    private double elapsed;


    public DiscordBot() {
        jda = JDABuilder.createLight(properties.botToken())
                .build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        commandRegistry = new CommandRegistry(jda);

        enable();
        commandRegistry.push();

        long lastTick = System.nanoTime();
        while(running) {

            long current = System.nanoTime();
            double delta = (current - lastTick) / 1e+9; // turns ns to s
            lastTick = current;
            elapsed += delta;

            update(delta);
        }
    }

    public abstract void enable();
    public abstract void update(double delta);

}
