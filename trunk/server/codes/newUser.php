<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>descover me</title>
</head>

<body>
<?php
	$username = $_GET["username"];
	$filename = "users/".$username."_friends.txt";
	$string="";
	file_put_contents($filename, $string);
	echo "\n</br>".$filename;
	$filename = "users/".$username."_notif.txt";
	file_put_contents($filename, $string);
	echo "\n</br>".$filename;
	$filename = "users/".$username."_location.txt";
	file_put_contents($filename, $string);
	echo "\n</br>".$filename;
	echo $data."\n</br>";
?>

</body>
</html>
