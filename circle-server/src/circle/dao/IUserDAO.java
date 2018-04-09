package circle.dao;

import java.util.List;

public interface IUserDAO {
	 public <T> T findOne(String hql);
	 public <T> boolean save(T temp);
	 public List  findList(String hql);
	 public <T> boolean remove(T temp);
	 public <T> boolean update(T temp);
	 public int findOnesql(String sql);
	 public boolean remove(String hql);
}