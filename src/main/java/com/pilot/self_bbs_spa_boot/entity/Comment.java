package com.pilot.self_bbs_spa_boot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment implements Article{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentIdx;

    private String commentContent;

    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @CreationTimestamp
    private LocalDateTime commentTime;

    @ManyToOne
    @JoinColumn(name = "belonging_post_idx")
    private Post belongingPost;

    @ManyToOne
    @JoinColumn(name = "comment_writer_email")
    private Account commentWriter;


}
