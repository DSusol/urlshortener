package com.learning.urlshortener.database.links;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.learning.urlshortener.database.customers.CustomerEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "link")
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shortened_url", unique = true)
    @NotNull
    private String shortenedUrl;

    @Column(name = "url")
    @NotNull
    private String url;

    @Column(name = "click_count")
    @Builder.Default
    private Integer clickCount = 0;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "customer")
    private CustomerEntity customer;
}
