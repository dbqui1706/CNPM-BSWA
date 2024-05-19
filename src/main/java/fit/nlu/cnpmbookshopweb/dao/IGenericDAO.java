package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.model.AbstractModel;

public interface IGenericDAO<T extends AbstractModel> {
    Long insert(T t);
    void update(T t);
    void delete(T t);
    T getByID(Long id);
}
