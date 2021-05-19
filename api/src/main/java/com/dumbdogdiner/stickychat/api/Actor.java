package com.dumbdogdiner.stickychat.api;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a console or human-like entity that triggered an
 * event or packet.
 */
public class Actor {
	@Getter
	private final UUID uniqueId;
	@Getter
	private final String name;

	/**
	 * @return The console actor.
	 */
	public static Actor getCosoleActor() {
		return new Actor(new UUID(0,0), "CONSOLE");
	}

	/**
	 * Construct a new actor.
	 * @param uniqueId The unique ID of the actor
	 * @param name The name of the actor.
	 */
	public Actor(UUID uniqueId, String name) {
		this.uniqueId = uniqueId;
		this.name = name;
	}

	/**
	 * @return True if this actor was the console.
	 */
	boolean isConsole() {
		return this.uniqueId.equals(new UUID(0, 0));
	}

	/**
	 * @return True if this actor was a player.
	 */
	boolean isPlayer() {
		return !this.isConsole();
	}

	/**
	 * @return True if this actor is online.
	 */
	boolean isOnline() {
		if (this.isConsole()) {
			return true;
		}
		return this.getPlayer() != null;
	}

	/**
	 * Attempt to resolve this actor into a player.
	 * @return A {@link Player} if they are online, otherwise `null`.
	 */
	@Nullable Player getPlayer() {
		if (this.isConsole()) {
			throw IllegalStateException("Cannot resolve CONSOLE actor to OfflinePlayer");
		}
		return Bukkit.getPlayer(this.uniqueId);
	}

	/**
	 * @return The offline player this actor represents.
	 */
	@NotNull OfflinePlayer getOfflinePlayer() {
		if (this.isConsole()) {
			throw IllegalStateException("Cannot resolve CONSOLE actor to OfflinePlayer");
		}
		return Bukkit.getOfflinePlayer(this.uniqueId);
	}
}
