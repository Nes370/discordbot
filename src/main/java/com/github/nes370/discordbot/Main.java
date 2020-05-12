package com.github.nes370.discordbot;

import org.ini4j.*;	// Provides means of reading and writing to config.ini
import org.javacord.api.*; // Manages interaction with the Discord API
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.UserStatus;

public class Main {
	
	public static void main(String[] args) {
		
		// Retrieve settings from config.ini
		
		String token = null,
				prefix = null,
				userStatus = null,
				activity = null,
				activityType = null;
		Wini ini = null;
		try {
			ini = new Wini(Main.class.getClassLoader().getResourceAsStream("config.ini"));
			token = ini.get("Settings", "token");
			prefix = ini.get("Settings", "prefix");
			userStatus = ini.get("Settings", "userStatus");
			activity = ini.get("Settings", "activity");
			activityType = ini.get("Settings", "activityType");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Application terminated due to config error.");
			System.exit(0);
		}
		
		// Log into Discord as an application
		
		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
		System.out.println("Logged into Discord.");
		
		// Update default status and activity
		
		if(userStatus != null)
			api.updateStatus(UserStatus.fromString(userStatus));
		else api.updateStatus(UserStatus.ONLINE);
		
		if(activity != null) {
			switch(activityType.toLowerCase()) {
				case "listening":
					api.updateActivity(ActivityType.LISTENING, activity);
					break;
				case "playing":
					api.updateActivity(ActivityType.PLAYING, activity);
					break;
				case "streaming":
					api.updateActivity(ActivityType.STREAMING, activity);
					break;
				case "watching":
					api.updateActivity(ActivityType.WATCHING, activity);
					break;
				default:
					api.updateActivity(activity);
					break;
			}
		}
		
		// Create listener threads
		
		api.addMessageCreateListener(new Responder(prefix));
		
	}

}
