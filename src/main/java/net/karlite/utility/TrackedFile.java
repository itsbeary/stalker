package net.karlite.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrackedFile {

    private static final Path PATH = Path.of("players.txt");

    public static List<String> get() {
        if(!Files.exists(PATH)) {
            return new ArrayList<>();
        }
        try {
            return Arrays.stream(Files.readString(PATH).split("\n")).map(String::trim).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void add(String data) {
        try {
            if(!Files.exists(PATH)) {
                Files.createFile(PATH);
                System.out.println("Could not find " + PATH + ". (now creating)");
            }

            var current = Files.readString(PATH);
            current += data + "\n";
            Files.writeString(PATH, current);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
