<?php
	$username = $_GET["username"];
	$filename = "users/".$username."_notif.txt";
	$data="";
	$data = file_get_contents($filename);
	echo $data;
	$string = "";
	file_put_contents($filename, $string);
?>