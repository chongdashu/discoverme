<?php

	$friend = $_GET["friendemail"];
	$username = $_GET["username"];
	$firstname = $_GET["firstname"];
	insertFriend($friend, $username);
	insertFriend($username, $friend);
	$notif = "FriendRes:".$username.":".$firstname." Added You as friend";
	echo "</br>".$notif."</br>";
	sendNotifTo($friend, $notif);
?>
<?php function sendNotifTo($friend, $notif)
{
$file = "users/".$friend."_notif.txt";
$stringData = "\n".$notif;
file_put_contents($file, $stringData, FILE_APPEND);
}
?>
<?php
function insertFriend($friend, $username)
{
	//$friend = $_GET["friendID"];
	//$username = $_GET["username"];
	$filename = "users/".$username."_friends.txt";
	$mydata = file_get_contents($filename);
	echo "</br>friend is ".$friend;
	echo "</br>user is ".$username;
	$directory = "directory.txt";
	$data = file_get_contents($directory);
	
	$pieces = explode("\n", $data);
	echo "</br>".count($pieces);
	$rowToAdd = -1;
	$content = "";
	for($i = 0 ; $i <count($pieces); $i = $i +1)
	{
		$pos = strpos($pieces[$i], $friend);
		if($pos==false)
		{}
		else 
		$rowToAdd = $i;
	}
	if($row!= -1)
		$mydata=$mydata."\n".$pieces[$rowToAdd];
	echo "</br>".$mydata;
	echo "</br>".$rowToAdd;
	file_put_contents($filename, $mydata);
	echo "</br>".$filename." taking in ".$mydata."</br>";
	
}
?>