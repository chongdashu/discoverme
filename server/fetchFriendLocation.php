<?php
	$username = $_GET["username"];
	$filename = "users/".$username."_location.txt";
	//echo "helo";
	//echo $filename;
	$data = file_get_contents($filename);
	//echo $filename;
	echo $data;
?>