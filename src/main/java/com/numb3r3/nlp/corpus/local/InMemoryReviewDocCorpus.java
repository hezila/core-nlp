package com.numb3r3.nlp.corpus.local;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.numb3r3.nlp.corpus.ReviewDocCorpus;
import com.numb3r3.nlp.doc.Document;
import com.numb3r3.nlp.doc.ReviewDoc;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryReviewDocCorpus implements ReviewDocCorpus {
    private List<ReviewDoc> docs = Lists.newArrayList();

    private BiMap<String, Integer> indexer = HashBiMap.create();
    private BiMap<Integer, String> inverse_indexer = indexer.inverse();

    private BiMap<String, List<Integer>> user_indexer = HashBiMap.create();
    private BiMap<String, Integer> user_maps = HashBiMap.create();
    private BiMap<Integer, String> inverse_user_maps = user_maps.inverse();

    private BiMap<String, List<Integer>> prd_indexer = HashBiMap.create();
    private BiMap<String, Integer> prd_maps = HashBiMap.create();
    private BiMap<Integer, String> inverse_prd_maps = prd_maps.inverse();

    private Map<String, Map<String, Integer>> maps = Maps.newHashMap();

    public InMemoryReviewDocCorpus() {
    }


    @Override
    public ReviewDoc getReviewDoc(int index) {
        return this.docs.get(index);
    }

    @Override
    public ReviewDoc getReviewDoc(String review_id) {
        if (this.indexer.containsKey(review_id)) {
            int index = this.indexer.get(review_id);
            return this.getReviewDoc(index);
        }
        return null;
    }

    @Override
    public int getReviewDocIndex(String review_id) {
        if (this.indexer.containsKey(review_id)) {
            int index = this.indexer.get(review_id);
            return index;
        }
        return -1;
    }

    @Override
    public int getUserIndex(String user_id) {
        return this.user_maps.get(user_id);
    }

    @Override
    public int getPrdIndex(String prd_id) {
        return this.prd_maps.get(prd_id);
    }

    @Override
    public int getReviewIndex(String user_id, String prd_id) {
        if (this.maps.containsKey(user_id)) {
            if (this.maps.get(user_id).containsKey(prd_id)) {
                return this.maps.get(user_id).get(prd_id);

            }
        }
        return -1;
    }

    public ReviewDoc getReviewDoc(String user_id, String prd_id) {
        int index = this.getReviewIndex(user_id, prd_id);
        if (index >= 0) {
            return this.getReviewDoc(index);
        }
        return null;
    }

    @Override
    public ReviewDoc getReviewDoc(int uid, int pid) {
        String user_id = this.inverse_user_maps.get(uid);
        String prd_id = this.inverse_prd_maps.get(pid);

        return this.getReviewDoc(user_id, prd_id);
    }

    @Override
    public double getOverallRating(String user_id, String prd_id) {
        ReviewDoc review = this.getReviewDoc(user_id, prd_id);
        if (review != null) {
            return review.getOverallRating();
        }
        return -1.0;
    }

    @Override
    public double getOverallRating(int uid, int pid) {
        ReviewDoc review = this.getReviewDoc(uid, pid);
        if (review != null) {
            return review.getOverallRating();
        }
        return -1.0;
    }

    @Override
    public List<Integer> getReviewsByUser(String user_id) {
        if (this.user_indexer.containsKey(user_id)) {
            return this.user_indexer.get(user_id);
        }
        return new ArrayList<Integer>();
    }

    @Override
    public List<Integer> getReviewsByUser(int uid) {
        String user_id = this.inverse_user_maps.get(uid);
        return this.getReviewsByUser(user_id);
    }

    @Override
    public List<Integer> getReviewsByPrd(String prd_id) {
        if (this.prd_indexer.containsKey(prd_id)) {
            return this.prd_indexer.get(prd_id);
        }
        return new ArrayList<Integer>();
    }

    @Override
    public List<Integer> getReviewsByPrd(int pid) {
        String prd_id = this.inverse_prd_maps.get(pid);
        return this.getReviewsByPrd(prd_id);
    }


    @Override
    public void addReviewDoc(ReviewDoc doc) {
        String review_id = doc.getReviewId();
        int index = this.size();
        if (review_id != null) {
            this.indexer.put(review_id, index);
        }

        this.docs.add(doc);

        String user_id = doc.getUserId();
        int uid = -1;
        if (user_maps.containsKey(user_id)) {
            uid = user_maps.get(user_id);
        } else {
            uid = user_maps.size();
            user_maps.put(user_id, uid);
        }

        String prd_id = doc.getProductId();
        int pid = -1;
        if (prd_maps.containsKey(prd_id)) {
            pid = prd_maps.get(prd_id);
        } else {
            pid = prd_maps.size();
            prd_maps.put(prd_id, pid);
        }

        if (user_indexer.containsKey(user_id)) {
            List<Integer> list = user_indexer.get(user_id);
            if (!list.contains(index)) user_indexer.get(user_id).add(index);
        } else {
            List<Integer> list = Lists.newArrayList();
            list.add(index);
            user_indexer.put(user_id, list);
        }

        if (prd_indexer.containsKey(prd_id)) {
            List<Integer> list = prd_indexer.get(prd_id);
            if (!list.contains(index)) prd_indexer.get(prd_id).add(index);
        } else {
            List<Integer> list = Lists.newArrayList();
            list.add(index);
            prd_indexer.put(prd_id, list);
        }

        if (this.maps.containsKey(user_id)) {
            this.maps.get(user_id).put(prd_id, index);
        } else {
            this.maps.put(user_id, new HashMap<String, Integer>());
            this.maps.get(user_id).put(prd_id, index);
        }
    }


    @Override
    public void addReviewDoc(String review_id, ReviewDoc doc) {
        int index = -1;
        if (indexer.containsKey(review_id)) {
            index = indexer.get(review_id);
            this.docs.set(index, doc);
            // TODO: maybe some index updating operations are needed in this branch
        } else {
            index = this.size();
            this.indexer.put(review_id, index);
            this.docs.add(doc);

            String user_id = doc.getUserId();
            int uid = -1;
            if (user_maps.containsKey(user_id)) {
                uid = user_maps.get(user_id);
            } else {
                uid = user_maps.size();
                user_maps.put(user_id, uid);
            }

            String prd_id = doc.getProductId();
            int pid = -1;
            if (prd_maps.containsKey(prd_id)) {
                pid = prd_maps.get(prd_id);
            } else {
                pid = prd_maps.size();
                prd_maps.put(prd_id, pid);
            }

            if (user_indexer.containsKey(user_id)) {
                List<Integer> list = user_indexer.get(user_id);
                if (!list.contains(index)) user_indexer.get(user_id).add(index);
            } else {
                List<Integer> list = Lists.newArrayList();
                list.add(index);
                user_indexer.put(user_id, list);
            }

            if (prd_indexer.containsKey(prd_id)) {
                List<Integer> list = prd_indexer.get(prd_id);
                if (!list.contains(index)) prd_indexer.get(prd_id).add(index);
            } else {
                List<Integer> list = Lists.newArrayList();
                list.add(index);
                prd_indexer.put(prd_id, list);
            }
            if (this.maps.containsKey(user_id)) {
                this.maps.get(user_id).put(prd_id, index);
            } else {
                this.maps.put(user_id, new HashMap<String, Integer>());
                this.maps.get(user_id).put(prd_id, index);
            }
        }

    }

    @Override
    public double getRating(int index) {
        return this.getReviewDoc(index).getOverallRating();
    }

    @Override
    public List<Double> getAspectRatings(int index) {
        return this.getReviewDoc(index).getAspectRatings();
    }

    @Override
    public int size() {
        return this.docs.size();
    }

    @Override
    public Document getDoc(int index) {
        return this.getReviewDoc(index).getDoc();
    }

    @Override
    public Document getDoc(String doc_id) {
        ReviewDoc rdoc = this.getReviewDoc(doc_id);
        if (rdoc != null) {
            return rdoc.getDoc();
        }
        return null;
    }

    @Override
    public int getDocIndex(String doc_id) {
        if (this.indexer.containsKey(doc_id)) {
            return this.indexer.get(doc_id);
        }
        return -1;
    }

    @Override
    public String getDocID(int index) {
        if (this.inverse_indexer.containsKey(index)) {
            return this.inverse_indexer.get(index);
        }
        return null;
    }

    @Override
    public void addDoc(Document doc) {
        try {
            throw new OperationNotSupportedException("This method is not implemented yet.");
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addDoc(String doc_id, Document doc) {
        try {
            throw new OperationNotSupportedException("This method is not implemented yet.");
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getUserSize() {
        return this.user_indexer.size();
    }

    @Override
    public int getPrdSize() {
        return this.prd_indexer.size();
    }

    @Override
    public int getSize() {
        return this.docs.size();
    }

    @Override
    public String summary() {
        StringBuffer buffer = new StringBuffer("The statistic summary of the corpus:\n");
        buffer.append("\n\tuser size:\t" + this.getUserSize());
        buffer.append("\n\tprd size:\t" + this.getPrdSize());
        buffer.append("\n\ttotal size:\t" + this.getSize());
        return buffer.toString();
    }
}
