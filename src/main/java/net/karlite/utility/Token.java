package net.karlite.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Token {

    public static String Get() {
        try {
            return Files.readString(Path.of("token.txt")).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
