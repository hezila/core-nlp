package com.numb3r3.nlp.core.postagger;

import com.numb3r3.nlp.core.PostaggedToken;

import java.util.Iterator;

public interface Postagger {

    public void init(final String modelName);

    public Iterator<PostaggedToken> postagTokens(String sentence);

}
