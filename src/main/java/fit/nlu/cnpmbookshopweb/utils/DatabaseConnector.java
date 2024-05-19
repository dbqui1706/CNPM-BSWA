package fit.nlu.cnpmbookshopweb.utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnector {
    private static MysqlDataSource mysqlDataSource = new MysqlDataSource();
    private static Jdbi jdbi;

    static {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            mysqlDataSource.setURL(properties.getProperty("url"));
            mysqlDataSource.setUser(properties.getProperty("user"));
            mysqlDataSource.setPassword(properties.getProperty("password"));

            jdbi = Jdbi.create(mysqlDataSource);
            jdbi.useHandle(handle -> {
                int status = handle.createQuery("SELECT 1").mapTo(Integer.class).one();
                System.out.println("Successfully connection!");
            });
        } catch (IOException e) {
            System.out.println("Unsuccessfully connection!");
            throw new RuntimeException(e);
        }
    }
    public static Jdbi getJdbi(){
        return jdbi;
    }
}
