package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.dao._interface.IProductDAO;
import fit.nlu.cnpmbookshopweb.model.Product;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;

public class ProductDao extends AbstractDAO<Product> implements IProductDAO {
    @Override
    public Long insert(Product product) {
        return null;
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public Product getByID(Long id) {
        return DatabaseConnector.getJdbi().withHandle(handle -> {
            String sql = "SELECT * FROM product WHERE id=:id";
            return handle.createQuery(sql)
                    .bind("id", id)
                    .mapToBean(Product.class)
                    .one();
        });
    }
}