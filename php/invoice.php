<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createStudent();
}
function createStudent()
{
	global $connect;
	
	$cust_id= $_POST["name"];	
	$gstno = $_POST["price"];
	$cname= $_POST["cname"];
	$mdate=date("Y/m/d");
	$unit=100;
	$quantity=$_POST["quantity"];
	$total=$_POST["total"];
	
	$query = " Insert into invoice(cust_id, quantity, price_per_unit, total, date, gstno, cust_name) values ('$cust_id','$quantity',$unit,'$total','$mdate','$gstno','$cname');";
	
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);

	
}
?>