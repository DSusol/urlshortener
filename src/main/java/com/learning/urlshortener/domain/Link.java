package com.learning.urlshortener.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {

    private Long id;
    private String shortenedUrl;
    private String url;
    private Integer clickCount;

}
