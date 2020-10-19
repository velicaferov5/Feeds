package com.feeds.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class Attr {

    private String itemTag;
    private String feedUrl;
    private String title;
    private String description;
    private String publicationDate;
    private String imageUrl;

    public Attr(String itemTag, String feedUrl, String title, String description, String publicationDate, String imageUrl) {
        this.itemTag = itemTag;
        this.feedUrl = feedUrl;
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.imageUrl = imageUrl;
    }

    public String getItemTag() {
        return itemTag;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public enum Nos {
        ITEM_TAG("item"),
        FEED_URL("link"),
        TITLE("title"),
        DESCRIPTION("description"),
        PUBLICATION_DATE("pubDate"),
        IMAGE_URL("enclosure");

        private final String value;
        private final static Map<String, Nos> CONSTANTS = new HashMap<>();

        static {
            for (Nos c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Nos(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Nos fromValue(String value) {
            Nos constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }

}
