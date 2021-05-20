package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.Packet;
import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import com.dumbdogdiner.stickychat.api.messenger.RemoteServer;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlayerChangeServer extends Packet {
    @Override
    @NotNull
    default PacketType getType() {
        return PacketType.PLAYER_CHANGE_SERVER;
    }

    /** @return The name of the player who joined the server. */
    @NotNull
    String getPlayerName();

    /** @return The unique ID of the player who joined the server. */
    @NotNull
    UUID getPlayerUniqueId();

    /** @return The server the player is moving from. */
    @Nullable
    RemoteServer getFrom();

    /** @return The server the player is moving to. */
    @Nullable
    RemoteServer getTo();
}
