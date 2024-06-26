package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.model.User;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;
import org.jdbi.v3.core.Jdbi;

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
// phương thức xử lý câu query để lấy id người dùng
    @Override
    public User getByID(Long id) {
        return DatabaseConnector.getJdbi().withHandle(handle -> {
            String sql = "SELECT * FROM USER WHERE id=:id";
            return handle.createQuery(sql).bind("id", id)
                    .mapToBean(User.class)
                    .one();
        });
    }
}