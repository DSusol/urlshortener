package com.learning.urlshortener.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.learning.urlshortener.database.customers.CustomerEntity;
import com.learning.urlshortener.database.customers.CustomerRepository;
import com.learning.urlshortener.database.links.LinkEntity;
import com.learning.urlshortener.database.links.LinkRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty("init.dummy.data.on.start")
@Profile("!prod")
@Component
public class DataLoader implements CommandLineRunner {

    private final LinkRepository linkRepository;
    private final CustomerRepository userRepository;

    public DataLoader(LinkRepository linkRepository, CustomerRepository userRepository) {
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
                .chatId(1L)
                .links(new ArrayList<>())
                .build();

        CustomerEntity dima = CustomerEntity.builder()
                .chatId(2L)
                .links(new ArrayList<>())
                .build();

        LinkEntity antonsLink1 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/a1")
                .token("abcde1")
                .clickCount(2)
                .customer(anton)
                .build();

        LinkEntity antonsLink2 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/a2")
                .token("abcde2")
                .clickCount(1)
                .customer(anton)
                .build();

        LinkEntity antonsLink3 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/a3")
                .token("abcde3")
                .clickCount(5)
                .customer(anton)
                .build();

        anton.getLinks().addAll(List.of(antonsLink1, antonsLink2, antonsLink2));

        LinkEntity dimasLink1 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/d1")
                .token("abcde4")
                .clickCount(2)
                .customer(dima)
                .build();

        LinkEntity dimasLink2 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/d2")
                .token("abcde5")
                .clickCount(6)
                .customer(dima)
                .build();

        LinkEntity dimasLink3 = LinkEntity.builder()
                .url("https://somehost.com/urltobeshortened/d3")
                .token("abcde6")
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