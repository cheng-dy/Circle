package com.circle.circle.Bean;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

public class Dynamic implements Serializable {
	private long dynamicID;
	private Long userID;
	private String content;
	private String imageUrls;
	private Integer likeAmount;
	private String userName;
	private String headerImg;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeaderImg() {
		return headerImg;
	}

	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}

	public Integer getLikeAmount() {
		return likeAmount;
	}

	public void setLikeAmount(Integer likeAmount) {
		this.likeAmount = likeAmount;
	}

	public long getDynamicID() {
		return dynamicID;
	}

	public void setDynamicID(long dynamicID) {
		this.dynamicID = dynamicID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

}
