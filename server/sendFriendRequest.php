<?php
	$friend = $_GET["friendemail"];
	$username = $_GET["username"];
	$firstname = $_GET["firstname"];
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