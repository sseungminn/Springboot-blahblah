package com.hong.blah.model;

import lombok.Data;

@Data
public class ChatRoom {
	int roomNumber;
	String roomName;
	
	@Override
	public String toString() {
		return "Room [roomNumber=" + roomNumber + ", roomName=" + roomName + "]";
	}	
}
