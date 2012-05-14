<?php
	$eventname = $_GET["eventname"];
	$firstname = $_GET["firstname"];
	$eventID = $_GET["eventID"];
	$friendname = $_GET["friendname"];
	$username = $_GET["username"];

	$notif = "ProposedChangeRejected::".$firstname." do not think your suggested change is feasible to event ".$eventname;
	sendNotifTo($friendname, $notif);
	$notif = "EventInvite:".$eventID.":".$firstname." invited you to ".$eventname." again";
	sendNotifTo($friendname, $notif);

	
?>

<?php function sendNotifTo($friendname, $notif)
{
$file = "users/".$friendname."_notif.txt";
$stringData = "\n".$notif;
file_put_contents($file, $stringData, FILE_APPEND);
}

?>