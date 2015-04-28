package com.numb3r3.nlp.corpus.local;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.numb3r3.nlp.corpus.DocCorpus;
import com.numb3r3.nlp.doc.Document;

import java.util.List;

public class InMemoryDocCorpus implements DocCorpus {

    private List<Document> docs = Lists.newArrayList();

    BiMap<String, Integer> indexer = HashBiMap.create();
    BiMap<Integer, String> inverse_indexer = indexer.inverse();

    public InMemoryDocCorpus() {
    }

    @Override
    public int size() {
        return this.docs.size();
    }

    @Override
    public Document getDoc(int index) {
        return this.docs.get(index);
    }

    @Override
    public Document getDoc(String doc_id) {
        if (this.indexer.containsKey(doc_id)) {
            int doc_index = this.indexer.get(doc_id);
            return this.getDoc(doc_index);
        } else {
            System.err.println("The doc id does not exists in the corpus");
            return null;
        }
    }

    @Override
    public int getDocIndex(String doc_id) {
        if (this.indexer.containsKey(doc_id))
            return this.indexer.get(doc_id);
        else System.err.println("The doc id does not exists in the corpus");
        return -1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDocID(int index) {
        if (this.inverse_indexer.containsKey(index)) {
            return this.inverse_indexer.get(index);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addDoc(Document doc) {
        int doc_index = this.size();
        String docID = doc.getDocID();
        if (docID != null)
            this.indexer.put(docID, doc_index);
        this.docs.add(doc);

    }

    @Override
    public void addDoc(String doc_id, Document doc) {
        int doc_index = this.size();
        this.indexer.put(doc_id, doc_index);
        this.docs.add(doc);
    }


}
