package com.numb3r3.nlp.core.sentencer;

/**
 * Created with IntelliJ IDEA.
 * User: fwang
 * Date: 9/12/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

/*
 * TODO: http://www.monlp.com/2012/03/13/segmenting-words-and-sentences/
 */

public interface Sentencer {

    public List<String> sentences(final String text);

    public void init(String modelName);

}