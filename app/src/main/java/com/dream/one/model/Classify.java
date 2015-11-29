package com.dream.one.model;

/**
 * Created by CNKI-0000 on 2015/11/27.
 */
public class Classify {
    public String id;
    public String name;

    public Classify() {
    }

    public Classify(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + " : " + this.name;
    }
}
