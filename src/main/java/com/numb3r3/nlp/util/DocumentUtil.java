package com.numb3r3.nlp.util;

import com.google.common.collect.Lists;
import com.numb3r3.nlp.doc.Document;
import com.numb3r3.nlp.doc.local.InMemoryDoc;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fwang
 * Date: 10/31/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentUtil {
    public static JSONObject json_parse(Document doc) {
        JSONObject json_doc = new JSONObject();
        String doc_id = doc.getDocID();
        if (doc_id != null)
            json_doc.put("doc_id", doc_id);
        JSONArray words_array = new JSONArray();
        for (int word : doc.getWords()) {
            words_array.add(String.valueOf(word));
        }
        json_doc.put("words", words_array);

        if (doc.getBreaks() != null) {
            JSONArray breakers_array = new JSONArray();
            for (int loc : doc.getBreaks()) {
                breakers_array.add(String.valueOf(loc));
            }
            json_doc.put("breaks", breakers_array);
        }

        return json_doc;

    }

    public static String json_dumps(Document doc) {

        return DocumentUtil.json_parse(doc).toJSONString();
    }

    public static Document load_json(JSONObject json_doc) {
        String doc_id = null;
        if (json_doc.containsKey("doc_id")) {
            doc_id = json_doc.get("doc_id").toString();
        }

        List<Integer> words = Lists.newArrayList();
        if (json_doc.containsKey("words")) {
            JSONArray words_array = (JSONArray) json_doc.get("words");

            for (int i = 0; i < words_array.size(); i++) {
                words.add(Integer.parseInt(words_array.get(i).toString()));
            }

        }

        List<Integer> breaks = null;
        if (json_doc.containsKey("breaks")) {
            JSONArray breaks_array = (JSONArray) json_doc.get("breaks");
            breaks = Lists.newArrayList();
            for (int i = 0; i < breaks_array.size(); i++) {
                breaks.add(Integer.parseInt(breaks_array.get(i).toString()));
            }
        }
        return new InMemoryDoc(doc_id, words, breaks);

    }

    public static Document json_loads(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json_doc = (JSONObject) parser.parse(jsonString);
            return DocumentUtil.load_json(json_doc);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
