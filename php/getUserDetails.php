<?php
$con=mysql_connect("localhost","root","");
mysql_select_db("egift",$con);
 
 
$id=$_REQUEST['a'];
$response=array();
$post=array();
 
 
$r=mysql_query("select * from user where ac_no='$id'");
$row=mysql_fetch_array($r);

print(json_encode($row));
mysql_close($con);
?>
 