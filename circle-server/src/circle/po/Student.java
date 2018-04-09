package circle.po;

import java.io.Serializable;

public class Student implements Serializable {
	private long childID;
	private Long classID;
	private String name;
	private boolean sex;

	public long getChildID() {
		return childID;
	}

	public void setChildID(long childID) {
		this.childID = childID;
	}

	public Long getClassID() {
		return classID;
	}

	public void setClassID(Long classID) {
		this.classID = classID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

}
