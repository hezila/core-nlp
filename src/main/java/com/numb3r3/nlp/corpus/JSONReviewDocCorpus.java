package com.numb3r3.nlp.corpus;

import com.numb3r3.nlp.corpus.local.InMemoryReviewDocCorpus;
import com.numb3r3.nlp.doc.JSONReviewDoc;
import com.numb3r3.nlp.doc.ReviewDoc;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONReviewDocCorpus {
    public static JSONObject json_parse(ReviewDocCorpus corpus) {
        JSONObject json_doc = new JSONObject();

        JSONArray reviews = new JSONArray();
        for (int i = 0; i < corpus.size(); i++) {
            ReviewDoc review = corpus.getReviewDoc(i);
            reviews.add(JSONReviewDoc.json_parse(review));
        }
        json_doc.put("reviews", reviews);
        return json_doc;
    }

    public static String dumps(ReviewDocCorpus corpus) {
        return json_parse(corpus).toString();
    }

    public static ReviewDocCorpus json_load(JSONObject json_doc) {
        ReviewDocCorpus corpus = new InMemoryReviewDocCorpus();
        JSONArray review_array = (JSONArray) json_doc.get("reviews");
        for (int i = 0; i < review_array.size(); i++) {
            ReviewDoc review = JSONReviewDoc.json_load((JSONObject) review_array.get(i));
            corpus.addReviewDoc(review);
        }
        return corpus;
    }

    public static ReviewDocCorpus load(String filepath) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json_doc = (JSONObject) parser.parse(new FileReader(new File(filepath)));
            return json_load(json_doc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
