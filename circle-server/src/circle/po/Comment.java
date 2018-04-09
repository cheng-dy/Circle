package circle.po;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
	private long commentID;
	private Long dynamicID;
	private Date time;
	private String content;
	private Long userID;
	private String userName;

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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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
