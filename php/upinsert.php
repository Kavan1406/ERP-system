<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createStudent();
}
function createStudent()
{
	global $connect;
	
	$name = $_POST["name"];
	$price = $_POST["price"];
	$pdate=date("Y/m/d");

	
	$query = " UPDATE sale SET cust_id='$price', pur_date='$pdate' where id='$name'";
	
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
	
}