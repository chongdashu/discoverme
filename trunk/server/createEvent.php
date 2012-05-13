<?php
	$filename = $_GET["eventid"];
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
?>
