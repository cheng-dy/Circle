package circle.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO extends BaseHibernateDAO implements IUserDAO {
	private Session session;
	@Override
	public <T> T findOne(String hql) {
		// TODO Auto-generated method stub
		session=getSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			Query query = session.createQuery(hql);
			return (T) query.uniqueResult();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	@Override
	public <T> boolean save(T temp) {
		// TODO Auto-generated method stub
		session=getSession();
		Transaction tran = null;
		Session session = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			session.save(temp);
			tran.commit();
			return true;
		} catch (RuntimeException re) {
			if (tran != null)
				tran.rollback();
			re.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	@Override
	public List findList(String hql) {
		// TODO Auto-generated method stub
		session=getSession();
		Transaction tran=null;
		try{
			tran=session.getTransaction();
			tran.begin();
			Query query = session.createQuery(hql);
			return (List) query.list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	@Override
	public <T> boolean remove(T temp) {
		// TODO Auto-generated method stub
		session=getSession();
		Transaction tran=null;
		try{
			tran=session.getTransaction();
			tran.begin();
			session.delete(temp);
			tran.commit();
			return true;
		}catch(Exception e)
		{
			if(tran!=null)
				tran.rollback();
			e.printStackTrace();
			return false;
		}finally{
			session.close();
		}
	}
	@Override
	public <T> boolean update(T temp) {
		// TODO Auto-generated method stub
		session=getSession();
		Transaction tran=null;
		try{
			tran=session.getTransaction();
			tran.begin();
			session.update(temp);
			tran.commit();
			return true;
		}catch(Exception e)
		{
			if(tran!=null)
				tran.rollback();
			e.printStackTrace();
			return false;
		}finally{
			session.close();
		}
	}
	@Override
	public int findOnesql(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean remove(String hql) {
		// TODO Auto-generated method stub
		return false;
	}
}