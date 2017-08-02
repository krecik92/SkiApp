<?php
    $con = mysqli_connect("localhost", "id1391953_dawid_pieprzyca", "nadal21", "id1391953_skiappdatabase");

    $name = isset($_POST['name']) ? $_POST['name'] : '';
    $email = isset($_POST['email']) ? $_POST['email'] : '';
    $surname = isset($_POST['surname']) ? $_POST['surname'] : '';
    $password = isset($_POST['password']) ? $_POST['password'] : '';
    $statement = mysqli_prepare($con, "INSERT INTO userInformation (name, surname, email, password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssss", $name, $surname, $email, $password);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;
    header('Content-Type: application/json');
    echo json_encode($response);
?>