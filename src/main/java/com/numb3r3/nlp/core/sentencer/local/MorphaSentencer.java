package com.numb3r3.nlp.core.sentencer.local;

import com.google.common.base.Joiner;
import com.numb3r3.nlp.core.sentencer.Sentencer;
import edu.northwestern.at.utils.corpuslinguistics.sentencesplitter.DefaultSentenceSplitter;
import edu.northwestern.at.utils.corpuslinguistics.sentencesplitter.SentenceSplitter;

import java.util.ArrayList;
import java.util.List;

public class MorphaSentencer implements Sentencer {

    SentenceSplitter spliter = new DefaultSentenceSplitter();

    @Override
    public List<String> sentences(String text) {
        List<String> sentences = new ArrayList<String>();

        for (List<String> list : spliter.extractSentences(text)) {
            sentences.add(Joiner.on(" ").skipNulls().join(list));
        }

        return sentences;

    }

    @Override
    public void init(String modelName) {
        // TODO Auto-generated method stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        //String tests = "sleep late and still find room under a straw hutbeautiful resort I found the Hotel inside and outside beautifulcheck in took 15 min theres no question that the workers work very hard and deserve tipsI strongly recomend tippingFrom bell boy to check in to maid and if not to drunk bartenderjust kidding I found I was put in bungalow 10which has to be prime locationRoom was beautiful the food was goodthere was never any crowds and even though you should book some of your dinner reservations in advance still had to book a couple and found I had to wait 20 min on a line some rest do not require reservationsthere is also room service and you could order any time thats a bit slow in the eveningsall in all I had a great timeI would recomend thisthe beach is so beautiful I find it better than the bahamas and beach in can not?cunyou will find no rocks or broken shells sand is so smooth on bavaro beachof course there are people taking advantage of all inclusive you do see people ordering room service with loads of bottles of beer or people checking out with 2 or even 3 bottles of beer in their hand one guy came up to the lobby bar and asked for a 6 packtheres nothing wrong with that I only saw one man at the pool get carried by the bartender and the wife following him up to their room that happens this is such a great family place to relax and rest no one bothers you they keep every thing clean and maintained";

        String tests = "This is a very good camrea. I like it.";


        Sentencer sentencer = new MorphaSentencer();
        for (String sent : sentencer.sentences(tests)) {
            System.out.println(sent);
        }

    }
}
