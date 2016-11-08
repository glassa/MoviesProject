<?php
	$dsn = 'mysql:host=cssgate.insttech.washington.edu;dbname=glassa';
    	$username = 'glassa';
    	$password = 'Astuleo';
    	try {
        	$db = new PDO($dsn, $username, $password);
            $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			
			$user_id = isset($_GET['id']) ? $_GET['id'] : '';
            $user_name = isset($_GET['name']) ? $_GET['name'] : '';
            $user_pw = isset($_GET['pw']) ? $_GET['pw'] : '';
			
			if (strlen($user_id) < 7 
                    || strlen($user_name) < 1 
                    || strlen($user_pw) < 1  {
                echo '{"result": "fail", "error": "Please enter valid data."}';
            } else {    

                //build query
                $sql = "INSERT INTO User";
                $sql .= " VALUES ('$user_id', '$user_name', '$user_pw')";
             
                //attempts to add record
                if ($db->query($sql)) {
                    echo '{"result": "success"}';
                    $db = null;
                } 
            }   
        } catch(PDOException $e) {
                if ((int)($e->getCode()) == 23000) {
                    echo '{"result": "fail", "error": "That course already exists."}';
                } else {
                    echo 'Error Number: ' . $e->getCode() . '<br>';
                    echo '{"result": "fail", "error": "Unknown error (' . (((int)($e->getCode()) + 123) * 2) .')"}';
                }
        }
?>