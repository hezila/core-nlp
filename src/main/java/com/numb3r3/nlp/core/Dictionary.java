package com.numb3r3.nlp.core;

import java.util.Set;

public interface Dictionary {

    public Set<String> terms();

    public int word(final String term);

    public String term(final int index);

    public boolean has(final String term);

    /* filtering words */
    public boolean has(final String term, double min);

    public int add(final String term);

    public void addAll(Iterable<String> collection);

    public int remove(final String term);

    public int remove(final int word);

    public int size();

    public void reset();

    public double freq(final int word);

    public int counts(final int word);

    public void dumps(String targetFile);

    public void loads(String sourceFile);

    public void profile();

    public Dictionary trim(double min);

    public void addterm(String term, double count);

}
