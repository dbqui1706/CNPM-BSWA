package fit.nlu.cnpmbookshopweb.service;

import fit.nlu.cnpmbookshopweb.dao.UserDao;
import fit.nlu.cnpmbookshopweb.model.User;

public class UserService {
    private final UserDao userDao = new UserDao();
    private static final UserService instance = new UserService();
    public User getByID(Long id) {
        return  userDao.getByID(id);
    }


    public static UserService getInstance() {
        return instance;
    }


}
