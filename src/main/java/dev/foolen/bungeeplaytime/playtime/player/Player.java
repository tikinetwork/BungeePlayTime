package dev.foolen.bungeeplaytime.playtime.player;

import java.util.UUID;

public class Player {

	private UUID uuid;
	private int totalSecondsPlayed;
	
	public Player(UUID uuid, int totalSecondsPlayed) {
		this.uuid = uuid;
		this.totalSecondsPlayed = totalSecondsPlayed;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public int getTotalSecondsPlayed() {
		return totalSecondsPlayed;
	}
	
}
