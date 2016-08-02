package com.example.james.wordsearch;

/**
 * Created by James on 8/1/2016.
 */
public class Word {
    private long id;
    private String name;
    private String definition;
    private String other;

    public Word() {
        setId(0);
        setName("");
        setDefinition("");
        setOther("");
    }

    public Word(long id, String name, String definition, String other) {
        this.setId(id);
        this.setName(name);
        this.setDefinition(definition);
        this.setOther(other);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
