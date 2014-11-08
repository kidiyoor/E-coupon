<?php

$con = mysql_connect('localhost','root','');
header('Content-type: application/json');

if (mysql_select_db("egift",$con))
{
//echo "connected";
}


$acc=$_REQUEST['acc'];
$cname=$_REQUEST['cname'];
$uname=$_REQUEST['uname'];
$pass=$_REQUEST['pass'];
$amount=$_REQUEST['amount'];
$email=$_REQUEST['email'];
$phno=$_REQUEST['phno'];
$time=$_REQUEST['time'];

function generateUniqueToken($number)
{
    $arr = array('a', 'b', 'c', 'd', 'e', 'f',
                 'g', 'h', 'i', 'j', 'k', 'l',
                 'm', 'n', 'o', 'p', 'r', 's',
                 't', 'u', 'v', 'x', 'y', 'z',
                 'A', 'B', 'C', 'D', 'E', 'F',
                 'G', 'H', 'I', 'J', 'K', 'L',
                 'M', 'N', 'O', 'P', 'R', 'S',
                 'T', 'U', 'V', 'X', 'Y', 'Z',
                 '1', '2', '3', '4', '5', '6',
                 '7', '8', '9', '0');
    $token = "";
    for ($i = 0; $i < $number; $i++) {
        $index = rand(0, count($arr) - 1);
        $token .= $arr[$index];
    }

    if (false) {
        return generateUniqueToken($number);
    } else {
        return $token;
    }
}





$output=array();
// check if amount available


$sql0 = "select * from user where name='$uname' and pass ='$pass'";
$sql1 = mysql_query($sql0);

$n=mysql_num_rows($sql1);
if($n!=1)
{
$output['a']="error authenticating";
}
else
{
$data=mysql_fetch_array($sql1);
	if($data[4]>intval($amount))
	{
		//reduce balance
		$t=intval($amount);
//		echo $t;
		$sql0 = "update user set ac_bal=ac_bal-$t where ac_no=$acc";
		$sql1 = mysql_query($sql0);
				
		//insert into coupon table
		$key=genKey();
//		echo "::::::";
		$mobKey=genMob();
//		echo "::::::";
		
//		echo $cname;
//		echo "::::::";
//		echo $key;
//		echo "::::::";
//		echo $mobKey;
//echo "::::::";		
		//echo $acc;
		//echo "::::::";
		$a=intval($amount);
		//echo $a;
		//echo "::::::";
		//echo $phno;
		//echo "::::::";
		//echo $email;
		//$sql0 = "insert into coupons ('coup_name','uac_no','amount','ekey','pkey','semail','sphone') values ('{$cname}','{$acc}',100,'{$key}','{$mobKey}','{$email}','{$phno}')";
		$sql0 ="INSERT INTO `coupons` (`coup_name`, `uac_no`, `amount`, `ekey`, `pkey`, `expdate`, `semail`, `sphone`) VALUES ( '$cname', '$acc', $a, '$key', '$mobKey','$time', '$email', '$phno')";
		//echo "::::::";
		$sql1 = mysql_query($sql0);
		//echo $sql1;	
		//echo "end of 1";
	
		$output['a']="success";
		$output['b']=$key;
		$output['c']=$mobKey;
		$output['d']=$time;
		$output['e']=$a;


	}
	else
	{
		$output['a']="Low Balance";
	}


}



//while($r=mysql_fetch_assoc($sql1))
//{
//$row[] = $r;
//}

//select on algorithm


function genKey()
{
$sql0 = "select * from coupons";
$sql1 = mysql_query($sql0);
$n=mysql_num_rows($sql1);

$k=generateUniqueToken(10);

return($k.strval($n-1));
//return "rt";
}

function genMob()
{
$sql0 = "select * from coupons";
$sql1 = mysql_query($sql0);
$n=mysql_num_rows($sql1);

$k=generateUniqueToken(4);
//return "rrrr";
return(strval($n-1).$k);
}
//echo '{';

print(json_encode($output));

//echo "'0':'ff'";
//echo '}';
mysql_close();

?>	