<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>descover me</title>
</head>

<body>
<?php
	$username = $_GET["username"];
	$filename = "users/".$username."_location.txt";
	//echo "helo";
	//echo $filename;
	$data = file_get_contents($filename);
	//echo $filename;
	echo $data;
?>

</body>
</html>
