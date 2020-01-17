<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createStudent();
}
function createStudent()
{
	global $connect;
	
	$id = $_POST["id"];	
	$price = $_POST["price1"];
	$name = $_POST["name"];	
	$wei = $_POST["weight"];
	$mdate=date("Y/m/d");
	
	
	$location = "Production Unit";
	
	
	$query = " Insert into sale(id, price, mfg_date, location, Weight, cust_id, pur_date,name) values ('$id','$price','$mdate','$location',$wei,'','','$name');";
	
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
	
}
?>