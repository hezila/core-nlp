package com.numb3r3.nlp.corpus;

import com.numb3r3.nlp.doc.ReviewDoc;

import java.util.List;

public interface ReviewDocCorpus extends DocCorpus {

    public ReviewDoc getReviewDoc(int index);

    public ReviewDoc getReviewDoc(String review_id);

    public int getReviewDocIndex(String review_id);

    public int getUserIndex(String user_id);

    public int getPrdIndex(String prd_id);

    public ReviewDoc getReviewDoc(String user_id, String prd_id);

    public ReviewDoc getReviewDoc(int uid, int pid);

    public double getOverallRating(String user_id, String prd_id);

    public double getOverallRating(int uid, int pid);

    public List<Integer> getReviewsByUser(String user_id);

    public List<Integer> getReviewsByPrd(String prd_id);

    public List<Integer> getReviewsByUser(int uid);

    public List<Integer> getReviewsByPrd(int pid);

    public int getReviewIndex(String user_id, String prd_id);

    public void addReviewDoc(ReviewDoc doc);

    public void addReviewDoc(String review_id, ReviewDoc doc);

    public double getRating(int index);

    public List<Double> getAspectRatings(int index);

    public int getUserSize();

    public int getPrdSize();

    public int getSize();

    public String summary();

}
