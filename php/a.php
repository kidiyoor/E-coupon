<?php
$con=mysql_connect("localhost","root","");
mysql_select_db("egift",$con);
 
 
$id=$_REQUEST['a'];
$response=array();
//$post=array();
$out[]="";
 
$r=mysql_query("select * from coupons where uac_no='$id'");
while($row=mysql_fetch_array($r))
{
        $coup_name=$row['coup_name'];
	$expdate=$row['expdate'];
	$coup_id=$row['coup_id'];	
	
 
	$post[]=array('coup_name'=>$coup_name,'expdate'=>$expdate,'coup_id'=>$coup_id);
$out[]=$row ;
}
 
$response['feed']=$post;
print(json_encode($out));
mysql_close($con);
?>
 