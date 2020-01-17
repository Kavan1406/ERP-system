<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createStudent();
}
function createStudent()
{
	global $connect;
	
	$name = $_POST["name"];	

	
	$query = " UPDATE sale SET location='Distributor' where id='$name'";
	
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
	
}


