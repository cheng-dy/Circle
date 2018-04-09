package com.circle.circle.Bean;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
	private long commentID;
	private Long dynamicID;
	private String content;
	private Long userID;
	private String userName;
	public Comment(){}
	public Comment(String userName,String content){
		this.userName=userName;
		this.content=content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getCommentID() {
		return commentID;
	}

	public void setCommentID(long commentID) {
		this.commentID = commentID;
	}

	public Long getDynamicID() {
		return dynamicID;
	}

	public void setDynamicID(Long dynamicID) {
		this.dynamicID = dynamicID;
	}



	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

}
