package com.numb3r3.nlp.core.stemmer.local;

import com.numb3r3.nlp.core.Token;
import com.numb3r3.nlp.core.stemmer.Stemmer;
import com.numb3r3.nlp.core.tokenizer.Tokenizer;
import com.numb3r3.nlp.core.tokenizer.local.OpenNLPTokenizer;
import uk.ac.susx.informatics.Morpha;

import java.io.StringReader;
import java.util.Iterator;
import java.util.regex.Pattern;


public class MorphaStemmer implements Stemmer {

    private static final Pattern whitespace = Pattern.compile("\\s+");

    private static String cleanText(String text) {
        return text.replaceAll("_", "-");
    }

    /**
     * Run the morpha algorithm on the specified string.
     */
    public static String morpha(String text, boolean tags) {
        if (text.isEmpty()) {
            return "";
        }

        String[] textParts = whitespace.split(text);

        StringBuilder result = new StringBuilder();
        try {
            for (int i = 0; i < textParts.length; i++) {
                Morpha morpha = new Morpha(new StringReader(textParts[i]), tags);

                if (result.length() != 0) {
                    result.append(" ");
                }

                result.append(morpha.next());
            }
        }
        // yes, Morpha is cool enough to throw Errors
        // usually when the text contains underscores
        catch (Error e) {
            return text;
        } catch (java.io.IOException e) {
            return text;
        }

        String string = result.toString();
        if (string.equals("null")) {
            return "";
        } else {
            return string;
        }
    }

    @Override
    public void init(String modelName) {
        // TODO Auto-generated method stub

    }

    @Override
    public String stem(String word) {
        return morpha(cleanText(word), false);
    }

    @Override
    public String stemToken(Token token) {

        // if (whitespace.matcher(token).find()) {
        // throw new IllegalArgumentException(
        // "Token may not contain a space: " + token);
        // }
        //return morpha(cleanText(token), false);
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
        Stemmer stemmer2 = new MorphaStemmer();
        for (Iterator it = tokenizer.tokens(test); it.hasNext(); ) {
            Token token = (Token) it.next();
            System.out.print(stemmer2.stem(token.term()) + " ");
        }
        System.out.println();
    }
}
