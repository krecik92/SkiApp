package com.pieprzyca.dawid.skiapp.data;

/**
 * Created by Dawid on 18.06.2017.
 *
 * Klasa zawiera linki URL do skryptów PHP które umożliwiają połączenie z bazą dancyh or operacje na niej. Takie jak dodawania, usuwanie czu pobieranie wartości.
 * Znajdują się w niej również nazwy kolumn z niektórych tabel.
 */
public class DatabaseConfig {
    public static final String ALL_SKIRESORTS_REQUEST_URL = "http://dawidpieprzyca.hostei.com/skiResortNameRequest.php";
    public static final String REVIEW_RATING_REQUEST_URL = "http://dawidpieprzyca.hostei.com/reviewAndRatingRequest.php";
    public static final String INSERT_RATING_REQUEST_URL = "https://daveee9.000webhostapp.com/insertNewReview.php";
    public static final String FAVOURITE_SKIRESORTS_URL = "https://daveee9.000webhostapp.com/favouriteSkiResortsRequest.php";
    public static final String IS_FAVOURITE_URL = "https://daveee9.000webhostapp.com/isFavouriteResortRequest.php";
    public static final String ADD_FAVOURITE_URL = "https://daveee9.000webhostapp.com/AddFavouriteResort.php";
    public static final String REMOVE_FAVOURITE_URL = "https://daveee9.000webhostapp.com/DeleteFavouriteResort.php";
    public static final String JSON_ARRAY = "result";
    public static final String SKIRESORTS_KEY_NAME = "name";
    public static final String SKI_R_ID = "skiResortId";
    public static final String SKIRESORTS_KEY_ADDRESS = "address";
}
