package com.github.nes370.discordbot;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Responder implements MessageCreateListener {
	
	private String prefix;

	public Responder(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		
		if(event.getMessageAuthor().isRegularUser() && event.getMessageContent().startsWith(prefix)) {
			
			String[] args = event.getMessageContent().substring(prefix.length()).split(" ");
			
			//TODO handle commands
			
		}
		
	}

}
