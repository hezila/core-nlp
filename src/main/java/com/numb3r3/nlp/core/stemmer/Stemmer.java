package com.numb3r3.nlp.core.stemmer;

import com.numb3r3.nlp.core.Token;

public interface Stemmer {

    public void init(String modelName);

    /**
     * Apply the stemming algorithm.
     *
     * @param word
     * @return
     */
    public String stem(final String word);

    /**
     * Stem a token with a postag.
     *
     * @param token
     * @return
     */
    public String stemToken(final Token token);

    /**
     * Apply the normalizing algorithm and then the stemming algorithm.
     *
     * @param word
     * @return
     */
    public String lemmatize(final String word);

    /**
     * Lemmatize a token with a postag.
     *
     * @param token
     * @return
     */
    public String lemmatizeToken(final Token token);
}
