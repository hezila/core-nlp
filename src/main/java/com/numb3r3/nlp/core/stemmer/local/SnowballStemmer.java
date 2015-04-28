package com.numb3r3.nlp.core.stemmer.local;

import com.numb3r3.nlp.core.Token;
import com.numb3r3.nlp.core.stemmer.Stemmer;
import com.numb3r3.nlp.core.tokenizer.Tokenizer;
import com.numb3r3.nlp.core.tokenizer.local.OpenNLPTokenizer;
import org.tartarus.snowball.SnowballProgram;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Wrapper for any Snowball stemmer.
 * TODO Fix the problem, this stemmer doest not work well.
 *
 * @author numb3r3
 */
public class SnowballStemmer implements Stemmer {

    public static SnowballProgram decoratee = null;

    public String DEFAULT_MODEL = "PorterStemmer";

    private final Set<String> stopWords = new HashSet<String>(
            Arrays.asList(new String[]{"i", "me", "my", "myself", "we",
                    "our", "ours", "ourselves", "you", "your", "yours",
                    "yourself", "yourselves", "he", "him", "his", "himself",
                    "she", "her", "hers", "herself", "it", "its", "itself",
                    "they", "them", "their", "theirs", "themselves", "what",
                    "which", "who", "whom", "this", "that", "these", "those",
                    "am", "is", "are", "was", "were", "be", "been", "being",
                    "have", "has", "had", "having", "do", "does", "did",
                    "doing", "would", "should", "could", "ought", "i'm",
                    "you're", "he's", "she's", "it's", "we're", "they're",
                    "i've", "you've", "we've", "they've", "i'd", "you'd",
                    "he'd", "she'd", "we'd", "they'd", "i'll", "you'll",
                    "he'll", "she'll", "we'll", "they'll", "isn't", "aren't",
                    "wasn't", "weren't", "hasn't", "haven't", "hadn't",
                    "doesn't", "don't", "didn't", "won't", "wouldn't",
                    "shan't", "shouldn't", "can't", "cannot", "couldn't",
                    "mustn't", "let's", "that's", "who's", "what's", "here's",
                    "there's", "when's", "where's", "why's", "how's", "a",
                    "an", "the", "and", "but", "if", "or", "because", "as",
                    "until", "while", "of", "at", "by", "for", "with", "about",
                    "against", "between", "into", "through", "during",
                    "before", "after", "above", "below", "to", "from", "up",
                    "down", "in", "out", "on", "off", "over", "under", "again",
                    "further", "then", "once", "here", "there", "when",
                    "where", "why", "how", "all", "any", "both", "each", "few",
                    "more", "most", "other", "some", "such", "no", "nor",
                    "not", "only", "own", "same", "so", "than", "too", "very"}));

    @Override
    public String stem(String word) {
        decoratee.setCurrent(word);
        decoratee.stem();
        return decoratee.getCurrent();
    }

    @Override
    public String stemToken(Token token) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String lemmatize(String word) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String lemmatizeToken(Token token) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String test = "I really love this place and don't know why it has an average review of anything less than 4 stars. I usually never repeat restaurants and have been here three times already. It's the only halal burger place that I know of in Manhattan, but I also love how they use fresh organic ingredients. They also offer a large variety of toppings, the citrus aioli is amazing. The burgers are huge so come with an appetite.\\nSide note- If you only eat halal, the elk and bison burgers are not halal and the onion rings are beer battered.";
        System.out.println(test);
        Tokenizer tokenizer = new OpenNLPTokenizer();
        Stemmer stemmer2 = new SnowballStemmer();
        stemmer2.init(null);
        for (Iterator it = tokenizer.tokens(test); it.hasNext(); ) {
            Token token = (Token) it.next();
            System.out.print(stemmer2.stem(token.term()) + " ");
        }
        System.out.println();

    }

    @Override
    public void init(String modelName) {
        if (decoratee == null) {
            try {
                if (modelName == null) {
                    modelName = DEFAULT_MODEL;
                }
                decoratee = (SnowballProgram) Class.forName(
                        "org.tartarus.snowball.ext." + modelName).newInstance();

            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
