package circle.service;

import circle.po.User;

import java.util.Map;

import circle.po.Class;
import circle.po.Comment;
import circle.po.Dynamic;
import circle.po.Notice;

public interface IUserService {
	public Map<String, Object> register_m(User user,Class aclass);
	public Map<String, Object> register_tp(User user);
	public Map<String, Object> login(String phone,String password);
	public String getCode(Class aclass);
	public Map<String, Object> getDynamic(String classID);
	public Map<String, Object> getNotice(String classID);
	public String publishNotice(Notice notice);
	public String publishDynamic(Dynamic dynamic);
	public String publishComment(Comment comment);
	public String addPraise(Dynamic dynamic);
	public String updateClass(User user);
	public Map<String, Object> getAllUsers(String classID);
	public String delUser(User user);
	public String delDynamic(Dynamic dynamic);
}