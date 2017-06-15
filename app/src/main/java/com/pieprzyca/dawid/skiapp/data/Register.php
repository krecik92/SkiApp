<?php
    $con = mysqli_connect("localhost", "id1391953_dawid_pieprzyca", "nadal21", "id1391953_skiappdatabase");

    $name = isset($_POST['name']) ? $_POST['name'] : '';
    $email = isset($_POST['email']) ? $_POST['email'] : '';
    $userName = isset($_POST['userName']) ? $_POST['userName'] : '';
    $password = isset($_POST['password']) ? $_POST['password'] : '';
    $statement = mysqli_prepare($con, "INSERT INTO userInformation (name, email, userName, password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssss", $name, $email, $userName, $password);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;
    header('Content-Type: application/json');
    echo json_encode($response);
?>