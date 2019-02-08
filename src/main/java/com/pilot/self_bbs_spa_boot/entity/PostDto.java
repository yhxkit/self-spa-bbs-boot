package com.pilot.self_bbs_spa_boot.entity;

import lombok.Data;

@Data
public class PostDto implements Article{

    private int postIdx;

    private String title;
    private String postContent;
    private String postWriter;
    
	
     
    
}
