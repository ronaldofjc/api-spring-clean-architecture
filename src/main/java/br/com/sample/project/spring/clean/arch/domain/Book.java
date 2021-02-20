package br.com.sample.project.spring.clean.arch.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    private Long id;
    private String title;
    private String author;
    private int pages;
    private boolean active;
    private ZonedDateTime creationTime;
    private ZonedDateTime updateTime;
}
