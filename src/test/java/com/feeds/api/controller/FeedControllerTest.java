package com.feeds.api.controller;

import com.feeds.api.interfaces.FeedRepository;
import com.feeds.api.model.Feed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class FeedControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @MockBean
    private FeedRepository feedRepository;

    @BeforeEach
    void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    void getFeed() throws Exception{
        Optional<Feed> feed = Optional.of(newFeed());
        when(feedRepository.findById(anyInt())).thenReturn(feed);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/feed/get/0").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void addFeed() throws Exception{
        List<Feed> feeds = Arrays.asList(newFeed());
        when(feedRepository.saveAll(any())).thenReturn(feeds);
        String stringUrl = "http://feeds.nos.nl/nosjournaal?format=xml";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/feed/add").contentType(MediaType.APPLICATION_JSON).content(stringUrl);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"sourceUrl\":\"http://feeds.nos.nl/nosjournaal?format=xml\",\"feedUrl\":\"http://feeds.nos.nl/~r/nosjournaal/~3/5cjNzUPdB5c/2353008\",\"title\":\"Netherlands going to lock-down\",\"description\":\"Dutch Prime Minister talked about lock-down.\",\"publicationDate\":\"Mon, 19 Oct 2020 20:16:55 +0200\",\"imageUrl\":\"https://assets.nos.nl/data/image/2020/10/19/684555/1008x567.jpg\"}]"));
    }

    @Test
    void deleteFeed() throws Exception{
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/feed/delete/0").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    Feed newFeed() {
        Feed feed = new Feed();
        feed.setId(1);
        feed.setSourceUrl("http://feeds.nos.nl/nosjournaal?format=xml");
        feed.setFeedUrl("http://feeds.nos.nl/~r/nosjournaal/~3/5cjNzUPdB5c/2353008");
        feed.setTitle("Netherlands going to lock-down");
        feed.setDescription("Dutch Prime Minister talked about lock-down.");
        feed.setPublicationDate("Mon, 19 Oct 2020 20:16:55 +0200");
        feed.setImageUrl("https://assets.nos.nl/data/image/2020/10/19/684555/1008x567.jpg");

        return feed;
    }
}
