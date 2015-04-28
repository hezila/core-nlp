package com.numb3r3.nlp.util;

import com.atlascopco.hunspell.Hunspell;
import com.google.common.collect.Lists;
import com.numb3r3.nlp.core.sentencer.Sentencer;
import com.numb3r3.nlp.core.sentencer.local.RitaSentencer;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;

/**
 * Created by fwang on 3/7/14.
 */
public class SpellChecker {
    Hunspell speller = null;

    JLanguageTool langTool = null;
    Sentencer sentencer = new RitaSentencer();

    public SpellChecker() {
        try {
            this.langTool = new JLanguageTool(new BritishEnglish());
            this.langTool.activateDefaultPatternRules();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
//        SpellChecker checker = new SpellChecker();
//        checker.correct("helo");
//        System.out.println("Spell checker");
//        SpellChecker checker = new SpellChecker();
//        speller = new Hunspell(, speller.getClass().getResource("hunspell/en_US.aff").getPath());

       SpellChecker checker = new SpellChecker();

        String text = "We went to Cafe Bello hoppinng forr an romantic nightt out. I callled to make an reservation and was informed that they do not accept reservations for parties of two. We were told that the wait for a table would be 20 minutes; it took an hour and ten minutes. When we were seated it took 15 minutes to get a menu, half an hour for the appetizers, and an hour after the appetizers we finally received our entree. I ordered a chicken breast that was stuffed with cheese and spinach and my chicken was dry and had a strong vinegar taste. The wait staff rarely visited our table to see if we needed anything which meant we had to work to get the things we needed (menus, our main dishes, etc.). I was terribly disappointed in the evening.";
        System.out.println(checker.correct_text(text));
    }

    public String correct_sent(String sent_str) {
        try {
            List<RuleMatch> matches = this.langTool.check(sent_str);
            StringBuffer buffer = new StringBuffer(sent_str);
            for (RuleMatch match : matches) {
                List<String> rpls = match.getSuggestedReplacements();
                if (rpls.size() <= 2 && rpls.size() > 0 && match.getEndColumn() <= buffer.length()) {

                    buffer.replace(match.getColumn() - 1, match.getEndColumn(), " " + rpls.get(0) + " ");
                }
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sent_str;
    }

    public String correct_text(String text) {

        List<String> new_sents = Lists.newArrayList();
        StringBuffer buffer = new StringBuffer();
        for (String sent : sentencer.sentences(text)) {
            buffer.append(this.correct_sent(sent) + " ");
        }
        return buffer.toString();
    }
}
