<?php

    $connection = mysqli_connect("localhost", "id1391953_dawid_pieprzyca", "nadal21", "id1391953_skiappdatabase");
    if(isset($_POST['skiResortId']) &&
        isset($_POST['review']) &&
            isset($_POST['rating'])){
        $skiResortId = intval($_POST['skiResortId']);
        $review = $_POST['review'];
        $rating = floatval($_POST['rating']);

        $query = "INSERT INTO reviews(skiResortId, review, rating, date) VALUES ('$skiResortId', '$review', '$rating', NOW())";
        $result = mysqli_query($connection, $query);

        if($result > 0 ){
            echo "success";
        }
        else{
            echo "failed";
        }

    }
    echo "failed";
?>