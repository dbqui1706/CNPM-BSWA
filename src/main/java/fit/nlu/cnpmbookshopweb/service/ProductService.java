package fit.nlu.cnpmbookshopweb.service;

import fit.nlu.cnpmbookshopweb.dao.ProductDao;
import fit.nlu.cnpmbookshopweb.model.Product;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;
import org.jdbi.v3.core.Jdbi;

public class ProductService {
    private final ProductDao productDao = new ProductDao();
// lấy ra id sản phẩm
    public Product getByID(Long id) {
        return productDao.getByID(id);
    }
}