package cn.ccnu.shop.user;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserDao extends HibernateDaoSupport {

	public void save(User user) {
		this.getHibernateTemplate().save(user);
	}

	@SuppressWarnings("unchecked")
	public User findUserByCode(String code) {
		List<User> userList = this.getHibernateTemplate().find(
				"from User where code=?", code);
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

	public void update(User user) {
		this.getHibernateTemplate().update(user);
	}

	@SuppressWarnings("unchecked")
	public User findActiveUserByUserNameAndPwd(User user) {
		List<User> userlist = this.getHibernateTemplate().find(
				"from User where username=? and password=? and state=1",
				user.getUsername(), user.getPassword());
		if (userlist.size() > 0) {
			return userlist.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {
		List<User> userlist = this.getHibernateTemplate().find(
				"from User where username=?", username);
		if (userlist.size() > 0) {
			return userlist.get(0);
		}
		return null;
	}

}
