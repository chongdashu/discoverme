<?php
	$username = $_GET["username"];
	$firstname = $_GET["firstname"];

	$file = $_GET["eventid"];
	$filename = "events/".$file;
	$ename = $_GET["eventname"];
	$part = $_GET["participants"];
	$rsvp = $_GET["rsvp"];
	$time = $_GET["time"];
	$loc = $_GET["location"];
	$lat = $_GET["locationLat"];
	$lng = $_GET["locationLng"];
	$type = $_GET["type"];
	$content = $ename.";".$part.";".$rsvp.";".$time.";".$loc.";".$lat.";".$lng.";".$type;
	$filename = $filename."_update.txt";

	$notif = "EventProposedChange:".$username.",".$eventid."_update:".$firstname." propsed a change to your event ".$ename;

	file_put_contents($filename, $content);
	echo $content;
?>

<?php function sendNotifTo($friend, $notif)
{
$file = "users/".$friend."_notif.txt";
$stringData = "\n".$notif;
file_put_contents($file, $stringData, FILE_APPEND);
}
?>