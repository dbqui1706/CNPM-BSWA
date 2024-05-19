package fit.nlu.cnpmbookshopweb.service;

import fit.nlu.cnpmbookshopweb.dao.UserDao;
import fit.nlu.cnpmbookshopweb.model.Product;
import fit.nlu.cnpmbookshopweb.model.User;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User getByID(Long id) {
        return userDao.getByID(id);
    }

}
