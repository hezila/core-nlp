package com.numb3r3.nlp.util.wordnet;

import com.numb3r3.nlp.core.postagger.PosTagCategoryFactory;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;

public class WNLemmatizer {

    private Dictionary wordnet;
    public IndexMap compoundTermsIndex;

    /**
     * Constructor for class filters.Lemmatizer
     */
    public WNLemmatizer() {
        wordnet = DictionaryFactory.getWordnetInstance();
        compoundTermsIndex = DictionaryFactory.setupCompoundTermsIndex(wordnet);
    }

    public WNLemmatizer(Dictionary wordnet, IndexMap compoundTermsIndex) {
        this.wordnet = wordnet;
        this.compoundTermsIndex = compoundTermsIndex;
    }

    public Synset[] lookupWord(String word) {
        try {
            IndexWord[] lookup_results = wordnet.lookupAllIndexWords(word)
                    .getIndexWordArray();
            Synset[] synsets = new Synset[lookup_results.length];
            for (int i = 0; i < lookup_results.length; i++) {
                IndexWord lookup_result = lookup_results[i];
                synsets[i] = new Synset(PosTagCategoryFactory.toCategory(lookup_result
                        .getSense(1).getPOS()), lookup_result.getSense(1)
                        .getOffset());
            }

            return synsets;
        } catch (JWNLException e) {
            System.err
                    .println("In method lookupWord: A problem occured when attempting to read data from Wordnet");
            return new Synset[0];
        }

    }

    public Synset lookupWord(String word, POS pos) {
        try {
            IndexWord lookup_result = wordnet.lookupIndexWord(pos, word);
            if (lookup_result != null)
                return new Synset(PosTagCategoryFactory.toCategory(pos), lookup_result
                        .getSense(1).getOffset());
            else
                return null;
        } catch (JWNLException e) {
            System.err
                    .println("In method lookupWord: A problem occured when attempting to read data from Wordnet");
            return null;
        }
    }

    // public Lemma lookupSingleWordLemma(Lemma lemma) {
    // Lemma lemma_to_return = null;
    // try {
    // IndexWord lookup_result = wordnet.lookupIndexWord(lemma.getPos(),
    // lemma.getLemma());
    // if (lookup_result != null) {
    // lemma_to_return = new Lemma(lookup_result.getLemma(),
    // lookup_result.getSense(1));
    // } else {
    // lemma_to_return = new Lemma(lemma.getLemma());
    // lemma_to_return.setPos(lemma.getPos());
    // lemma_to_return.setNotFound();
    // }
    //
    // return lemma_to_return;
    // } catch (JWNLException e) {
    // AppLogger.error
    // .log(Level.SEVERE,
    // "In method lookupSingleWordLemma: A problem occured when attempting to read data from Wordnet");
    // return null;
    // }
    // }
    //
    // public Lemma lookupCompoundLemma(Lemma lemma) throws RuntimeException {
    // if (lemma.getLength() > 1) {
    // try {
    // IndexWordSet candidate_lemma_set = wordnet
    // .lookupAllIndexWords(lemma.getLemma());
    // IndexWord[] candidate_indexwords = candidate_lemma_set
    // .getIndexWordArray();
    //
    // Lemma lemma_to_return = new Lemma();
    // for (IndexWord candidate_indexword : candidate_indexwords) {
    // Lemma candidate_lemma = new Lemma();
    // candidate_lemma.appendTokensToLemma(candidate_indexword
    // .getLemma());
    // candidate_lemma.setSynset(candidate_indexword.getSense(1));
    // if (candidate_lemma.getLength() > lemma_to_return
    // .getLength()
    // || candidate_lemma.getWordLength() > lemma_to_return
    // .getWordLength()) {
    // lemma_to_return = candidate_lemma;
    // }
    // }
    //
    // if (lemma_to_return.getLength() != 0) {
    // lemma_to_return.overrideLength(lemma.getLength());
    // return lemma_to_return;
    // }
    // } catch (JWNLException e) {
    // AppLogger.error
    // .log(Level.SEVERE,
    // "In method lookupCompoundLemma: A problem occured when attempting to read data from Wordnet");
    // }
    //
    // AppLogger.error.log(Level.WARNING, "An indexed compound lemma ("
    // + lemma.getLemma() + ") was not found in Wordnet");
    // return lemma;
    // // throw new RuntimeException("Lookup error");
    // } else
    // return lemma;
    //
    // }
}
