package cn.ccnu.shop.user;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void regist(User user) {
		userDao.save(user);
	}

	public User findUserByCode(String code) {
		return userDao.findUserByCode(code);
	}

	public void update(User exitUser) {
		userDao.update(exitUser);
	}

	public User login(User user) {
		return userDao.findActiveUserByUserNameAndPwd(user);
	}

	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

}
