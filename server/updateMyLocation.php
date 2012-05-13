<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>descover me</title>
</head>

<body>
<?php
	$username = $_GET["username"];
	$location = $_GET["location"];
	$lat = $_GET["locationLat"];
	$lng = $_GET["locationLng"];
	$filename = "users/".$username."_location.txt";
	$stringData = $location.";".$lat.";".$lng;
	//echo "helo";
	//echo $filename;
	//$data = file_get_contents($filename);
	echo $filename."\n </br>";
	//echo $data."\n </br>";
	echo $stringData."\n </br>";
	
	file_put_contents($filename, $stringData);

	$data = file_get_contents($filename);
	echo "\n</br>".$filename;
	echo $data."\n</br>";


?>

</body>
</html>
