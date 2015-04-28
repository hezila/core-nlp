package com.numb3r3.nlp.core;

/**
 * Created with IntelliJ IDEA.
 * User: fwang
 * Date: 13-9-2
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public class SemanticEdge {
    private int gov_index = -1;
    private int dep_index = -1;
    private String dep_type = null;

    public SemanticEdge(int gov, int dep, String type) {
        this.gov_index = gov;
        this.dep_index = dep;
        this.dep_type = type;
    }

    public int gov_index() {
        return this.gov_index;
    }

    public int dep_index() {
        return this.dep_index;
    }

    public String dep_type() {
        return this.dep_type;
    }
}
