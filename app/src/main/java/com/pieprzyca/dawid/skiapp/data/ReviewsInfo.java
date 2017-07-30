package com.pieprzyca.dawid.skiapp.data;

/**
 * Created by Dawid on 19.07.2017.
 */

public class ReviewsInfo {
    private String review;
    private String reviewDate;
    private String reviewRating;

    public ReviewsInfo(String review, String reviewDate, String reviewRating) {
        this.review = review;
        this.reviewDate = reviewDate;
        this.reviewRating = reviewRating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }
}
