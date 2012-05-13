<?php
	$friend = $_GET["friendID"];
	$username = $_GET["username"];
	removeFriend($friend,$username);
	removeFriend($username,$friend);
?>
<?php
function removeFriend($friend, $username)
{
	$filename = "users/".$username."_friends.txt";
	$data = file_get_contents($filename);
	$pieces = explode("\n", $data);
	echo count($pieces);
	$rowToDelete = 0;
	$content = "";
	for($i = 0 ; $i <count($pieces); $i = $i +1)
	{
		$pos = strpos($pieces[$i], $friend);
		if($pos==false)
		{}
		else 
		$rowToDelete = $i;
			
	}
	for($i = 0 ; $i <count($pieces); $i = $i +1)
	{
		if($i==$rowToDelete)
		{
		}
		else 
		$content.=$pieces[$i];
			
	}
	echo "</br>".$data;
	echo "</br>".$rowToDelete;
	echo "</br>".$content;
	file_put_contents($filename, $content);
	
}


?>