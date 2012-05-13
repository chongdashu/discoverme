<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>descover me</title>
</head>

<body>
<?php
	$text = $_GET["searchKey"];
	$file = $_GET["file"];
	$filename = $text."_".$file;
	//echo "helo";
	//echo $filename;
	$data = file_get_contents($file);
	echo $data;

?>

</body>
</html>
