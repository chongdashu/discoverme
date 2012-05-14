package edu.mit.discoverme;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
	@Override
	public int compare(Event o1, Event o2) {
		long l1 = o1.getId();
		long l2 = o2.getId();
		int retVal = 0;
		if (l1 > l2)
			retVal = -1;
		else if (l1 == l2)
			retVal = 0;
		else if (l1 < l2)
			retVal = 1;
		return retVal;

	}
}