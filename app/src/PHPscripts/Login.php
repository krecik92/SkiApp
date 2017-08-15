<?php
    $con = mysqli_connect("localhost", "id1391953_dawid_pieprzyca", "nadal21", "id1391953_skiappdatabase");

    $email = isset($_POST['email']) ? $_POST['email'] : '0';
    $password = isset($_POST['password']) ? $_POST['password'] : '0';

    $statement = mysqli_prepare($con, "SELECT * FROM userInformation WHERE email = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $name, $surname, $email, $password);

    $response = array();

    if(mysqli_stmt_fetch($statement)){
         $response["success"] = true;
         $response["user_id"] = $userID;
         $response["name"] = $name;
         $response["surname"] = $surname;
    }
    else{
         $response["success"] = false;
    }

    header('Content-Type: application/json');
    echo json_encode($response);
?>