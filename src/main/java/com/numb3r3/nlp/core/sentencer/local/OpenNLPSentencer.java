package com.numb3r3.nlp.core.sentencer.local;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.numb3r3.nlp.core.sentencer.Sentencer;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OpenNLPSentencer implements Sentencer {

    public static SentenceDetector detector_instance = null;

    private String default_model = "en-sent.bin";

    @Override
    public void init(String modelName) {

        if (detector_instance == null) {
            if (modelName == null) {
                modelName = default_model;
            }
            SentenceModel sentModel;
            try {

                sentModel = new SentenceModel(this.getClass().getClassLoader()
                        .getResourceAsStream(modelName));
                detector_instance = new SentenceDetectorME(sentModel);
            } catch (InvalidFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    @Override
    public List<String> sentences(final String text) {
        if (detector_instance == null) {
            init(null);
        }

        List<String> sents = Lists.newArrayList();
        for (String sent : Splitter.on("\n").omitEmptyStrings().split(text)) {
            sents.addAll(Arrays.asList(detector_instance.sentDetect(sent)));
        }

        return sents;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Sentencer sentencer = new OpenNLPSentencer();
        sentencer.init(null);

        String tests = "sleep late and still find room under a straw hutbeautiful resort I found the Hotel inside and outside beautifulcheck in took 15 min theres no question that the workers work very hard and deserve tipsI strongly recomend tippingFrom bell boy to check in to maid and if not to drunk bartenderjust kidding I found I was put in bungalow 10which has to be prime locationRoom was beautiful the food was goodthere was never any crowds and even though you should book some of your dinner reservations in advance still had to book a couple and found I had to wait 20 min on a line some rest do not require reservationsthere is also room service and you could order any time thats a bit slow in the eveningsall in all I had a great timeI would recomend thisthe beach is so beautiful I find it better than the bahamas and beach in can not?cunyou will find no rocks or broken shells sand is so smooth on bavaro beachof course there are people taking advantage of all inclusive you do see people ordering room service with loads of bottles of beer or people checking out with 2 or even 3 bottles of beer in their hand one guy came up to the lobby bar and asked for a 6 packtheres nothing wrong with that I only saw one man at the pool get carried by the bartender and the wife following him up to their room that happens this is such a great family place to relax and rest no one bothers you they keep every thing clean and maintained";

        //tests = NLPUtil.clean(tests);


        for (String sent : sentencer.sentences(tests)) {
            System.out.println("SENT: " + sent);
        }

    }

}
