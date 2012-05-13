<?php
	$file = $_GET["eventid"];
	$filename = "events/".$file.".txt";

	unlink($filename);
?>
