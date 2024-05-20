package fit.nlu.cnpmbookshopweb.dao._interface;


public interface ITransfer<D, E> {
    D toDto(E entity);

    E toEntity(D dto);
}
