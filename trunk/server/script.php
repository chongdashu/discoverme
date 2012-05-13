<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>descover me</title>
</head>

<body>
<?php
      
echo "helo";



?>

hi there

</body>
</html>

<?php 
function http_file_exists($url) 
{ 
$f=@fopen($url,"r"); 
if($f) 
{ 
fclose($f); 
return true; 
} 
return false; 
} 
?>
