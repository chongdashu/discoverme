<?php
	$eventID = $_GET["eventid"];
	$filename = "events/".$eventID.".txt";
	echo $filename."\n </br>";
	unlink($filename);
?>