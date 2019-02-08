package com.pilot.self_bbs_spa_boot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Post implements Article{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postIdx;

    private String title;
    private String postContent;


    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @CreationTimestamp
    private LocalDateTime postTime;

    @ManyToOne
    @JoinColumn(name = "post_writer_email")
    @Lazy
    private Account postWriter;

    @OneToMany(mappedBy = "belongingPost")
    @JsonIgnore//이거 없으면 재귀적으로 돌면서 오류가 납니다
    @Lazy
    private List<Comment> comments;


}
