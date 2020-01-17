<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createStudent();
}
function createStudent()
{
	global $connect;
	
	$name = $_POST["name"];	//barcode
	$price = $_POST["price"];//Distributor id
	$mdate=date("Y/m/d");
	$dname="abcd";
	$dadd="101 xyz, changa, Anand";
	
	switch($price)
	{
			case "d1":
				$dname=" Shah";
				$dadd="101 dream villa, nadiad, gujarat";
				break;
				
			case "d2":
				$dname=" patel";
				$dadd="403 karmveer palace, jaipur, rajasthan";
				break;
				
			default:
				$dname=" patel";
				$dadd="desai vago, jaisalpur, goa";
	}
	
	$query = " Insert into dist(id, distid, date, dist_name, dist_add) values ('$name','$price','$mdate','$dname','$dadd');";
	
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
	
}