package com.mjc.school.repository.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder
public class Author implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;
    @OneToMany(mappedBy = "author")
    private List<News> news;

    public void addNews(News news) {
        this.news.add(news);
        news.setAuthor(this);
    }

    public void removeNews(News news) {
        this.news.remove(news);
        news.setAuthor(null);
    }
}
