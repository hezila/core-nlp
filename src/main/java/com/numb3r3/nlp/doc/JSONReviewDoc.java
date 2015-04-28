package com.numb3r3.nlp.doc;

import com.google.common.collect.Lists;
import com.numb3r3.nlp.doc.local.InMemoryReviewDoc;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;


public class JSONReviewDoc {
    public static JSONObject json_parse(ReviewDoc review) {
        JSONObject json_doc = new JSONObject();

        String review_id = review.getReviewId();
        if (review_id != null) json_doc.put("review_id", review_id);

        String user_id = review.getUserId();
        if (user_id != null) json_doc.put("user_id", user_id);

        String product_id = review.getProductId();
        if (product_id != null) json_doc.put("product_id", product_id);

        double rating = review.getOverallRating();
        json_doc.put("overall_rating", rating);


        Document doc = review.getDoc();
        if (doc != null) json_doc.put("doc", JSONDoc.json_parse(doc));

        List<Double> ratings = review.getAspectRatings();
        if (ratings != null) {
            JSONArray rating_array = new JSONArray();
            for (double r : ratings) {
                rating_array.add(r);
            }
            json_doc.put("aspect_ratings", rating_array);
        }

        long date = review.getDate();
        if (date > 0) json_doc.put("date", date);

        return json_doc;
    }

    public static String dumps(ReviewDoc review) {
        return json_parse(review).toString();
    }

    public static ReviewDoc json_load(JSONObject json_doc) {
        String review_id = null;
        if (json_doc.containsKey("review_id")) review_id = json_doc.get("review_id").toString();

        Document doc = null;
        if (json_doc.containsKey("doc")) doc = JSONDoc.load_json((JSONObject) json_doc.get("doc"));

        ReviewDoc review = new InMemoryReviewDoc(review_id, doc);

        double rating = Double.parseDouble(json_doc.get("overall_rating").toString());
        review.setOverallRating(rating);

        if (json_doc.containsKey("user_id")) review.setUserId(json_doc.get("user_id").toString());

        if (json_doc.containsKey("product_id")) review.setProductId(json_doc.get("product_id").toString());

        if (json_doc.containsKey("date")) {
            long date = Long.parseLong(json_doc.get("date").toString());
            review.setDate(date);
        }

        if (json_doc.containsKey("aspect_ratings")) {
            JSONArray rating_array = (JSONArray) json_doc.get("aspect_ratings");
            List<Double> ratings = Lists.newArrayList();
            for (int i = 0; i < rating_array.size(); i++) {
                ratings.add(Double.parseDouble(rating_array.get(i).toString()));
            }
            review.setAspectRatings(ratings);
        }
        return review;
    }
}
