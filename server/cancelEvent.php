<?php
	$eventID = $_GET["eventid"];
	$username = $_GET["username"];
	$filename = "events/".$eventID.".txt";
	echo $filename."\n </br>";
	$data="";
	$data = file_get_contents($filename);
	//unlink($filename);
	
	$pieces = null;
	$pieces = explode(";", $data);
	
	echo count($pieces)."</br>";
	$participants = explode(",",$pieces[1]);
	$notif = "EventCanceled:".$eventID.":".$pieces[0]." is canceled";
echo $notif;
			for($i=0; $i<count($participants);$i=$i+1)
		{
		if(strcmp(trim($participants[$i]),trim($username))==0){
			echo "found";
			}
		else{
			echo "ney";
			sendNotifTo($participants[$i],$notif);
			}
		}
?>

<?php function sendNotifTo($friendname, $notif)
{
$file = "users/".$friendname."_notif.txt";
$stringData = "\n".$notif;
file_put_contents($file, $stringData, FILE_APPEND);
}

?>