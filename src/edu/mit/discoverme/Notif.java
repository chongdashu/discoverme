package edu.mit.discoverme;

//package de.vogella.android.sqlite.first;

public class Notif {
	private long id;
	private String name;
	private String type;
	private String detail;
	private String readFlag;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}


	public void setNotif(String name, String type, String details,
			String readFlag) {
		this.name = name;
		this.type = type;
		this.detail = details;
		this.readFlag = readFlag;


	}
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}
	
	public boolean isRead() {
		return getReadFlag().equals("yes");
	}
}
