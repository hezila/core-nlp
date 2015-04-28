package com.numb3r3.nlp.core.parser.local;

import com.google.common.collect.Lists;
import com.numb3r3.nlp.core.PostaggedToken;
import com.numb3r3.nlp.core.Sentence;
import com.numb3r3.nlp.core.parser.Parser;
import com.numb3r3.nlp.core.sentencer.Sentencer;
import com.numb3r3.nlp.core.sentencer.local.OpenNLPSentencer;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SimpleStanfordParser implements Parser {

    private static StanfordCoreNLP pipeline = null;

    private Sentencer sentencer = new OpenNLPSentencer();

    private static int counter = 0;

    private static boolean initialized = init();

    public SimpleStanfordParser() {

    }


    public static boolean init() {
        StanfordCoreNLP.clearAnnotatorPool();
        // creates a StanfordCoreNLP object, with POS tagging,
        // lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        pipeline = new StanfordCoreNLP(props);

        counter = 0;
        return true;
    }


    @Override
    public void parse(Sentence sentence) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Sentence> parse(String text) {
        if (pipeline == null) {
            System.out.println("Initializing Stanford corenlp ...");
            init();
        }

        counter++;

        if (counter > 5000) init();

        List<Sentence> sents = new ArrayList<Sentence>();

        List<String> sents1 = sentencer.sentences(text);

        for (String t : sents1) {
            int sent_size = t.split(" ").length;
            if (sent_size > 150) {
                System.out.println(t);
                System.out.println(text);
            }

            // create an empty Annotation just with the given text
            Annotation document = new Annotation(t);

            // run all Annotators on this text
            pipeline.annotate(document);

            // these are all the sentences in this document
            // a CoreMap is essentially a Map that uses class objects as keys
            // and
            // has values with custom types
            List<CoreMap> sentences = document.get(SentencesAnnotation.class);

            int sent_index = 0;

            for (CoreMap sentence : sentences) {
                List<PostaggedToken> postags = new ArrayList<PostaggedToken>();
                // traversing the words in the current sentence
                // a CoreLabel is a CoreMap with additional token-specific
                // methods
                int index = 0;
                for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                    // this is the text of the token
                    String word = token.get(TextAnnotation.class);
                    // this is the POS tag of the token
                    String pos = token.get(PartOfSpeechAnnotation.class);
                    String lemma = token.get(LemmaAnnotation.class).toLowerCase();

                    postags.add(new PostaggedToken(word, index, pos, lemma));
                    index++;
                }


//                //this is the parse tree of the current sentence
//                Tree tree = sentence.get(TreeAnnotation.class);
//
//                //this is the Stanford dependency graph of the current sentence
//                SemanticGraph dependencies = sentence
//                		.get(CollapsedCCProcessedDependenciesAnnotation.class);
//                SemanticDeps sem_deps = new SemanticDeps();
//
//                for (SemanticGraphEdge edg : dependencies.edgeIterable()) {
//
//                    IndexedWord gov = edg.getGovernor();
//                    IndexedWord dep = edg.getDependent();
//                    String type = edg.getRelation().toString();
//                    sem_deps.add(new SemanticEdge(gov.index(), dep.index(), type));
//                }
                sents.add(new Sentence(postags, sent_index));
                sent_index++;
            }
        }
        return sents;
    }

    @Override
    public Sentence sent_parse(String sent_str) {
        List<Sentence> sents = this.parse(sent_str);
        if (sents.size() == 1) {
            return sents.get(0);
        } else {
            Sentence result = new Sentence();
            result.setTerms(sent_str);
            List<PostaggedToken> tokens = Lists.newArrayList();
            for (int i = 0; i < sents.size(); i++) {

                tokens.addAll(sents.get(i).posTokens());
            }
            result.setPosTokens(tokens);
            return result;
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String sentence = "This is a good toy.";
        Parser parser = new StanfordParser();

        for (Sentence sent : parser.parse(sentence)) {
            System.out.println(sent);
        }
    }

}
