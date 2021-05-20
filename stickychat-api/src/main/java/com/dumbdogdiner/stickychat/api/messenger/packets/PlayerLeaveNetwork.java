package com.dumbdogdiner.stickychat.api.messenger.packets;



import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import com.dumbdogdiner.stickychat.api.messenger.RemoteServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlayerLeaveNetwork extends PlayerChangeServer {
    @Override
    default @NotNull PacketType getType() {
        return PacketType.PLAYER_LEAVE_NETWORK;
    }

    @Override
    @Nullable
    default RemoteServer getTo() {
        return null;
    }
}
