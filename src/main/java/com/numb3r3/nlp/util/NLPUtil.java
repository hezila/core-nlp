package com.numb3r3.nlp.util;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


public class NLPUtil {

    private static final Pattern nonASCII = Pattern.compile("[^\\x00-\\x7f]");
    /*
     * from https://github.com/larsmans/lucene-stanford-lemmatizer
     */
    private static final Pattern unwantedPosRE = Pattern
            .compile("^(CC|DT|[LR]RB|MD|POS|PRP|UH|WDT|WP|WP\\$|WRB|\\$|\\#|\\.|\\,|:)$");
    private static final Pattern NounPosRE = Pattern.compile("^(NN|NNP|NNPS|NNS)$");
    static Map<String, String> swap_word = new HashMap<String, String>();
    static Map<String, String> swap_char = new HashMap<String, String>();
    static Set<String> after_word_stuff = new HashSet<String>();
    static Set<String> stopwords = Sets.newHashSet();

    public static Set<String> NegWords = Sets.newHashSet();

    private static void init() {
        if (swap_word.size() == 0) {
            swap_word.put("arent't", "are not");
            swap_word.put("can't", "can not");
            swap_word.put("couldn't", "could not");
            swap_word.put("didn't", "did not");
            swap_word.put("doesn't", "does not");
            swap_word.put("don't", "do not");
            swap_word.put("hadn't", "had not");
            swap_word.put("hasn't", "has not");
            swap_word.put("haven't", "have not");
            swap_word.put("isn't", "is not");
            swap_word.put("mightn't", "might not");
            swap_word.put("mustn't", "must not");
            swap_word.put("oughtn't", "ought not");
            swap_word.put("shan't", "shall not");
            swap_word.put("shouldn't", "should not");
            swap_word.put("wasn't", "was not");
            swap_word.put("weren't", "were not");
            swap_word.put("won't", "will not");
            swap_word.put("wouldn't", "would not");
            swap_word.put("arent", "are not");
            swap_word.put("cant", "can not");
            swap_word.put("couldnt", "could not");
            swap_word.put("didnt", "did not");
            swap_word.put("doesnt", "does not");
            swap_word.put("dont", "do not");
            swap_word.put("hadnt", "had not");
            swap_word.put("hasnt", "has not");
            swap_word.put("havent", "have not");
            swap_word.put("isnt", "is not");
            swap_word.put("mightnt", "might not");
            swap_word.put("mustnt", "must not");
            swap_word.put("oughtnt", "ought not");
            swap_word.put("shant", "shall not");
            swap_word.put("shouldnt", "should not");
            swap_word.put("wasnt", "was not");
            swap_word.put("werent", "were not");
            swap_word.put("wont", "will not");
            swap_word.put("wouldnt", "would not");

            NegWords.addAll(swap_word.keySet());
            NegWords.add("not");
            NegWords.add("never");
            NegWords.add("n't");
//
//
//            swap_word.put("'cause", "because");
//            swap_word.put("'cuz", "because");
//            swap_word.put("kinda", "kind of");
//            swap_word.put("sorta", "sort of");
//            swap_word.put("gonna", "going to");
//            swap_word.put("wanna", "want to");
//            swap_word.put("vs.", "versus");
//            swap_word.put("adn", "any day now");
//            swap_word.put("afk", "away from keyboard");
//            swap_word.put("aight", "alright");
//            swap_word.put("b4", "before");
//            swap_word.put("b4n", "bye for now");
//            swap_word.put("bak", "back at the keyboard");
//            swap_word.put("bf", "boyfriend");
//            swap_word.put("bff", "best friends forever");
//            swap_word.put("bta", "but then again");
//            swap_word.put("ppl", "people");
//            swap_word.put("r", "are");
//            swap_word.put("ru", "are you");
//            swap_word.put("somy", "sick of me yet");
//            swap_word.put("thx", "thanks");
//            swap_word.put("ur", "you are");
//            swap_word.put("ym", "young man");
//
//
        }
//        if (swap_char.size() == 0) {
//            swap_char.put("--", " - ");
//            swap_char.put(Character.toString((char) 147), "\"");
//            swap_char.put(Character.toString((char) 148), "\"");
//            swap_char.put(Character.toString((char) 145), "\"");
//            swap_char.put(Character.toString((char) 146), "'");
//            swap_char.put(Character.toString((char) 151), " - ");
//            swap_char.put(Character.toString((char) 133), "...");
//            swap_char.put(Character.toString((char) 150), " - ");
//
//
//        }

        if (after_word_stuff.size() == 0) {
            String[] temp = {" ", ".", ",", ":", ";", "\"", "\\)", "[?]", "!"};
            after_word_stuff.addAll(Arrays.asList(temp));
        }

    }

    private static boolean isnotnegone(final int i) {
        return i != -1;
    }

