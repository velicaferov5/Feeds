package com.feeds.api.service;

import com.feeds.api.interfaces.FeedRepository;
import com.feeds.api.model.Feed;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
class FeedServiceTest {

    @Mock
    private FeedRepository feedRepository;

    @InjectMocks
    private FeedService feedService;

    @Test
    void getFeedById() {
        Optional<Feed> feed = Optional.of(newFeed());
        when(feedRepository.findById(anyInt())).thenReturn(feed);
        Optional<Feed> expected = feedService.getFeedById(0);
        assertTrue(expected.isPresent());
        assertFeed(feed.get(), expected.get());
    }

    @Test
    void getFeedByUrl() {
        List<Feed> feeds = Arrays.asList(newFeed());
        when(feedRepository.findFeedByUrl(anyString())).thenReturn(feeds);
        assertFeeds(feeds, feedService.getFeedsByUrl("http://feeds.nos.nl/nosjournaal?format=xml"));
    }

    @Test
    void addOrUpdateFeeds() {
        List<Feed> feeds = Arrays.asList(newFeed());
        when(feedRepository.saveAll(any())).thenReturn(feeds);
        List<Feed> actual = feedService.addOrUpdateFeeds("http://feeds.nos.nl/nosjournaal?format=xml");
        assertFeeds(feeds, actual);
    }

    @Test
    void saveFeeds() {
        List<Feed> feeds = Arrays.asList(newFeed());
        when(feedRepository.saveAll(any())).thenReturn(feeds);
        List<Feed> actual = feedService.saveFeeds(feeds);
        assertFeeds(feeds, actual);
    }

    @Test
    void deleteFeeds() {
        assertTrue(feedService.deleteFeedsByUrl("http://feeds.nos.nl/nosjournaal?format=xml"));
    }

    Feed newFeed() {
        Feed feed = new Feed();
        feed.setId(1);
        feed.setSourceUrl("http://feeds.nos.nl/nosjournaal?format=xml");
        feed.setFeedUrl("http://feeds.nos.nl/~r/nosjournaal/~3/5cjNzUPdB5c/2353008");
        feed.setTitle("Nokia kondigt mobiel netwerk op de maan aan");
        feed.setDescription("Over een paar jaar kunnen astronauten hun mobieltje rustig meenemen als ze naar de maan gaan. Nokia zegt dat het aan de slag gaat met de bouw van een eerste mobiel netwerk op de maan.\n" +
                "\n" +
                "Volgens de Finse fabrikant heeft de Amerikaanse ruimtevaartorganisatie NASA opdracht gegeven voor het aanleggen van 4G op de maan. De apparatuur daarvoor zou eind 2022 met een maanlander naar z'n bestemming moeten worden gebracht.\n" +
                "\n" +
                "NASA is al langer bezig met plannen voor langere verblijven van mensen op de maan. Astronauten zouden een 4G-verbinding onder meer kunnen gebruiken voor het op afstand besturen van wagentjes, navigatie en het versturen van videobeelden.\n" +
                "\n" +
                "Betrouwbaar\n" +
                "\"Betrouwbare en hoogwaardige communicatienetwerken zijn essentieel voor menselijk leven op het maanoppervlak\", zegt het hoofd techniek van Nokia. De Amerikaanse tak van het Finse bedrijf krijgt ruim 14 miljoen dollar om zendapparatuur te bouwen die sterk genoeg is om de reis door de ruimte en de omstandigheden op de maan te kunnen doorstaan.\n" +
                "\n" +
                "Betrouwbaarheid is ook de reden dat Nokia kiest voor het aanleggen van 4G op de maan, en niet het nieuwere 5G. Dat laatste netwerk heeft zich volgens de Finnen nog niet genoeg in de praktijk bewezen om in de ruimte te functioneren.");
        feed.setPublicationDate("Mon, 19 Oct 2020 20:16:55 +0200");
        feed.setImageUrl("https://assets.nos.nl/data/image/2020/10/19/684538/1008x567.jpg");
        return feed;
    }

    void assertFeed(Feed expected, Feed actual) {
        assertTrue((expected == null && actual == null) || (expected != null && actual != null));
        if (expected != null) {
            assertEquals(expected.getSourceUrl(), actual.getSourceUrl());
            assertEquals(expected.getFeedUrl(), actual.getFeedUrl());
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getDescription(), actual.getDescription());
            assertEquals(expected.getPublicationDate(), actual.getPublicationDate());
            assertEquals(expected.getImageUrl(), actual.getImageUrl());
        }
    }

    void assertFeeds(List<Feed> expected, List<Feed> actual) {
        assertEquals(expected.size(), actual.size());
        for (int index=0; index<expected.size(); index++) {
            assertFeed(expected.get(index), actual.get(index));
        }
    }
}
