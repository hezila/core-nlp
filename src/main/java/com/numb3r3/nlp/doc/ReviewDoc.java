package com.numb3r3.nlp.doc;

import java.util.List;


public interface ReviewDoc extends Document {

    public String getUserId();

    public void setUserId(final String user_id);

    public String getProductId();

    public void setProductId(final String product_id);

    public String getReviewId();

    public void setReviewId(final String review_id);

    public Document getDoc();

    public void setDoc(Document doc);

    public double getOverallRating();

    public void setOverallRating(double rating);

    public long getDate();

    public void setDate(long datetime);

    public List<Double> getAspectRatings();

    public void setAspectRatings(List<Double> ratings);
}