    public static String UppercaseFirstLetters(String str) {
        char[] stringArray = str.toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        return new String(stringArray);
    }

    public static String swap(String sentence) {
        // #for char in swap_char.keys():
        // # text = text.replace(char, swap_char[char])
        if (swap_word.size() == 0) {
            NLPUtil.init();
        }

        for (String c : swap_char.keySet()) {
            String replated = swap_char.get(c);
            sentence = sentence.replaceAll(c, replated);
        }


        for (String word : swap_word.keySet()) {
            for (Iterator<String> it = after_word_stuff.iterator(); it
                    .hasNext(); ) {
                String stuff = it.next();

                sentence = sentence.replaceAll(" " + word + stuff, " "
                        + swap_word.get(word) + stuff);


                sentence = sentence.replaceAll(UppercaseFirstLetters(word)
                        + stuff, UppercaseFirstLetters(swap_word.get(word))
                        + stuff);

            }
        }


        return sentence;
    }

    public static String clean(String text) {
        init();
        // input.replaceAll([^\\p{ASCII}], "");

        /* string.replace() is straitforward string replace function */
        text = text.replace("showreview", " ");
        text = text.replaceAll("-lrb-", "(");
        text = text.replaceAll("-rrb-", ")");
        text = text.replaceAll("-LRB-", "(");
        text = text.replaceAll("-RRB-", ")");

        text = text.replaceAll("<br .*?/>", "\n");
        //text = text.replaceAll("<br "+(char)92+"/><br "+(char)92+"/>", "\n");
        text = text.replaceAll("<br />", "\n");

        text = text.replace("" + (char) 92 + (char) 47, "/");

        //System.out.println(text);

        text = swap(text);
        text = nonASCII.matcher(text).replaceAll(" ");

        /* remove duplicate punctuations */
        text = text.replaceAll("([^\\w^\\d^\\s]{1})[^\\w^\\d^\\s]+", "$1 ");


//        text = text.replace("xx", "xxx");
//        text = text.replace("xxxx", "xxx");
//        text = text.replace("xxxxx", "xxx");
//        text = text.replace("xxxxxx", "xxx");

        /* string.replaceAll() is regex supporting function */
        // text = text.replaceAll("[\\.;]+", ".\n");
        text = text.replaceAll("(\\s)\\1", "$1"); // replace duplicate white spaces


//
//
//        text = text.replaceAll("\\s*\\(\\s*([\\d]+)\\s*[\\)]\\s+", " $1) ");
//
//
//        text = text.replaceAll("([\\w]+)([\\s,]*)([\\d]+)[\\s]*[\\)\\\\.]",
//                "$1. $3\\)");
//        text = text.replaceAll("\\)\\s*", ") ");
//
//        text = text.replaceAll("\\s+([a-zA-Z]+\\p{P})([A-Z][\\w]+)\\s+", " $1 $2 ");


        return text;
    }

    public static boolean unwantedPOS(String tag) {
        init();
        return unwantedPosRE.matcher(tag).matches();
    }

