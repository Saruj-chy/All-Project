package com.example.allproject.Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Nested {

    List<String> tagUrl = new ArrayList<>();

    public Nested() {
    }

    public Nested(List<String> tagUrl) {
        this.tagUrl = tagUrl;
    }

    public List<String> getTagUrl() {
        return tagUrl;
    }

    public void setTagUrl(List<String> tagUrl) {
        this.tagUrl = tagUrl;
    }
}
