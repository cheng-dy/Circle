package circle.po;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
	private long noticeID;
	private String content;
	private Date time;
	private Long classID;
	private String formatTime;

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getClassID() {
		return classID;
	}

	public void setClassID(Long classID) {
		this.classID = classID;
	}
}
