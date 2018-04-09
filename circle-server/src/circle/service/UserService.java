package circle.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import circle.dao.UserDAO;
import circle.po.Class;
import circle.po.Comment;
import circle.po.Dynamic;
import circle.po.User;
import circle.po.DynamicDetail;
import circle.po.Notice;

public class UserService implements IUserService {
	private UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public Map<String, Object> login(String phone, String password) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		String hql="from User where phoneNumber='"+phone+"' and password='"+password+"'";
		result.put("result", userDAO.findOne(hql));
		return result;
	}

	@Override
	public Map<String, Object> register_m(User user, Class aclass) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		if(userDAO.save(aclass)) {
			user.setClassID(Long.parseLong(getCode(aclass)));
			if(userDAO.save(user)) {
				String hql="from User where phoneNumber='"+user.getPhoneNumber()+"' and username='"+user.getUsername()+"'";
				result.put("result", userDAO.findOne(hql));
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> register_tp(User user) {
		// TODO Auto-generated method stub
		userDAO.save(user);
		Map<String, Object> result = new HashMap<String, Object>();
		String hql="from User where phoneNumber='"+user.getPhoneNumber()+"' and username='"+user.getUsername()+"'";
		result.put("result", userDAO.findOne(hql));
		return result;
	}

	@Override
	public String getCode(Class aclass) {
		// TODO Auto-generated method stub
		String hql="from Class where school='"+aclass.getSchool()+"' and className='"+aclass.getClassName()+"'";
		if(userDAO.findOne(hql)!=null)
		{
			return ((Class)userDAO.findOne(hql)).getCode()+"";
		}
		return "fail";
	}

	@Override
	public Map<String, Object> getDynamic(String classID) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		String hql = "from Dynamic where userID in (select userID from User where classID=" + classID+") order by time DESC";
		List<Dynamic> dynamics=userDAO.findList(hql); 
		List<DynamicDetail> dds=new ArrayList<>();
		for(int i=0;i<dynamics.size();i++) {
			String hql1 = "from Comment where dynamicID=" + dynamics.get(i).getDynamicID()+" order by time";
			String hql2="from User where userID="+dynamics.get(i).getUserID();
			User user=userDAO.findOne(hql2);
			dynamics.get(i).setHeaderImg(user.getHeaderImg());
			dynamics.get(i).setUserName(user.getUsername());
			List<Comment>comments=userDAO.findList(hql1);
			for(int j=0;j<comments.size();j++) {
				String hql3="from User where userID="+comments.get(j).getUserID();
				comments.get(j).setUserName(((User)(userDAO.findOne(hql3))).getUsername());
			}
			dds.add(new DynamicDetail(dynamics.get(i), comments));
		}
		result.put("results", dds);
		return result;
	}

	@Override
	public Map<String, Object> getNotice(String classID) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		String hql = "from Notice where classID=" + classID+" order by time DESC";
		List<Notice> notices=userDAO.findList(hql);
		if(notices!=null) {
			for(int i=0;i<notices.size();i++) {
				Notice notice=notices.get(i);
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				String formatTime=df.format(notice.getTime());
				notices.get(i).setFormatTime(formatTime);
			}
		}
		result.put("results", notices);
		return result;
	}

	@Override
	public String publishDynamic(Dynamic dynamic) {
		// TODO Auto-generated method stub
		userDAO.save(dynamic);
		return "success";
	}

	@Override
	public String publishComment(Comment comment) {
		// TODO Auto-generated method stub
		userDAO.save(comment);
		return "success";
	}

	@Override
	public String addPraise(Dynamic dynamic) {
		// TODO Auto-generated method stub
		String hql = "from Dynamic where dynamicID="+dynamic.getDynamicID();
		dynamic=userDAO.findOne(hql);
		int amount=dynamic.getLikeAmount()+1;
		dynamic.setLikeAmount(amount);
		userDAO.update(dynamic);
		return "success";
	}

	@Override
	public String publishNotice(Notice notice) {
		// TODO Auto-generated method stub
		userDAO.save(notice);
		return "success";
	}

	@Override
	public String updateClass(User user) {
		// TODO Auto-generated method stub
		String hql = "from User where userID="+user.getUserID();
		Long code=user.getClassID();
		user=userDAO.findOne(hql);
		user.setClassID(code);
		userDAO.update(user);
		return "success";
	}

	@Override
	public Map<String, Object> getAllUsers(String classID) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		String hql = "from User where classID=" +classID+" and characters in('parent','teacher')";
		List<User> users=userDAO.findList(hql);
		result.put("results", users);
		return result;
	}

	@Override
	public String delUser(User user) {
		// TODO Auto-generated method stub
		userDAO.remove(user);
		return "success";
	}

	@Override
	public String delDynamic(Dynamic dynamic) {
		// TODO Auto-generated method stub
		userDAO.remove(dynamic);
		return "success";
	}
}