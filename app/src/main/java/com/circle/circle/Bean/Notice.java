package com.circle.circle.Bean;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
	private long noticeID;
	private String content;
	private String formatTime;
	private Long classID;

	public long getNoticeID() {
		return noticeID;
	}

	public void setNoticeID(long noticeID) {
		this.noticeID = noticeID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

	public Long getClassID() {
		return classID;
	}

	public void setClassID(Long classID) {
		this.classID = classID;
	}
}
