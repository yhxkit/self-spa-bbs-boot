package com.pilot.self_bbs_spa_boot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pilot.self_bbs_spa_boot.entity.enumForEntity.Auth;
import com.pilot.self_bbs_spa_boot.entity.enumForEntity.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter //data 안 쓴 이유는 스택오버플로우때문
@Setter
@Entity
public class Account {
	@Id
    private String email;
    private String password;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @CreationTimestamp
    private LocalDateTime accountCreatedTime;

    @Enumerated(EnumType.STRING)
    private Auth auth= Auth.USER;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ENABLE;

    @OneToMany(mappedBy = "postWriter")
    @JsonIgnore//이거 없으면 재귀적으로 돌면서 오류가 납니다
   // @Lazy //레이지로 해놔도 Dto 안쓰면 500 오류
    private List<Post> posts;

    @OneToMany(mappedBy = "commentWriter")
    @JsonIgnore
    //@Lazy
    private List<Comment> comments;

    

}
