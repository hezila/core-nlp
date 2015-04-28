package com.numb3r3.nlp.core.sentencer.local;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.numb3r3.nlp.core.sentencer.Sentencer;
import spiaotools.SentParDetector;

import java.util.Iterator;
import java.util.List;


/**
 * A heuristic-based sentencer.
 */
public class PiaoSentencer implements Sentencer {

    public static SentParDetector detector_instance = null;

    @Override
    public void init(String modelName) {
        if (detector_instance == null)
            detector_instance = new SentParDetector();
    }

    @Override
    public List<String> sentences(final String text) {
        if (detector_instance == null)
            this.init(null);

        List<String> sents = Lists.newArrayList();
        Iterator itt = Splitter.on("\n").omitEmptyStrings().trimResults().split(detector_instance.markupRawText(2, text)).iterator();
        for (Iterator it = itt; it.hasNext(); ) {
            sents.add(it.next().toString());
        }
        return sents;

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Sentencer sentencer = new PiaoSentencer();
        sentencer.init(null);

        String tests = "sleep late and still find room under a straw hutbeautiful resort I found the Hotel inside and outside beautifulcheck in took 15 min theres no question that the workers work very hard and deserve tipsI strongly recomend tippingFrom bell boy to check in to maid and if not to drunk bartenderjust kidding I found I was put in bungalow 10which has to be prime locationRoom was beautiful the food was goodthere was never any crowds and even though you should book some of your dinner reservations in advance still had to book a couple and found I had to wait 20 min on a line some rest do not require reservationsthere is also room service and you could order any time thats a bit slow in the eveningsall in all I had a great timeI would recomend thisthe beach is so beautiful I find it better than the bahamas and beach in can not?cunyou will find no rocks or broken shells sand is so smooth on bavaro beachof course there are people taking advantage of all inclusive you do see people ordering room service with loads of bottles of beer or people checking out with 2 or even 3 bottles of beer in their hand one guy came up to the lobby bar and asked for a 6 packtheres nothing wrong with that I only saw one man at the pool get carried by the bartender and the wife following him up to their room that happens this is such a great family place to relax and rest no one bothers you they keep every thing clean and maintained";
        //String tests = "This is a very good camrea. I like it.";
        for (String sent : sentencer.sentences(tests)) {
            System.out.println(sent);
        }

    }

}
