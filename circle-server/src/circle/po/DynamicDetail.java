package circle.po;

import java.io.Serializable;
import java.util.List;

public class DynamicDetail implements Serializable{
	private Dynamic dynamic;
	private List<Comment> comments;
	public DynamicDetail(Dynamic dynamic,List<Comment> comments) {
		this.dynamic=dynamic;
		this.comments=comments;
	}
	public Dynamic getDynamic() {
		return dynamic;
	}
	public void setDynamic(Dynamic dynamic) {
		this.dynamic = dynamic;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
