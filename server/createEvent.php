<?php
	$file = $_GET["eventid"];
	$firstname= $_GET["firstname"];
	$filename = "events/".$file.".txt";
	$ename = $_GET["eventname"];
	$part = $_GET["participants"];
	$rsvp = $_GET["rsvp"];
	$time = $_GET["time"];
	$loc = $_GET["location"];
	$lat = $_GET["locationLat"];
	$lng = $_GET["locationLng"];
	$type = $_GET["type"];
	$content = $ename.";".$part.";".$rsvp.";".$time.";".$loc.";".$lat.";".$lng.";".$type;
	file_put_contents($filename, $content);
	echo $content;

	$partArr = explode(",",$part);
	for($i=1;$i<count($partArr)-1;$i=$i+1)
	{
		$notif = "EventInvite:".$file.":".$firstname." invited you to ".$ename;
		$friend = trim($partArr[$i]);
		sendNotifTo($friend, $notif);


	}
?>

<?php function sendNotifTo($friend, $notif)
{
$file = "users/".trim($friend)."_notif.txt";
$stringData = "\n".$notif;
file_put_contents($file, $stringData, FILE_APPEND);
}
?>