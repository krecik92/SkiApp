<?php
    $con = mysqli_connect("localhost", "id1391953_dawid_pieprzyca", "nadal21", "id1391953_skiappdatabase");

    $userName= isset($_POST['userName']) ? $_POST['userName'] : '';
    $password = isset($_POST['password']) ? $_POST['password'] : '';

    $statement = mysqli_prepare($con, "SELECT * FROM userInformation WHERE userName = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $userName, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $name, $userName, $email, $password);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["name"] = $name;
        $response["email"] = $email;
        $response["userName"] = $userName;
        $response["password"] = $password;
    }
    header('Content-Type: application/json');
    echo json_encode($response);
?>