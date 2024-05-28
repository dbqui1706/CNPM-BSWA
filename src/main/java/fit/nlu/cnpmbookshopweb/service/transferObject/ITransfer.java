package fit.nlu.cnpmbookshopweb.service.transferObject;

public interface ITransfer<D, E> {
    D toDto(E entity);

    E toEntity(D dto);
}
