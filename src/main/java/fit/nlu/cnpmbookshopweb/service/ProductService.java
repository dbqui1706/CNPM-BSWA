package fit.nlu.cnpmbookshopweb.service;

import fit.nlu.cnpmbookshopweb.dao.ProductDao;
import fit.nlu.cnpmbookshopweb.model.Product;

public class ProductService {
    private final ProductDao productDao = new ProductDao();

    public Product getByID(Long id) {
        Product product = productDao.getByID(id);
        product.setId(id);
        return product;
    }
}
