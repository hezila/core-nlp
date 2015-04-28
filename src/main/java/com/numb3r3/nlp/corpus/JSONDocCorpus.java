package com.numb3r3.nlp.corpus;

import com.numb3r3.nlp.corpus.local.InMemoryDocCorpus;
import com.numb3r3.nlp.doc.JSONDoc;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class JSONDocCorpus {

    public static JSONObject json_parse(DocCorpus doc_corpus) {
        JSONObject json_doc = new JSONObject();

        JSONArray docs_json = new JSONArray();
        int doc_size = doc_corpus.size();
        for (int i = 0; i < doc_size; i++) {
            docs_json.add(JSONDoc.json_parse(doc_corpus.getDoc(i)));
        }
        json_doc.put("docs", docs_json);
        return json_doc;
    }

    public static String dumps(DocCorpus doc_corpus) {
        return json_parse(doc_corpus).toString();
    }

    public static DocCorpus load_json(JSONObject json_doc) {
        DocCorpus corpus = new InMemoryDocCorpus();
        JSONArray doc_array = (JSONArray) json_doc.get("docs");
        int size = doc_array.size();
        for (int i = 0; i < size; i++) {
            corpus.addDoc(JSONDoc.load_json((JSONObject) doc_array.get(i)));
        }
        return corpus;
    }
}
