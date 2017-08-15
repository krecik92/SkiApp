<?php
    $connection = mysqli_connect("localhost", "id1391953_dawid_pieprzyca", "nadal21", "id1391953_skiappdatabase");
    if(isset($_POST['skiResortId']) &&
        isset($_POST['userId'])) {

        $user_id = intval($_POST['userId']);
        $skiResortId = intval($_POST['skiResortId']);
        
        $query = "DELETE FROM favouriteResorts WHERE user_id = '$user_id' AND skiResortId = '$skiResortId'";
        $result = mysqli_query($connection, $query);

        if($result > 0 ){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "failed after loop";
?>