package com.numb3r3.nlp.core;


import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.numb3r3.common.StringUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Sentence {

    /* Sentence raw string */
    private String terms = null;

    /* Sentence's word identifications  */
    private List<Integer> words = null;

    private List<PostaggedToken> posTokens = null;

    private SemanticDeps deps = null;


    /* the offset of the sentence in a paragraph or article */
    private int offset = -1;


    public Sentence() {
    }


    public Sentence(String terms) {
        this.terms = terms;
    }


    public Sentence(List<PostaggedToken> postags) {
        List<String> raw_tokens = new ArrayList<String>();
        this.words = new ArrayList<Integer>();

        for (PostaggedToken token : postags) {
            raw_tokens.add(token.term());
            this.words.add(token.word());
        }

        this.terms = StringUtil.untokenize(raw_tokens, ' ', true);

        this.posTokens = postags;
        this.deps = null;
    }

    public Sentence(String terms, List<PostaggedToken> postags) {
        this.terms = terms;

        this.posTokens = postags;
        this.deps = null;
    }

    public Sentence(List<PostaggedToken> postags, int offset) {
        this(postags);

        this.offset = offset;
    }

    public Sentence(List<PostaggedToken> postags, SemanticDeps deps) {
        this(postags);
        this.deps = deps;
    }

    public Sentence(List<PostaggedToken> postags, SemanticDeps deps, int offset) {
        this(postags, deps);
        this.offset = offset;

    }


    public int offset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setDeps(SemanticDeps deps) {
        this.deps = deps;
    }

    public SemanticDeps deps() {
        return this.deps;
    }

    public List<PostaggedToken> posTokens() {
        return this.posTokens;
    }

    public void setPosTokens(List<PostaggedToken> tokens) {
        this.posTokens = tokens;
        List<String> raw_tokens = new ArrayList<String>();
        this.words = new ArrayList<Integer>();

        for (PostaggedToken token : tokens) {
            raw_tokens.add(token.term());
            this.words.add(token.word());
        }

        this.terms = StringUtil.untokenize(raw_tokens, ' ', true);
        this.deps = null; // FIXME: the deps must be cleaned
    }

    public List<Token> tokens() {
        List<Token> tokens = Lists.newArrayList();
        int index = 0;
        for (PostaggedToken postoken : this.posTokens()) {
            Token token = new Token(postoken.term(), postoken.word(), index);
            tokens.add(token);
            index++;
        }
        return tokens;
    }

    public Token token(int index) {
        return this.posToken(index).token();

    }

    public PostaggedToken posToken(int index) {
        return this.posTokens.get(index);
    }

    public String terms() {
        return this.terms;
    }

    // FIXME: update the postokens and deps
    public void setTerms(String terms) {
        this.terms = terms;

    }

    public List<Integer> words() {
        return this.words;
    }

    public void setWords(List<Integer> words) {
        this.words = words;
    }

    public int size() {
        if (this.posTokens() != null) return this.posTokens().size();
        else if (this.words() != null) return this.words().size();
        else if (this.terms() != null) {
            String temp = StringUtil.stripPunctuation(this.terms());
            return temp.split(" ").length;
        }
        return -1;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (deps != null) {
            return "Sentence [tokens=" + tokens() + ", posTokens="
                    + posTokens + ", deps=" + "..." + "]";
        } else {
            return "Sentence [tokens=" + tokens() + ", posTokens="
                    + posTokens + "]";
        }

    }

    public static JSONObject json_parse(Sentence sentence) {
        JSONObject json_doc = new JSONObject();
        JSONArray json_array = new JSONArray();
        List<PostaggedToken> postokens = sentence.posTokens();
        if (sentence.offset() >= 0)
            json_doc.put("offset", sentence.offset());

        for (PostaggedToken postoken : postokens) {

            JSONObject pos_json = PostaggedToken.json_parse(postoken);
            json_array.add(pos_json);

        }

        json_doc.put("postokens", json_array);

        // deps relationships
        if (sentence.deps() != null) {
            JSONArray deps_json = new JSONArray();

            for (SemanticEdge edg : sentence.deps().deps()) {
                JSONObject dep_json = new JSONObject();

                dep_json.put("gov", edg.gov_index());
                dep_json.put("dep", edg.dep_index());
                dep_json.put("type", edg.dep_type());
                deps_json.add(dep_json);

            }
            if (deps_json.size() > 0)
                json_doc.put("deps", deps_json);
        }

        return json_doc;
    }


    public static String json_dumps(Sentence sentence) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(Sentence.json_parse(sentence).toString());

        return gson.toJson(je);
    }


    public static Sentence json_loads(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json_doc = (JSONObject) parser.parse(jsonString);
            return Sentence.load_json(json_doc);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    public static Sentence load_json(JSONObject json_doc) {
        int offset = -1;
        if (json_doc.containsKey("offset"))
            offset = Integer.parseInt(json_doc.get("offset").toString());

        List<PostaggedToken> postokens = Lists.newArrayList();
        JSONArray json_array = (JSONArray) json_doc.get("postokens");
        for (int i = 0; i < json_array.size(); i++) {
            JSONObject job = (JSONObject) json_array.get(i);
            postokens.add(PostaggedToken.load_json(job));
        }

        SemanticDeps deps = null;

        if (json_doc.containsKey("deps") && json_doc.get("deps") != null) {
            deps = new SemanticDeps();
            JSONArray deps_json = (JSONArray) json_doc.get("deps");
            for (int i = 0; i < deps_json.size(); i++) {
                JSONObject dep_json = (JSONObject) json_array.get(i);
                int gov = Integer.parseInt(dep_json.get("gov").toString());
                int dep = Integer.parseInt(dep_json.get("dep").toString());
                String type = dep_json.get("type").toString();
                deps.add(new SemanticEdge(gov, dep, type));
            }
        }

        return new Sentence(postokens, deps, offset);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
