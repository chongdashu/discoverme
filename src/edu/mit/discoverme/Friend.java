package edu.mit.discoverme;

//package de.vogella.android.sqlite.first;

public class Friend {
	private long id;
	private String name;
	private String fone;
	private String email;
	private String address;

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

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setFriend(String name, String fone, String email, String address) {
		this.name = name;
		this.fone = fone;
		this.email = email;
		this.address = address;
	}
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}
}
