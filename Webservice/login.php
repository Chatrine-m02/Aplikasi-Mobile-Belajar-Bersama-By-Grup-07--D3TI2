<?php
	include 'koneksi.php';
	
	
	
	$email = $_POST["email"];
	$password = $_POST["password"];

	// $email = "nvs@gmail.com";
	// $password = "1234567";
	$isValidEmail = filter_var($email, FILTER_VALIDATE_EMAIL);
	if ($con) {
		if ($isValidEmail === 0) {
			echo "Email is not valid";
		}
		else{
			$sqlCheckEmail = "SELECT * from users WHERE email LIKE '$email' ";
			$usernameQuery = mysqli_query($con,$sqlCheckEmail); 

			if(mysqli_num_rows($usernameQuery)>0){
				$sqlLogin = "SELECT * from users WHERE email = '$email' and password = '$password' ";
				$loginQuery = mysqli_query($con,$sqlLogin);

				if (mysqli_num_rows($loginQuery)>0) {
					echo "Loggin Success";
					
				}
				else{
					echo "Wrong password";
				}
			}
			else{
				echo "This email not registered";
			}
		}
	}

	else{
		echo "Koneksi error";
	}

	// $koneksi = mysqli_connect("localhost","root","");
	// $database = mysqli_select_db($koneksi,"tkpembinaporsea");

	// $username = $_POST['email'];
	// $password = $_POST['password'];

	

	// $result = mysqli_query($koneksi,"SELECT * FROM users WHERE email='$username' AND password='$password' ");

	// $row = mysqli_fetch_array($result);
	// $data = $row[0];

	// if ($data) {
	// 	echo "Login Berhasil";
	// }

	// else {
	// 	$data = "username atau password tidak benar";
	// }

?>


