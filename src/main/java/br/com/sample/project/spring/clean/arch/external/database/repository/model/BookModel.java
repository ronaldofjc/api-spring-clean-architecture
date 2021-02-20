package br.com.sample.project.spring.clean.arch.external.database.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_BOOK")
public class BookModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String author;
    private int pages;
    private boolean active;

    @Column(name = "CREATION_TIME")
    private ZonedDateTime creationTime;

    @Column(name = "UPDATE_TIME")
    private ZonedDateTime updateTime;
}
