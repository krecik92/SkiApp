<?php

    $con = mysqli_connect("localhost", "id1391953_dawid_pieprzyca", "nadal21", "id1391953_skiappdatabase");

    $sql = "SELECT * FROM skiResorts";
    $r = mysqli_query($con, $sql);
    $result = array();

    while($row = mysqli_fetch_array($r)){
        array_push($result,array(
            "name"=>$row[0],
            "isFavourite"=>$row[2]
            )
        );
    }
    header('Content-Type: application/json');
    echo json_encode(array("result"=>$result));
?>