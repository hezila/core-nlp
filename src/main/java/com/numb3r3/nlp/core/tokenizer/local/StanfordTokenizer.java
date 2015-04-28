package com.numb3r3.nlp.core.tokenizer.local;

import com.numb3r3.nlp.core.Token;
import com.numb3r3.nlp.core.tokenizer.Tokenizer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class StanfordTokenizer implements Tokenizer {

    @Override
    public void init(String modelName) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<Token> tokens(String sentence) {
        List<Token> tokens = new ArrayList<Token>();
        int index = 0;
        for (Iterator it = new PTBTokenizer(new java.io.StringReader(sentence),
                new CoreLabelTokenFactory(), ""); it.hasNext(); ) {
            CoreLabel label = (CoreLabel) it.next();

            tokens.add(new Token(label.word(), index));
            index++;
        }
        return tokens.iterator();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Tokenizer tokenizer = new StanfordTokenizer();
        String sentence = "This is a good computer.";
        for (Iterator it = tokenizer.tokens(sentence); it.hasNext(); ) {
            System.out.println(it.next());
        }

    }

}
