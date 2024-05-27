package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.model.User;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDAO<User> {
    @Override
    public Long insert(User user) {
        return null;
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



    @Override
    public boolean update(User user) {
        try {
            return DatabaseConnector.getJdbi().withHandle(handle -> {
                String sql = "UPDATE user SET username = :username, password = :password, fullname = :fullname, email = :email, phoneNumber = :phoneNumber, gender = :gender, address = :address, role = :role WHERE id = :id";
                System.out.print("id cua user: " + user.getId());
                int rowsAffected = handle.createUpdate(sql)
                        .bind("id", user.getId())
                        .bind("username", user.getUsername())
                        .bind("password", user.getPassword())
                        .bind("fullname", user.getFullName())
                        .bind("email", user.getEmail())
                        .bind("phoneNumber", user.getPhoneNumber())
                        .bind("gender", user.getGender())
                        .bind("address", user.getAddress())
                        .bind("role", user.getRole())
                        .execute();
                return rowsAffected > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    public ArrayList<String> getAllUsernames() {
        return DatabaseConnector.getJdbi().withHandle(handle -> {
            String sql = "SELECT username FROM user";
            List<String> usernames = handle.createQuery(sql)
                    .mapTo(String.class)
                    .list();
            return new ArrayList<>(usernames);
        });
    }
}
