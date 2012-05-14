package edu.mit.discoverme;

import java.util.Comparator;

public class FriendComparator implements Comparator<Friend> {
	@Override
	public int compare(Friend o1, Friend o2) {
		String l1 = o1.getName();
		String l2 = o2.getName();
		int retVal = 0;
		retVal = l1.compareTo(l2);
		return retVal;

	}
}
