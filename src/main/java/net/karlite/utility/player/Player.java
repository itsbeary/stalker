package net.karlite.utility.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private final String name;
    private final String channelTrackedIn;
    private PlayerState state;
}
