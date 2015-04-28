package com.numb3r3.nlp.doc;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fwang
 * Date: 10/30/13
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Document {
    public String getDocID();

    public int getDocLength();

    public int getWord(int index);

    public List<Integer> getWords();

    public int getWordCount(int w);

    public List<Integer> getSentence(int sent_offset);

    public List<List<Integer>> getSentences();

    public List<Integer> getBreaks();

    public int getSentNum();

    public void addSent(List<Integer> words);
}
