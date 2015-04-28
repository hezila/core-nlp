package com.numb3r3.nlp.core.tokenizer;

import com.numb3r3.nlp.core.Token;

import java.util.Iterator;

public interface Tokenizer {

    public void init(final String modelName);

    public Iterator<Token> tokens(final String sentence);

}
