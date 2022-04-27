package com.learning.urlshortener.bootstrap;

import com.learning.urlshortener.domains.Link;
import com.learning.urlshortener.domains.Customer;
import com.learning.urlshortener.repositories.LinkRepository;
import com.learning.urlshortener.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public DataLoader(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findAll().size() == 0 || linkRepository.findAll().size() == 0) {
            addDataToDatabase();
        }
    }

    private void addDataToDatabase() {
        userRepository.deleteAll();
        linkRepository.deleteAll();

        Customer anton = Customer.builder()
                .nickname("pechenka")
                .email("anton@mail.com")
                .password("weak password")
                .links(new ArrayList<>())
                .build();

        Customer dima = Customer.builder()
                .nickname("sd")
                .email("dima@mail.com")
                .password("strong password")
                .links(new ArrayList<>())
                .build();

        Link antonsLink1 = Link.builder()
                .url("https://somehost.com/urltobeshortened/a1")
                .shortenedUrl("https://shortversion/a1")
                .clickCount(2)
                .customer(anton)
                .build();

        Link antonsLink2 = Link.builder()
                .url("https://somehost.com/urltobeshortened/a2")
                .shortenedUrl("https://shortversion/a2")
                .clickCount(1)
                .customer(anton)
                .build();

        Link antonsLink3 = Link.builder()
                .url("https://somehost.com/urltobeshortened/a3")
                .shortenedUrl("https://shortversion/a3")
                .clickCount(5)
                .customer(anton)
                .build();

        anton.getLinks().addAll(List.of(antonsLink1, antonsLink2, antonsLink2));

        Link dimasLink1 = Link.builder()
                .url("https://somehost.com/urltobeshortened/d1")
                .shortenedUrl("https://shortversion/d1")
                .clickCount(2)
                .customer(dima)
                .build();

        Link dimasLink2 = Link.builder()
                .url("https://somehost.com/urltobeshortened/d2")
                .shortenedUrl("https://shortversion/d2")
                .clickCount(6)
                .customer(dima)
                .build();

        Link dimasLink3 = Link.builder()
                .url("https://somehost.com/urltobeshortened/d3")
                .shortenedUrl("https://shortversion/d3")
                .clickCount(20)
                .customer(dima)
                .build();

        dima.getLinks().addAll(List.of(dimasLink1, dimasLink2, dimasLink3));

        userRepository.saveAll(List.of(anton, dima));
        log.debug(userRepository.findAll().size() + " users are added to H2 database.");

        linkRepository.saveAll(List.of(antonsLink1, antonsLink2, antonsLink3, dimasLink1, dimasLink2, dimasLink3));
        log.debug(linkRepository.findAll().size() + " links are added to H2 database.");
    }
}
