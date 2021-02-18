<?php
/* ===== www.dedykuncoro.com ===== */
	require 'koneksi.php';
	



	$username = $_POST["username"];
	$email = $_POST["email"];
	$password = $_POST["password"];
	$mobile = $_POST["mobile"];
	$gender = $_POST["gender"];

	// $username = "sa2dasyasad";
	// $email = "nvsas2asdda@gmail.com";
	// $password = "1234567";
	// $mobile = "12345666";
	// $gender = "laki-laki";


	$isValidEmail = filter_var($email, FILTER_VALIDATE_EMAIL);
	if($con){
		
		if ($isValidEmail===false) {
			echo "This Email is not valid";
		}
		else{
			$sqlCheckUsername = "SELECT * from users WHERE username LIKE '$username'";

			$usernameQuery = mysqli_query($con,$sqlCheckUsername); 

			$sqlCheckEmail = "SELECT * from users WHERE email LIKE '$email' ";
			$emailQuery = mysqli_query($con,$sqlCheckEmail); 

			if (mysqli_num_rows($usernameQuery) > 0) {

				echo "username already used, type another one";
			}
			elseif (mysqli_num_rows($emailQuery) >0) {
				echo "email already used, type another Email";
			}

			else{
				$sql_register = "INSERT INTO users VALUES ('','$username','$email','$password','$mobile','$gender')";

				if (mysqli_query($con,$sql_register)) {
					echo "successfully registered";
				}
				else
				{
					echo "failed to register";
				}
			}
		}
	}
	else{
		echo "Connetion Error";
	}

?>	


 