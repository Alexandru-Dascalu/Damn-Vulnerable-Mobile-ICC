package uk.ac.swansea.dascalu.dvmicc.newsaggregator.model;

import java.util.LinkedList;
import java.util.List;

public class User {
    private List<String> bookmarks;
    private List<String> customKeywords;
    private List<NewsStream> customStreams;

    private User() {}

    public User(List<String> bookmarks, List<String> customKeywords, List<NewsStream> customStreams) {
        this.customKeywords = customKeywords;
        this.customStreams = customStreams;
        this.bookmarks = bookmarks;
    }

    public List<String> getBookmarks() {
        return bookmarks;
    }

    public List<String> getCustomKeywords() {
        return customKeywords;
    }

    public List<NewsStream> getCustomStreams() {
        return customStreams;
    }

    public void setCustomKeywords() {
        if(this.customKeywords == null) {
            this.customKeywords = new LinkedList<>();
        }
    }

    public void setBookmarks() {
        if(this.bookmarks == null) {
            this.bookmarks = new LinkedList<>();
        }
    }
}
