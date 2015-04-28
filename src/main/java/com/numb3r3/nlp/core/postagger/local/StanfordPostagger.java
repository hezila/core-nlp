package com.numb3r3.nlp.core.postagger.local;

import com.google.common.base.Splitter;
import com.numb3r3.nlp.core.PostaggedToken;
import com.numb3r3.nlp.core.Token;
import com.numb3r3.nlp.core.postagger.Postagger;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StanfordPostagger implements Postagger {

    public static MaxentTagger tagger = null;

    public String DEFAULT_MODEL = "english-left3words-distsim.tagger";

    /**
     * @param args
     */
    public static void main(String[] args) {
        String sent = "This is a great movie_music.";
        Postagger tagger = new StanfordPostagger();
        for (Iterator it = tagger.postagTokens(sent); it.hasNext(); ) {
            System.out.println((PostaggedToken) it.next());
        }
    }

    @Override
    public void init(String modelName) {
        if (tagger == null) {
            if (modelName == null) {
                modelName = DEFAULT_MODEL;
            }
            try {

                tagger = new MaxentTagger(this.getClass().getClassLoader()
                        .getResource(modelName).getPath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    @Override
    public Iterator<PostaggedToken> postagTokens(String sentence) {
        if (tagger == null) {
            init(null);
        }
        String taggedsent = tagger.tagString(sentence);

        int index = 0;

        List<PostaggedToken> postaggedTokens = new ArrayList<PostaggedToken>();

        for (String token : Splitter.on(' ').trimResults().omitEmptyStrings()
                .split(taggedsent)) {
            int begin = token.lastIndexOf('_');
            String word = token.substring(0, begin);
            String tag = token.substring(begin + 1);
            postaggedTokens.add(new PostaggedToken(word, index, tag, null));
            index++;
        }


        return postaggedTokens.iterator();
    }

    public Iterator<PostaggedToken> postagTokens(List<Token> tokens) {
        if (tagger == null) {
            this.init(null);
        }
        List<CoreLabel> labels = new ArrayList<CoreLabel>();
        for (Token token : tokens) {
            CoreLabel label = new CoreLabel();
            label.setWord(token.term());
            labels.add(label);
        }
        List<PostaggedToken> taggedTokens = new ArrayList<PostaggedToken>();
        tagger.tagCoreLabels(labels);
        for (CoreLabel label : labels) {
            PostaggedToken postoken = new PostaggedToken(label.word(),
                    label.index(), label.tag(), label.lemma());
            taggedTokens.add(postoken);
        }

        return taggedTokens.iterator();

    }

}
