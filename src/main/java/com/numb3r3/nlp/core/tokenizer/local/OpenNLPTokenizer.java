package com.numb3r3.nlp.core.tokenizer.local;

import com.numb3r3.nlp.core.Token;
import com.numb3r3.nlp.core.tokenizer.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class OpenNLPTokenizer implements Tokenizer {

    public static TokenizerME detector_instance = null;

    private String DEFAULT_MODEL = "en-token.bin";

    @Override
    public void init(String modelName) {
        if (detector_instance == null) {
            if (modelName == null) {
                modelName = DEFAULT_MODEL;
            }
            try {
                TokenizerModel model = new TokenizerModel(this.getClass()
                        .getClassLoader().getResourceAsStream(modelName));
                detector_instance = new TokenizerME(model);
            } catch (InvalidFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public Iterator<Token> tokens(String sentence) {
        if (detector_instance == null) {
            init(null);
        }
        List<Token> tokens = new ArrayList<Token>();
        int index = 0;
        for (Span span : detector_instance.tokenizePos(sentence)) {
            String term = sentence.substring(span.getStart(), span.getEnd());

            tokens.add(new Token(term, index));
            index++;
        }
        return tokens.iterator();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Tokenizer tokenizer = new OpenNLPTokenizer();
        String sentence = "This is a good computer.";
        for (Iterator it = tokenizer.tokens(sentence); it.hasNext(); ) {
            System.out.println(it.next());
        }

    }

}
