package fit.nlu.cnpmbookshopweb.service;

import fit.nlu.cnpmbookshopweb.dao.UserDao;
import fit.nlu.cnpmbookshopweb.model.Product;
import fit.nlu.cnpmbookshopweb.model.User;

public class UserService {
    private final UserDao userDao = new UserDao();
// lấy ra id người dùng
    public User getByID(Long id) {
        return userDao.getByID(id);
    }
    private static final UserService instance = new UserService();


    public static UserService getInstance() {
        return instance;
    }

}