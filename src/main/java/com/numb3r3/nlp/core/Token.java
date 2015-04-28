package com.numb3r3.nlp.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.numb3r3.common.exception.EnrichableException;
import com.numb3r3.common.util.HashCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Token {

    /* The token's string term */
    private String term = null;
    /* The token's index in the dictionary */
    private int word = -1;
    /* The position of the token occurred in a sentence */
    private int offset = -1;


    public Token(String term) {
        this.term = term;
    }

    public Token(String term, int offset) {
        this.term = term;
        this.offset = offset;
    }

    public Token(String term, int word, int offset) {
        this.term = term;
        this.word = word;
        this.offset = offset;
    }

    public static String json_dumps(Token token) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(Token.json_parse(token).toString());
        return gson.toJson(je);
    }

    public static JSONObject json_parse(Token token) {
        JSONObject json_doc = new JSONObject();
        if (token.term() != null)
            json_doc.put("term", token.term());
        json_doc.put("word", token.word());
        json_doc.put("offset", token.offset());
        return json_doc;
    }

    public static Token json_loads(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json_doc = (JSONObject) parser.parse(jsonString);
            return Token.load_json(json_doc);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (EnrichableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Token load_json(JSONObject json_doc) throws EnrichableException {
        String term = null;
        if (json_doc.containsKey("term") && json_doc.get("term") != null) {
            term = json_doc.get("term").toString();
        }
        int word = -1;
        if (json_doc.containsKey("word")) {
            word = Integer.parseInt(json_doc.get("word").toString());
        }
        int offset = -1;
        if (json_doc.containsKey("offset")) {
            offset = Integer.parseInt(json_doc.get("offset").toString());
        }

        if (term == null && word == -1) {
            throw new EnrichableException("TOKEN_JSON_LOAD", "JSON_LOAD_ERROR", "None term and word information.");
        }
        return new Token(term, word, offset);

    }


    public int word() {
        return this.word;
    }

    public void setWord(int word) {
        this.word = word;
    }

    public int offset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String term() {
        return this.term;
    }

    public void setTerm(String other) {
        this.term = other;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     * TODO implement hashCode function
     */
    @Override
    public int hashCode() {
        HashCode h = new HashCode();
        h.addValue(this.term);
        h.addValue(this.offset);
        h.addValue(this.word);
        return h.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     * TODO implement equals methods
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else if (obj == this) {
            return true;
        }
        Token cobj = (Token) obj;

        return cobj.hashCode() == this.hashCode();

    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Token [term='" + this.term + ", word=" + this.word + "', offset=" + this.offset + "]";
    }


    public static void main(String[] argv) {
        Token token = new Token(null, -1);
        System.out.println("Token json string: " + Token.json_dumps(token));
        System.out.print("Token parser json" + Token.load_json(Token.json_parse(token)));
    }
}
