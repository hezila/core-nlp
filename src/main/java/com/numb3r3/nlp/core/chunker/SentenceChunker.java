package com.numb3r3.nlp.core.chunker;


import com.numb3r3.nlp.core.ChunkedSentence;

public interface SentenceChunker {

    public ChunkedSentence chunkSentence(String sent) throws ChunkerException;
}
