package com.mjc.school.repository.model;


import com.mjc.school.repository.annotation.Length;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @EntityListeners(AuditingEntityListener.class)

    public class News implements BaseEntity<Long> {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @Length(min = 5,max = 30)
        private String title;
        @Length(min = 5,max = 225)
        private String content;
        @Column(updatable = false, nullable = false)
        @CreatedDate
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
        private LocalDateTime createDate;
        @Column(nullable = false)
        @LastModifiedDate
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
        private LocalDateTime lastUpdateDate;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id")
        private Author author;
        @ManyToMany()
        @JoinTable(
                name = "news_tag",
                joinColumns = {@JoinColumn(name = "news_id")},
                inverseJoinColumns = {@JoinColumn(name = "tag_id")})
        private Set<Tag> tags;
    }
