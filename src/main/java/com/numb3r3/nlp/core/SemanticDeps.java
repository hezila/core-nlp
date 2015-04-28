package com.numb3r3.nlp.core;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fwang
 * Date: 13-9-2
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class SemanticDeps {
    private List<SemanticEdge> edges;

    public SemanticDeps() {
        this.edges = Lists.newArrayList();
    }

    public void add(SemanticEdge edg) {
        this.edges.add(edg);
    }

    public List<SemanticEdge> deps() {
        return this.edges;
    }
}
