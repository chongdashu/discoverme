<?php
	$username = $_GET["username"];
	$firstname = $_GET["firstname"];
	$eventname = $_GET["eventname"];
	$eventID = $_GET["eventID"];
	$rsvp = $_GET["rsvp"];
	$filename = "events/".$eventID.".txt";
	//echo "helo";
	echo $filename."\n </br>";
	$data="";
	$data = file_get_contents($filename);
	$pieces = null;
	$pieces = explode(";", $data);
	
	//echo count($pieces)."</br>";
	$participants = explode(",",$pieces[1]);
	$rsvps = explode(",",$pieces[2]);
	$newrsvp="";
	$newevent="";
	$notif="";
	if(strcmp($rsvp,"yes")==0)
		$notif = "EventAccepted:".$username.",".$eventID.":".$firstname." accepted ur invitation to ".$eventname;
	else 	
		$notif = "EventDeclined:".$username.",".$eventID.":".$firstname." declined ur invitation to ".$eventname;
		
	
	for($i=0;$i<count($participants);$i=$i+1)
		{
		if(strcmp(trim($participants[$i]),trim($username))==0){
			$newrsvp=$newrsvp.$rsvp;
			echo "found";
			}
		else{
			echo "ney";
			$newrsvp=$newrsvp.$rsvps[$i];
			sendNotifTo($participants[$i],$notif);
			}
		echo "\n</br>".$participants[$i].";".$username;
		if($i!=count($participants)-1)
			$newrsvp=$newrsvp.",";
		}
	
	
	for($i=0;$i<count($pieces);$i=$i+1)
		if($i==2)
			$newevent=$newevent.$newrsvp.";";
		else if($i==count($pieces)-1)
			$newevent=$newevent.$pieces[$i];
		else
			$newevent=$newevent.$pieces[$i].";";		
	
	//echo "new event is now :".$newevent."</br>";
	
	
	//echo "number of partiicpants = ".count($participants)."</br>";
	//echo "number of rsvps = ".count($rsvps)."</br>";
	
	echo $username."\n </br>";
	echo $eventID."\n </br>";
	echo $rsvp."\n </br>";
	echo $data."\n </br>";
	//echo $stringData."\n </br>";
	file_put_contents($filename, $newevent);

	//$data = file_get_contents($filename);
	//echo "\n</br>".$filename;
	//echo $data."\n</br>";


?>

<?php function sendNotifTo($friendname, $notif)
{
$file = "users/".$friendname."_notif.txt";
$stringData = "\n".$notif;
file_put_contents($file, $stringData, FILE_APPEND);
}

?>