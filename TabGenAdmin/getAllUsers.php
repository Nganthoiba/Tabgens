<?php
	include('connect_db.php');
	include('tabgen_php_functions.php');// all the function/ methodes are in this php file
$token = get_token_from_header();
if($token==null){
	echo json_encode(array("status"=>false,"message"=>"Your request is unauthorized."));
}
else{	
	if($conn){
		$query = "select Users.*,OrganisationUnit,Organisation,UniversalAccess 
				from Users,User_OU_Mapping,OrganisationUnit
				where Users.Id=User_OU_Mapping.user_id
				and User_OU_Mapping.OU_id=OrganisationUnit.Id
                and Users.DeleteAt=0 order by Users.Username";
		$res = $conn->query($query);
		$output=null;
		if($res){
			while($row=$res->fetch(PDO::FETCH_ASSOC)){
				$output[]=$row;
			}
			echo json_encode(array("status"=>true,"result"=>$output));
		}
		else{
			echo json_encode(array("status"=>false,"message"=>"Unable to get result, something is wrong."));
		}
	}
	else{
		echo json_encode(array("status"=>false,"message"=>"Failed to connect database."));
	}
}
?>
