package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.model.User;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;

public class UserDao extends AbstractDAO<User> {
    @Override
    public Long insert(User user) {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User getByID(Long id) {
        return DatabaseConnector.getJdbi().withHandle(handle -> {
            String sql = "SELECT * FROM user_ WHERE id=:id";
            return handle.createQuery(sql).bind("id", id)
                    .mapToBean(User.class)
                    .one();
        });
    }
}
