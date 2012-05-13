<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>descover me</title>
</head>

<body>
<?php
	$text = $_GET["searchKey"];
	$file = $_GET["file"];
	$content = $_GET["content"];
	$filename = $text."_".$file;
	//echo "helo";
	//echo $filename;
	$f=file_put_contents($file, $content);
?>

</body>
</html>
