package edu.mit.discoverme;

import android.app.Application;

public class StateManager extends Application {


	private String[] friends;
	private String[] pendingReq;// request came n now is waiting for your
								// response
	private String[] pendingRes;// you added as friend and now waiting fr
								// response
	private String[] directory_names;
	// private String[] directory_emails;
	// private String[] directory_phones;
	// private String[] directory_address;
	private String[] directory_friendType;

	public String[] getPendingReq() {
		return pendingReq;
	}

	public void setPendingRequestsFromHD() {
		pendingReq = getResources().getStringArray(
				R.array.pending_request_array);
	}

	public void setPendingReq(String[] pendingReq) {
		this.pendingReq = pendingReq;
	}

	public String[] getPendingRes() {
		return pendingRes;
	}

	public void setPendingResponseFromHD() {
		pendingRes = getResources().getStringArray(
				R.array.pending_response_array);
	}

	public void setPendingRes(String[] pendingRes) {
		this.pendingRes = pendingRes;
	}



	public void setDirectoryFromHD() {

		directory_names = getResources()
				.getStringArray(R.array.directory_array);
		// directory_emails =
		// getResources().getStringArray(R.array.email_array);
		// directory_phones =
		// getResources().getStringArray(R.array.phone_array);
		// directory_address = getResources()
		// .getStringArray(R.array.address_array);
		directory_friendType = getResources().getStringArray(
				R.array.friend_type_array);
	}

	public String[] getDirectoryNames() {
		return directory_names;
	}

	// public String[] getDirectory_emails() {
	// return directory_friendType;
	// }
	//
	// public String[] getDirectory_phones() {
	// return directory_friendType;
	// }
	//
	// public String[] getDirectory_addresses() {
	// return directory_friendType;
	// }

	public String[] getDirectory_friendType() {
		return directory_friendType;
	}


	public void setDirectory_friendType(String[] directory_friendType) {
		this.directory_friendType = directory_friendType;
	}

	public String[] getFriends() {
		// String[] friends = new String[2];
		// friends[0] = "onefriends";
		// friends[1] = "lonely person";
		// friends = getResources().getStringArray(R.array.friends_array);
		return friends;
	}

	public void setFriendsFromHD() {
		friends = getResources().getStringArray(R.array.friends_array);

	}

	public void setFriends(String[] newFriends) {
		friends = newFriends;

	}


}
