package com.numb3r3.nlp.corpus;

import com.numb3r3.nlp.doc.Document;


public interface DocCorpus {
    public int size();

    public Document getDoc(int index);

    public Document getDoc(String doc_id);

    public int getDocIndex(String doc_id);

    public String getDocID(int index);

    public void addDoc(Document doc);

    public void addDoc(String doc_id, Document doc);

}