    public static boolean stopword(String word, String path) {
        init();
        if (stopwords.size() == 0) {

            //System.out.println();
            File file = new File(path);

            //File t = new File(NLPUtil.class.getClassLoader().getResource("stop_words.txt"));
            try {
                for (String line : Files.readLines(file, Charsets.UTF_8)) {
                    stopwords.add(CharMatcher.anyOf("\n\r ").removeFrom(line)
                            .toLowerCase());
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return stopwords.contains(word.toLowerCase());
    }

    public static Set<String> load_stopwords(String path) {
        File file = new File(path);
        Set<String> stop_set = Sets.newHashSet();
        try {
            for (String line : Files.readLines(file, Charsets.UTF_8)) {
                stop_set.add(CharMatcher.anyOf("\n\r ").removeFrom(line)
                        .toLowerCase());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stop_set;
    }

    public static boolean contain_digitals(String text) {
        if (text.matches(".*\\d.*")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNoun(String tag) {
        return NounPosRE.matcher(tag).matches();
    }

    /**
     * <br>
     * Copyright (c) 2011, Regents of the University of Colorado <br>
     * All rights reserved.
     *
     * @author Philip Ogren
     * <p/>
     * <p/>
     * Portions of this code were derived from pseudocode located at
     * http://en.wikipedia.org/wiki/Levenshtein_distance
     */

    public static float levenshteinSimilarity(String string1, String string2) {
        if (string1.length() == 0 && string2.length() == 0) {
            return 1.0f;
        }
        int editDistance = editDistance(string1, string2);

        float similarity = (float) editDistance / (string1.length() + string2.length());
        return 1 - Math.min(similarity, 1.0f);
    }

    public static int editDistance(String string1, String string2) {
        int rowCount = string1.length() + 1;
        int columnCount = string2.length() + 1;

        int[][] matrix = new int[rowCount][columnCount];

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            matrix[rowIndex][0] = rowIndex;
        }

        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            matrix[0][columnIndex] = columnIndex;
        }

        for (int rowIndex = 1; rowIndex < matrix.length; rowIndex++) {
            for (int columnIndex = 1; columnIndex < matrix[0].length; columnIndex++) {
                char char1 = string1.charAt(rowIndex - 1);
                char char2 = string2.charAt(columnIndex - 1);
                if (char1 == char2) {
                    matrix[rowIndex][columnIndex] = matrix[rowIndex - 1][columnIndex - 1];
                } else {
                    int left = matrix[rowIndex][columnIndex - 1];
                    int up = matrix[rowIndex - 1][columnIndex];
                    int leftUp = matrix[rowIndex - 1][columnIndex - 1];
                    int distance = Math.min(left, up);
                    distance = Math.min(distance, leftUp);
                    matrix[rowIndex][columnIndex] = distance + 1;
                }
            }
        }

        return matrix[rowCount - 1][columnCount - 1];
    }

    public static List<String> split_para(String content, String delimiter) {
        if (delimiter == null) delimiter = "\n";

        List<String> paras = Lists.newArrayList();

        for (String str_item : Splitter.on(delimiter).trimResults().omitEmptyStrings().split(content)) {
            paras.add(str_item);
        }
        return paras;
    }

    public static Set abbreviations = new HashSet(64);

    static {
        abbreviations.add("Adm.");
        abbreviations.add("Capt.");
        abbreviations.add("Cmdr.");
        abbreviations.add("Col.");
        abbreviations.add("Dr.");
        abbreviations.add("Gen.");
        abbreviations.add("Gov.");
        abbreviations.add("Lt.");
        abbreviations.add("Maj.");
        abbreviations.add("Messrs.");
        abbreviations.add("Mr.");
        abbreviations.add("Mrs.");
        abbreviations.add("Ms.");
        abbreviations.add("Prof.");
        abbreviations.add("Rep.");
        abbreviations.add("Reps.");
        abbreviations.add("Rev.");
        abbreviations.add("Sen.");
        abbreviations.add("Sens.");
        abbreviations.add("Sgt.");
        abbreviations.add("Sr.");
        abbreviations.add("St.");

        // abbreviated first names
        abbreviations.add("Benj.");
        abbreviations.add("Chas.");
        // abbreviations.add("Alex."); // dch

        // abbreviated months
        abbreviations.add("Jan.");
        abbreviations.add("Feb.");
        abbreviations.add("Mar.");
        abbreviations.add("Apr.");
        abbreviations.add("Mar.");
        abbreviations.add("Jun.");
        abbreviations.add("Jul.");
        abbreviations.add("Aug.");
        abbreviations.add("Sept.");
        abbreviations.add("Oct.");
        abbreviations.add("Nov.");
        abbreviations.add("Dec.");

        // other abbreviations
        abbreviations.add("a.k.a.");
        abbreviations.add("c.f.");
        abbreviations.add("i.e.");
        abbreviations.add("e.g.");
        abbreviations.add("vs.");
        abbreviations.add("v.");

        Set tmp = new HashSet(64);
        Iterator it = abbreviations.iterator();
        while (it.hasNext())
            tmp.add(((String) it.next()).toLowerCase());
        abbreviations.addAll(tmp);
    }

    public static boolean isConjunction(String term) {
        if (term.equalsIgnoreCase("and") || term.equalsIgnoreCase("or") || term.equalsIgnoreCase("&") || term.equalsIgnoreCase("with") || term.equalsIgnoreCase("of")) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if 'input' is an abbreviation
     */
    public static boolean isAbbreviation(String input) {
        return abbreviations.contains(input); // case??
    }

    public static boolean isNegation(String term) {
        init();
        return NegWords.contains(term.trim().toLowerCase());
    }

    public static boolean isOverlapWithin(String term1, String term2) {
        if (term1.length() >= term2.length() && term2.length() >= 4) {
            return term1.contains(term2);
        } else if (term1.length() < term2.length() && term1.length() >= 4) {
            return term2.contains(term1);
        }
        return false;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String text = "We got it for parents, $400 for a<br \\/> 15.6&#34; Dell, i3 third/> gen, 4 GB RAM, 500 GB HDD, Windows 8. Brushed metal finish.<br \\/><br \\/>Overall fit & finish is good and matched any high end laptop, but don't compare with mac books. Its more that what you should expect for the price, looks good, thinner and much lighter than laptops in below $500 range.<br \\/><br \\/>The 15.6&#34; screen is bright and clean, viewing angle looks ok, resolution is standard 1366x768.";
//        for (int i = 0; i < text.length(); i++) {
//            System.out.println(text.charAt(i) + "\t" + (int) text.charAt(i));
//        }
        System.out.println(text);
//        System.out.println(NLPUtil.swap(text));

        System.out.println(NLPUtil.clean(text));


    }

}
