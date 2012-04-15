package edu.mit.discoverme;

public class EventLocation {
	
	public String name;
	public Boolean isQuiet;
	public Boolean hasFood;
	public Boolean hasIT;
	
	
	public EventLocation(String n, Boolean food, Boolean quiet, Boolean IT){
		name = n;
		hasFood = food;
		isQuiet = quiet;
		hasIT = IT;
	}

}
