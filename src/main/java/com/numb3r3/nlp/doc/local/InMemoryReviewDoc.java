package com.numb3r3.nlp.doc.local;


import com.numb3r3.nlp.doc.Document;
import com.numb3r3.nlp.doc.ReviewDoc;

import java.util.List;


public class InMemoryReviewDoc implements ReviewDoc {

    private String reviewId = null;
    private String userId = null;
    private String productId = null;
    private long date = 0;
    private Document doc = null;

    private double overall_rating = -1;
    private List<Double> aspect_ratings = null;

    public InMemoryReviewDoc(String reviewId) {
        this(reviewId, null, -1);
    }

    public InMemoryReviewDoc(String reviewId, Document doc) {
        this(reviewId, doc, -1.0);
    }

    public InMemoryReviewDoc(String reviewId, Document doc, double rating) {
        this.reviewId = reviewId;
        this.doc = doc;
        this.overall_rating = rating;
    }

    @Override
    public Document getDoc() {
        return this.doc;
    }

    @Override
    public void setDoc(Document doc) {
        this.doc = doc;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(final String user_id) {
        this.userId = user_id;
    }

    @Override
    public String getProductId() {
        return this.productId;
    }

    @Override
    public void setProductId(final String product_id) {
        this.productId = product_id;
    }

    @Override
    public String getReviewId() {
        return this.reviewId;
    }

    @Override
    public void setReviewId(final String reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public double getOverallRating() {
        return this.overall_rating;
    }

    @Override
    public void setOverallRating(final double rating) {
        this.overall_rating = rating;
    }

    @Override
    public long getDate() {
        return this.date;
    }

    @Override
    public void setDate(final long date) {
        this.date = date;
    }

    @Override
    public List<Double> getAspectRatings() {
        return this.aspect_ratings;
    }

    public void setAspectRatings(List<Double> ratings) {
        this.aspect_ratings = ratings;
    }

    @Override
    public String getDocID() {
        return this.reviewId;
    }

    @Override
    public int getDocLength() {
        return this.doc.getDocLength();
    }

    @Override
    public int getWord(int index) {
        return this.doc.getWord(index);
    }

    @Override
    public List<Integer> getWords() {
        return this.doc.getWords();
    }

    @Override
    public int getWordCount(int w) {
        // TODO: to implemented
        return -1;
    }

    @Override
    public List<Integer> getSentence(int sent_offset) {
        return this.doc.getSentence(sent_offset);
    }

    @Override
    public List<List<Integer>> getSentences() {
        return this.doc.getSentences();
    }

    @Override
    public List<Integer> getBreaks() {
        return this.doc.getBreaks();
    }

    @Override
    public int getSentNum() {
        return this.doc.getSentNum();
    }

    @Override
    public void addSent(List<Integer> words) {
        this.doc.addSent(words);
    }
}
