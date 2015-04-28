package com.numb3r3.nlp.core.chunker.opennlp;

import com.numb3r3.common.Range;
import com.numb3r3.nlp.core.ChunkedSentence;
import com.numb3r3.nlp.core.chunker.ChunkerException;
import com.numb3r3.nlp.core.chunker.SentenceChunker;
import com.numb3r3.nlp.util.OpenNlpUtils;
import opennlp.tools.chunker.Chunker;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * A class that combines OpenNLP tokenizer, POS tagger, and chunker objects into
 * a single object that converts String sentences to {@link ChunkedSentence}
 * objects. By default, uses the models from
 *
 * @author afader
 */
public class OpenNlpSentenceChunker implements SentenceChunker {

    public static final String chunkerModelFile = "en-chunker.bin";
    public static final String tokenizerModelFile = "en-token.bin";
    public static final String taggerModelFile = "en-pos-maxent.bin";

    Pattern convertToSpace = Pattern.compile("\\xa0");

    private Chunker chunker;
    private POSTagger posTagger;
    private Tokenizer tokenizer;


    private boolean attachOfs = true;
    private boolean attachPossessives = true;

    /**
     * Constructs a new object using the default models from
     *
     * @throws java.io.IOException if unable to load the models.
     */
    public OpenNlpSentenceChunker() throws IOException {
        this.tokenizer = new TokenizerME(new TokenizerModel(
                this.getClass().getResourceAsStream(tokenizerModelFile)));
        this.posTagger = new POSTaggerME(new POSModel(
                this.getClass().getResourceAsStream(taggerModelFile)));
        this.chunker = new ChunkerME(new ChunkerModel(
                this.getClass().getClassLoader().getResourceAsStream(chunkerModelFile)));

    }

    /**
     * Constructs a new {@link OpenNlpSentenceChunker} object using the provided
     * OpenNLP objects.
     *
     * @param tokenizer
     * @param posTagger
     * @param chunker
     */
    public OpenNlpSentenceChunker(Tokenizer tokenizer, POSTagger posTagger,
                                  Chunker chunker) {
        this.tokenizer = tokenizer;
        this.posTagger = posTagger;
        this.chunker = chunker;
    }

    /**
     * @return true if this object will attach NPs beginning with "of" with the
     * previous NP.
     */
    public boolean attachOfs() {
        return attachOfs;
    }

    /**
     * @return true if this object will attach NPs beginning with the tag POS
     * with the previous NP.
     */
    public boolean attachPossessives() {
        return attachPossessives;
    }

    /**
     * @param attachOfs
     */
    public void attachOfs(boolean attachOfs) {
        this.attachOfs = attachOfs;
    }

    /**
     * @param attachPossessives
     */
    public void attachPossessives(boolean attachPossessives) {
        this.attachPossessives = attachPossessives;
    }

    @Override
    /**
     * Chunks the given sentence and returns it as an {@link ChunkedSentence}
     * object.
     */
    public ChunkedSentence chunkSentence(String sent) throws ChunkerException {

        // OpenNLP cannot handle non-breaking whitespace
        sent = convertToSpace.matcher(sent).replaceAll(" ");

        ArrayList<Range> ranges;
        String[] tokens, posTags, npChunkTags;

        // OpenNLP can throw a NullPointerException. Catch it, and raise it
        // as a checked exception.
        // TODO: try to figure out what caused the NPE and actually fix the problem
        try {
            Span[] offsets = tokenizer.tokenizePos(sent);
            ranges = new ArrayList<Range>(offsets.length);
            ArrayList<String> tokenList = new ArrayList<String>(offsets.length);
            for (Span span : offsets) {
                ranges.add(Range.fromInterval(span.getStart(), span.getEnd()));
                tokenList.add(sent.substring(span.getStart(), span.getEnd()));
            }

            tokens = tokenList.toArray(new String[]{});
            posTags = posTagger.tag(tokens);
            npChunkTags = chunker.chunk(tokens, posTags);
        } catch (NullPointerException e) {
            throw new ChunkerException("OpenNLP threw NPE on '" + sent + "'", e);
        }

        if (attachOfs)
            OpenNlpUtils.attachOfs(tokens, npChunkTags);
        if (attachPossessives)
            OpenNlpUtils.attachPossessives(posTags, npChunkTags);

        ChunkedSentence result = new ChunkedSentence(
                ranges.toArray(new Range[]{}), tokens, posTags, npChunkTags);
        return result;
    }
}
