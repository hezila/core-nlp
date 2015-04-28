package com.numb3r3.nlp.core;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.numb3r3.common.util.HashCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PostaggedToken extends Token {

    private String postag = null;

    private String lemma = null;

    private String stem = null;

    public PostaggedToken(Token token) {
        super(token.term(), token.word(), token.offset());
        this.postag = null;
        this.lemma = null;
        this.stem = null;
    }

    public PostaggedToken(String term, int word, int offset, String postag, String lemma, String stem) {
        super(term, word, offset);
        this.postag = postag;
        this.lemma = lemma;
        this.stem = stem;
    }

    public PostaggedToken(String term, int offset, String postag, String lemma) {
        super(term);
        this.setOffset(offset);
        this.postag = postag;
        this.lemma = lemma;
    }

    public PostaggedToken(Token token, String postag, String lemma, String stem) {
        this(token.term(), token.word(), token.offset(), postag, lemma, stem);
    }


    public String postag() {
        return this.postag;
    }

    public void setPosTag(String postag) {
        this.postag = postag;
    }

    public String lemma() {
        return this.lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String stem() {
        return this.stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public Token token() {
        return new Token(this.term(), this.word(), this.offset());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     * TODO implement hashCode function
     */
    @Override
    public int hashCode() {
        HashCode h = new HashCode();
        h.addValue(this.token());

        h.addValue(this.postag);
        h.addValue(this.lemma);
        h.addValue(this.stem);
        return h.hashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     * TODO implement equals function
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else if (obj == this) {
            return true;
        }
        PostaggedToken posToken = (PostaggedToken) obj;

        return posToken.hashCode() == this.hashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Token [term='" + this.term() + "', offset=" + this.offset()
                + ", postag=" + this.postag() + ", lemma=" + this.lemma() + "]";
    }

    public static String json_dumps(PostaggedToken postoken) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(PostaggedToken.json_parse(postoken).toString());
        return gson.toJson(je);
    }

    public static JSONObject json_parse(PostaggedToken postoken) {
        JSONObject json_doc = Token.json_parse(postoken.token());
        if (postoken.postag() != null)
            json_doc.put("postag", postoken.postag());
        if (postoken.lemma() != null)
            json_doc.put("lemma", postoken.lemma());
        if (postoken.stem() != null)
            json_doc.put("stem", postoken.stem());
        return json_doc;
    }

    public static PostaggedToken load_json(JSONObject json_doc) {
        Token token = Token.load_json(json_doc);
        PostaggedToken ptoken = new PostaggedToken(token);

        if (json_doc.containsKey("postag") && json_doc.get("postag") != null) {
            ptoken.setPosTag(json_doc.get("postag").toString());
        }

        if (json_doc.containsKey("lemma") && json_doc.get("lemma") != null) {
            ptoken.setLemma(json_doc.get("lemma").toString());
        }

        if (json_doc.containsKey("stem") && json_doc.get("stem") != null) {
            ptoken.setStem(json_doc.get("stem").toString());
        }

        return ptoken;
    }

    public static PostaggedToken json_loads(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json_doc = (JSONObject) parser.parse(jsonString);
            return PostaggedToken.load_json(json_doc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isProperNoun(PostaggedToken token) {
        if (token.postag().equals("NNP") || token.postag().equals("NNPS")) {
            return true;
        }
        return false;
    }

    public static boolean isCommonNoun(PostaggedToken token) {
        if (token.postag.equals("NN") || token.postag().equals("NNS")) {
            return true;
        }
        return false;
    }

    public static boolean isNoun(PostaggedToken token) {
        return isProperNoun(token) || isCommonNoun(token);
    }

    public static boolean isPluralNoun(PostaggedToken token) {
        if (token.postag().equals("NNS") || token.postag().equals("NNPS")) {
            return true;
        }
        return false;
    }

    public static boolean isVerbBase(PostaggedToken token) {
        return token.postag().equals("VB");
    }

    public static boolean isVerbPast(PostaggedToken token) {
        return token.postag().equals("VBD");
    }

    public static boolean isVerbGerund(PostaggedToken token) {
        return token.postag().equals("VBG");
    }

    public static boolean isVerbPastParticiple(PostaggedToken token) {
        return token.postag().equals("VBN");
    }

    public static boolean isVerbNon3pPersent(PostaggedToken token) {
        return token.postag().equals("VBP");
    }

    public static boolean isVerb3pPresent(PostaggedToken token) {
        return token.postag().equals("VBZ");
    }

    public static boolean isVerbPresent(PostaggedToken token) {
        return isVerbNon3pPersent(token) || isVerb3pPresent(token);
    }

    public static boolean isVerb(PostaggedToken token) {
        return token.postag().equals("VB");
    }

    public static boolean isComparativeAdjective(PostaggedToken token) {
        return token.postag().equals("JJR");
    }

    public static boolean isSuperlativeAdjective(PostaggedToken token) {
        return token.postag().equals("JJS");
    }

    public static boolean isAdjective(PostaggedToken token) {
        return token.postag().equals("JJ") || isComparativeAdjective(token)
                || isSuperlativeAdjective(token);
    }

    public static boolean isPersonalPronoun(PostaggedToken token) {
        return token.postag().equals("PRP");
    }

    public static boolean isPossessivePronoun(PostaggedToken token) {
        return token.postag().equals("PRP$");
    }

    public static boolean isPronoun(PostaggedToken token) {
        return isPersonalPronoun(token) || isPossessivePronoun(token);
    }

    public static boolean isPossessive(PostaggedToken token) {
        return isPossessivePronoun(token) || token.postag().equals("POS");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
