<?php
	$friend = $_GET["friendID"];
	$username = $_GET["username"];
	$firstname = $_GET["userFirstname"];
	$notif = "FriendReq:".username.":".firstname." Added You as friend";
	sendNotifTo($friend, $notif);
?>

<?php function sendNotifTo($friendname, $notif)
{
$file = "users/".$friendname."_notif.txt";
$stringData = "\n".$notif;
file_put_contents($file, $stringData, FILE_APPEND);
}
