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

import java.util.List;

public class Text {
    private String content = null;
    public List<String> terms = null;
    public List<PostaggedToken> tokens = null;
    public List<Sentence> sentences = null;

    private List<Integer> paragraph_boundary = Lists.newArrayList();

    public Text() {

    }

    public Text(String content) {
        this.content = content;
    }

    public Text(List<String> terms, List<PostaggedToken> tokens) {
        this(terms, tokens, null);
    }

    public Text(List<String> terms, List<PostaggedToken> tokens, List<Sentence> sentences) {
        this.terms = terms;
        this.content = StringUtil.untokenize(terms, ' ', true);

        this.tokens = tokens;
        this.sentences = sentences;
    }

    public Text(List<Sentence> sentences) {
        this.terms = Lists.newArrayList();
        this.tokens = Lists.newArrayList();
        this.sentences = sentences;

        for (Sentence sent : this.sentences) {

            for (PostaggedToken token : sent.posTokens()) {
                this.terms.add(token.term());
                this.content = StringUtil.untokenize(this.terms(), ' ', true);
                this.tokens.add(token);
            }
        }
    }

    public static JSONObject json_parse(Text text) {
        JSONObject json_doc = new JSONObject();

        if (text.content() != null) json_doc.put("content", text.content());

        if (text.terms() != null) {
            JSONArray terms_array = new JSONArray();

            for (String term : text.terms()) {
                terms_array.add(term);
            }
            json_doc.put("terms", terms_array);
        }


        if (text.posTokens() != null && text.posTokens().size() > 0) {
            JSONArray json_array = new JSONArray();

            List<PostaggedToken> postokens = text.posTokens();

            for (PostaggedToken postoken : postokens) {

                JSONObject pos_json = PostaggedToken.json_parse(postoken);
                json_array.add(pos_json);

            }

            json_doc.put("postokens", json_array);
        }

        if (text.sentences() != null && text.length() > 0) {
            JSONArray sent_array = new JSONArray();
            for (Sentence sentence : text.sentences) {
                sent_array.add(Sentence.json_parse(sentence));
            }
            json_doc.put("sentences", sent_array);
        }

        if (text.paras() != null && text.paras().size() > 0) {
            JSONArray para_array = new JSONArray();
            for (int bd : text.paras()) {
                para_array.add(bd);
            }
            json_doc.put("paras", para_array);
        }

        return json_doc;
    }

    public static String json_dumps(Text text) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(Text.json_parse(text).toString());
        return gson.toJson(je);
    }

    public static Text load_json(JSONObject json_doc) {
        String content = null;
        if (json_doc.containsKey("content")) content = json_doc.get("content").toString();

        List<String> terms = Lists.newArrayList();
        if (json_doc.get("terms") != null) {
            JSONArray terms_array = (JSONArray) json_doc.get("terms");

            for (int i = 0; i < terms_array.size(); i++) {
                terms.add(terms_array.get(i).toString());

            }
        }


        List<PostaggedToken> tokens = null;
        List<Sentence> sentences = null;
        if (json_doc.containsKey("postokens")) {
            tokens = Lists.newArrayList();
            JSONArray token_array = (JSONArray) json_doc.get("postokens");
            for (int i = 0; i < token_array.size(); i++) {
                tokens.add(PostaggedToken.load_json((JSONObject) token_array.get(i)));
            }
        }

        if (json_doc.containsKey("sentences")) {
            sentences = Lists.newArrayList();
            JSONArray sent_array = (JSONArray) json_doc.get("sentences");
            for (int i = 0; i < sent_array.size(); i++) {
                sentences.add(Sentence.load_json((JSONObject) sent_array.get(i)));
            }
        }

        List<Integer> paras = null;
        if (json_doc.containsKey("paras")) {
            paras = Lists.newArrayList();
            JSONArray para_array = (JSONArray) json_doc.get("paras");
            for (int i = 0; i < para_array.size(); i++) {
                paras.add(Integer.parseInt(para_array.get(i).toString()));
            }
        }

        Text text = new Text();

        if (terms != null) {
            text.setTerms(terms);
        }

        if (tokens != null) {
            text.setPosTokens(tokens);
        }

        if (content != null) {
            text.setContent(content);
        }

        if (sentences != null) {
            text.setSentences(sentences);
        }

        if (paras != null) {
            text.setParas(paras);
        }

        return text;
    }

    public static Text json_loads(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json_doc = (JSONObject) parser.parse(jsonString);
            return Text.load_json(json_doc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> terms() {
        return this.terms;
    }

    public String content() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
        //this.content = StringUtil.untokenize(terms, ' ', true);
    }

    public List<PostaggedToken> posTokens() {
        return this.tokens;
    }

    public void setPosTokens(List<PostaggedToken> tokens) {
        this.tokens = tokens;
    }

    public PostaggedToken token(int index) {
        return this.tokens.get(index);
    }

    public PostaggedToken token(int sent_index, int offset) {
        return this.sentences.get(sent_index).posToken(offset);
    }

    public void setSentences(List<Sentence> sents) {
        this.sentences = sents;
    }

    /**
     * Return the sentence number in the text
     *
     * @return the sentence number
     */
    public int length() {
        if (this.sentences != null && this.sentences.size() > 0) return this.sentences.size();
        else if (this.sentences.size() == 0) return 0;
        else return -1;
    }

    public int tokensize() {
        if (this.tokens != null) return this.posTokens().size();
        return -1;
    }

    public List<Sentence> sentences() {
        return this.sentences;
    }

    public Sentence sentence(int index) {
        return this.sentences.get(index);
    }

    public void addSentence(Sentence sent) {


        for (PostaggedToken token : sent.posTokens()) {
            this.posTokens().add(token);
            this.terms().add(token.term());

        }
        this.content += sent.terms();
    }

    public void setParas(List<Integer> bds) {
        this.paragraph_boundary = bds;
    }

    /**
     * Return the paragraph sentence boundary
     *
     * @return
     */
    public List<Integer> paras() {
        return this.paragraph_boundary;
    }

    public List<Sentence> get_para(int index) {
        if (this.paragraph_boundary != null && this.paragraph_boundary.size() > index) {
            int begin = 0;
            if (index > 0) begin = this.paras().get(index - 1);
            int end = this.paras().get(index);
            List<Sentence> sents = Lists.newArrayList();
            for (int i = begin; i < end; i++) {
                sents.add(this.sentence(i));
            }
            return sents;
        }
        return null;
    }

    public void addPragraph(List<Sentence> sents) {
        for (Sentence sent : sents) {
            for (PostaggedToken token : sent.posTokens()) {
                this.posTokens().add(token);
                this.terms().add(token.term());
            }
        }
        if (this.length() > 0)
            this.paragraph_boundary.add(this.length() + sents.size());
        else
            this.paragraph_boundary.add(sents.size());
    }


}
