package com.pilot.self_bbs_spa_boot.entity;

import lombok.Data;

@Data
public class CommentDto implements Article{

	 private int commentIdx;
	
	 private String commentContent;
	 private int belongingPostIdx;
	 private String commentWriterEmail;
	 
	 
}
