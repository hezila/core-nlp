package com.numb3r3.nlp.core.parser;

import com.numb3r3.nlp.core.Sentence;

import java.util.List;


public interface Parser {

    public void parse(Sentence sentence);

    public List<Sentence> parse(String text);

    public Sentence sent_parse(String sent_str);

}
