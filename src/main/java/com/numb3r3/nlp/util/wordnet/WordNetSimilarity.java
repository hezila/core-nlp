package com.numb3r3.nlp.util.wordnet;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.*;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

public class WordNetSimilarity {

    private static ILexicalDatabase db = new NictWordNet();
    private static RelatednessCalculator[] rcs = {new HirstStOnge(db),
            new LeacockChodorow(db), new Lesk(db), new WuPalmer(db),
            new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db)};

    private static void run(String word1, String word2) {
        WS4JConfiguration.getInstance().setMFS(true);
        for (RelatednessCalculator rc : rcs) {
            double s = rc.calcRelatednessOfWords(word1, word2);
            System.out.println(rc.getClass().getName() + "\t" + s);
        }
    }

    public static double HirstStOnege(String w1, String w2) {
        return rcs[0].calcRelatednessOfWords(w1, w2);
    }

    public static double LeacockChodorow(String w1, String w2) {
        return rcs[1].calcRelatednessOfWords(w1, w2);
    }

    public static double Lesk(String w1, String w2) {
        return rcs[2].calcRelatednessOfWords(w1, w2);
    }

    public static double WUPalmer(String w1, String w2) {
        return rcs[3].calcRelatednessOfWords(w1, w2);
    }

    public static double Resnik(String w1, String w2) {
        return rcs[4].calcRelatednessOfWords(w1, w2);
    }

    public static double JiangConrath(String w1, String w2) {
        return rcs[5].calcRelatednessOfWords(w1, w2);
    }

    public static double Lin(String w1, String w2) {
        return rcs[6].calcRelatednessOfWords(w1, w2);
    }

    public static double Path(String w1, String w2) {
        return rcs[7].calcRelatednessOfWords(w1, w2);
    }

    public static void main(String[] args) {
        long t0 = System.currentTimeMillis();
        run("breakfast", "service");
        long t1 = System.currentTimeMillis();
        System.out.println("Done in " + (t1 - t0) + " msec.");
    }

}
