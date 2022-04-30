package com.learning.urlshortener.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.entities.CustomerEntity;
import com.learning.urlshortener.entities.LinkEntity;
import com.learning.urlshortener.repositories.LinkRepository;
import com.learning.urlshortener.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty("init.dummy.data.on.start")
@Profile("!prod")
@Component
public class DataLoader implements CommandLineRunner {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public DataLoader(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0 || linkRepository.count() == 0) {
            addDataToDatabase();
        }
    }

    private void addDataToDatabase() {
        userRepository.deleteAll();
        linkRepository.deleteAll();

        CustomerEntity anton = CustomerEntity.builder()
                .nickname("pechenka")
                .links(new ArrayList<>())
                .build();

        CustomerEntity dima = CustomerEntity.builder()
                .nickname("sd")
                .links(new ArrayList<>())
                .build();

        LinkEntity antonsLink1 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/a1")
                .shortenedUrl("https://shortversion/a1")
                .clickCount(2)
                .customer(anton)
                .build();

        LinkEntity antonsLink2 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/a2")
                .shortenedUrl("https://shortversion/a2")
                .clickCount(1)
                .customer(anton)
                .build();

        LinkEntity antonsLink3 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/a3")
                .shortenedUrl("https://shortversion/a3")
                .clickCount(5)
                .customer(anton)
                .build();

        anton.getLinks().addAll(List.of(antonsLink1, antonsLink2, antonsLink2));

        LinkEntity dimasLink1 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/d1")
                .shortenedUrl("https://shortversion/d1")
                .clickCount(2)
                .customer(dima)
                .build();

        LinkEntity dimasLink2 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/d2")
                .shortenedUrl("https://shortversion/d2")
                .clickCount(6)
                .customer(dima)
                .build();

        LinkEntity dimasLink3 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/d3")
                .shortenedUrl("https://shortversion/d3")
                .clickCount(20)
                .customer(dima)
                .build();

        dima.getLinks().addAll(List.of(dimasLink1, dimasLink2, dimasLink3));

        userRepository.saveAll(List.of(anton, dima));
        log.debug("{} users are added to database.", userRepository.count());

        linkRepository.saveAll(List.of(antonsLink1, antonsLink2, antonsLink3, dimasLink1, dimasLink2, dimasLink3));
        log.debug("{} links are added to database.", linkRepository.count());
    }
}