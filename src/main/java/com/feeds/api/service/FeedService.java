package com.feeds.api.service;

import com.feeds.api.interfaces.FeedRepository;
import com.feeds.api.model.Attr;
import com.feeds.api.model.Feed;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Services to get, insert, update, delete Feeds
 */
@Service
public class FeedService {

    private FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    /**
     * Gets and returns Feed by {@param id}
     * @param id
     * @return
     */
    public Optional<Feed> getFeedById(int id) {
         return feedRepository.findById(id);
    }

    /**
     * Gets and returns Feed by {@param url}
     * @param url
     * @return
     */
    public List<Feed> getFeedsByUrl(String url) {
        return feedRepository.findFeedByUrl(url);
    }

    /**
     * Adds or updates feeds by {@param url}
     * @param url
     * @return saved feeds
     */
    public List<Feed> addOrUpdateFeeds(String url) {
        Attr attr;
        //Currently only nos.nl handled
        if (url.contains("nos.nl")) {
            attr = new Attr(Attr.Nos.ITEM_TAG.value(), Attr.Nos.FEED_URL.value(), Attr.Nos.TITLE.value(), Attr.Nos.DESCRIPTION.value(), Attr.Nos.PUBLICATION_DATE.value(), Attr.Nos.IMAGE_URL.value());
            //Crawl, save and return
            return saveFeeds(crawlFeeds(url, attr));
        }

        return Collections.emptyList();
    }

    /**
     * Saves {@param feeds}
     * @param feeds
     * @return saved feeds
     */
    public List<Feed> saveFeeds(List<Feed> feeds) {
        if (feeds == null || feeds.size() == 0) {
            return Collections.emptyList();
        } else {
            return (List<Feed>) feedRepository.saveAll(feeds);
        }
    }

    /**
     * Deletes feeds by Id.
     * @param sourceUrl
     * @return true if deletion successfull, else false
     */
    public boolean deleteFeedsByUrl(String sourceUrl) {
        try {
            feedRepository.deleteFeedsByUrl(sourceUrl);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Crawls feeds on {@param feedUrl} according to {@param attribute} and returns {@link Feed} list
     * @param feedUrl
     * @param attribute
     * @return feeds
     */
    private List<Feed> crawlFeeds(String feedUrl, Attr attribute) {
        List<Feed> feeds = new ArrayList<>();
        try {
            //Fetch the HTML
            Document document = Jsoup.connect(feedUrl).get();
            //Parse the HTML to extract required content
            Elements elements = document.select(attribute.getItemTag());
            //Iterate and get feeds
            for (Element element : elements) {
                Feed feed = toFeed(element, attribute);
                feed.setSourceUrl(feedUrl);
                feeds.add(feed);
            }
        } catch (IOException e) {
            System.err.println("For '" + feedUrl + "': " + e.getMessage());
        }

        return feeds;
    }

    /**
     * Converts element to {@link Feed} and returns
     * @param element
     * @param attribute
     * @return feed
     */
    private Feed toFeed(Element element, Attr attribute) {
        Feed feed = new Feed();
        Elements elements = element.getElementsByAttribute(attribute.getFeedUrl());
        if (elements != null && elements.size() > 0) {
            feed.setFeedUrl(elements.get(0).text());
        }
        elements = element.getElementsByAttribute(attribute.getTitle());
        if (elements != null && elements.size() > 0) {
            feed.setTitle(elements.get(0).text());
        }
        elements = element.getElementsByAttribute(attribute.getDescription());
        if (elements != null && elements.size() > 0) {
            feed.setDescription(elements.get(0).text());
        }
        elements = element.getElementsByAttribute(attribute.getPublicationDate());
        if (elements != null && elements.size() > 0) {
            feed.setPublicationDate(elements.get(0).text());
        }
        elements = element.getElementsByAttribute(attribute.getImageUrl());
        if (elements != null && elements.size() > 0) {
            feed.setImageUrl(elements.get(0).attr("url"));
        }

        return feed;
    }
}
