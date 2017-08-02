<?php
    $con = mysqli_connect("localhost", "id1391953_dawid_pieprzyca", "nadal21", "id1391953_skiappdatabase");

    $skiResortId_string = isset($_POST['skiResortId']) ? $_POST['skiResortId'] : '0';
    $skiResortId = intval($skiResortId_string);

    $sql = "SELECT * FROM reviews WHERE skiResortId = $skiResortId";
    $r = mysqli_query($con, $sql);
    $result = array();

    while($row = mysqli_fetch_array($r)){
        array_push($result,array(
            "review"=>$row[1],
            "rating"=>$row[2],
            "date" =>$row[3]
            )
        );
    }
    header('Content-Type: application/json');
    echo json_encode(array("result"=>$result));
?>