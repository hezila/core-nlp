package com.numb3r3.nlp.doc.local;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.numb3r3.common.util.Counter;
import com.numb3r3.nlp.doc.Document;

import java.util.List;
import java.util.Map;


public class InMemoryDoc implements Document {
    private String doc_id = null;

    private List<Integer> words;

    private List<Integer> breaks;

    private Map<Integer, Counter> word_counts = Maps.newHashMap();

    public InMemoryDoc() {
        this.doc_id = null;
        words = Lists.newArrayList();
        breaks = Lists.newArrayList();
    }


    public InMemoryDoc(String doc_id) {
        this.doc_id = doc_id;
        words = Lists.newArrayList();
        breaks = Lists.newArrayList();

    }

    public InMemoryDoc(String doc_id, List<Integer> words, List<Integer> breaks) {
        this.doc_id = doc_id;
        this.words = words;
        for (int w : words) {
            if (word_counts.containsKey(w)) {
                word_counts.get(w).increment();
            } else {
                word_counts.put(w, new Counter(1));
            }
        }
        this.breaks = breaks;
    }

    @Override
    public String getDocID() {
        return this.doc_id;
    }


    @Override
    public int getDocLength() {
        return this.words.size();
    }

    @Override
    public int getWord(int index) {
        assert index < this.getDocLength();
        return this.words.get(index);
    }

    @Override
    public List<Integer> getWords() {
        return this.words;
    }

    @Override
    public int getWordCount(int w) {
        if (this.word_counts.containsKey(w)) {
            return this.word_counts.get(w).value();
        }
        return 0;
    }

    @Override
    public List<Integer> getSentence(int sent_offset) {
        assert this.breaks != null;

        int begin = 0;
        if (sent_offset > 0) begin = this.breaks.get(sent_offset - 1);
        int end = this.getDocLength();
        if (sent_offset < this.breaks.size())
            end = this.breaks.get(sent_offset);

        List<Integer> words = Lists.newArrayList();
        for (int i = begin; i < end; i++) {
            words.add(this.getWord(i));
        }
        return words;
    }

    @Override
    public List<List<Integer>> getSentences() {
        assert this.breaks != null;
        List<List<Integer>> sents = Lists.newArrayList();
        for (int sent_index = 0; sent_index < this.getSentNum(); sent_index++) {
            sents.add(this.getSentence(sent_index));
        }
        return sents;
    }

    @Override
    public List<Integer> getBreaks() {
        return this.breaks;
    }


    @Override
    public int getSentNum() {
        return this.breaks.size() + 1;
    }

    @Override
    public void addSent(List<Integer> words) {
        this.breaks.add(this.getDocLength());
        this.words.addAll(words);

    }

    public static void main(String[] args) {
        Document doc = new InMemoryDoc();
        List<Integer> words = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            words.add(i);
        }
        doc.addSent(words);
        words.add(8);
        doc.addSent(words);
        System.out.println(doc.getBreaks());
    }


}
