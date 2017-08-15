<?php
    $con = mysqli_connect("localhost", "id1391953_dawid_pieprzyca", "nadal21", "id1391953_skiappdatabase");

    $skiResortId_string = isset($_POST['skiResortId']) ? $_POST['skiResortId'] : '0';
    $skiResortId = intval($skiResortId_string);

    $userId_string = isset($_POST['userId']) ? $_POST['userId'] : '0';
    $userId = intval($userId_string);

    $statement = mysqli_prepare($con, "SELECT * FROM favouriteResorts WHERE skiResortId = ? AND user_id = ?");
    mysqli_stmt_bind_param($statement, "ii", $skiResortId, $userId);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    $response = array();
        if(mysqli_stmt_fetch($statement)){
             $response["success"] = true;
        }
        else{
             $response["success"] = false;
        }
    header('Content-Type: application/json');
    echo json_encode($response);
?>