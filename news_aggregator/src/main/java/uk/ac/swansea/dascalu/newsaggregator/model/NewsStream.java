package uk.ac.swansea.dascalu.newsaggregator.model;

import java.util.LinkedList;
import java.util.List;

public class NewsStream {
    private String name;
    private List<Integer> keywords;

    private NewsStream() {}

    public NewsStream(String name, List<Integer> keywords) {
        this.name = name;
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getKeywords() {
        return keywords;
    }

    public void setKeywords() {
        if(this.keywords == null){
            this.keywords = new LinkedList<>();
        }
    }
}
