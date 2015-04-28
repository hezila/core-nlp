package com.numb3r3.nlp.doc;

import com.google.common.collect.Lists;
import com.numb3r3.nlp.doc.local.InMemoryDoc;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;


public class JSONDoc {
    public static JSONObject json_parse(Document doc) {
        JSONObject json_obj = new JSONObject();
        String doc_id = doc.getDocID();
        json_obj.put("doc_id", doc_id);

        JSONArray word_array = new JSONArray();
        for (int word : doc.getWords()) {
            word_array.add(word);
        }
        json_obj.put("words", word_array);

        JSONArray bd_array = new JSONArray();
        for (int bd : doc.getBreaks()) {
            bd_array.add(bd);
        }
        json_obj.put("breaks", bd_array);
        return json_obj;
    }

    public static String dumps(Document doc) {
        return json_parse(doc).toString();
    }

    public static Document load_json(JSONObject json_doc) {
        String doc_id = json_doc.get("doc_id").toString();

        List<Integer> words = Lists.newArrayList();
        List<Integer> breaks = Lists.newArrayList();

        JSONArray words_array = (JSONArray) json_doc.get("words");
        for (int i = 0; i < words_array.size(); i++) {
            words.add(Integer.parseInt(words_array.get(i).toString()));
        }

        JSONArray break_array = (JSONArray) json_doc.get("breaks");
        for (int i = 0; i < break_array.size(); i++) {
            breaks.add(Integer.parseInt(break_array.get(i).toString()));
        }
        return new InMemoryDoc(doc_id, words, breaks);
    }
}
