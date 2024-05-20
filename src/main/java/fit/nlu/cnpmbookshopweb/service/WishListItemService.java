package fit.nlu.cnpmbookshopweb.service;

import fit.nlu.cnpmbookshopweb.dao.WishListItemDAO;
import fit.nlu.cnpmbookshopweb.dto.WishlistItemDto;
import fit.nlu.cnpmbookshopweb.model.TWishlistItem;
import fit.nlu.cnpmbookshopweb.model.WishListItem;

import java.util.Optional;

public class WishListItemService {
    private TWishlistItem tWishlistItem = new TWishlistItem();
    private WishListItemDAO wishlistItemDao = new WishListItemDAO();
    public int countByUserIdAndProductId(Long userId) {
        return wishlistItemDao.countByUserIdAndProductId(userId);
    }

    public Optional<WishlistItemDto> insert(WishlistItemDto wishlistItemDto) {
        Long id = wishlistItemDao.save(tWishlistItem.toEntity(wishlistItemDto));
        return getById(id);
    }
    public Optional<WishlistItemDto> getById(Long id) {
        Optional<WishListItem> wishListItem = wishlistItemDao.getById(id);
        if (wishListItem.isPresent()) {
            WishlistItemDto wishlistItemDto = tWishlistItem.toDto(wishListItem.get());
//            wishlistItemDto.setUser(UserService.getInstance()
//                    .getById(wishListItem.get().getUserId()).get());
//            wishlistItemDto.setProduct(ProductService.getInstance()
//                    .getById(wishListItem.get().getProductId()).get());
            return Optional.of(wishlistItemDto);
        }
        return Optional.empty();
    }
}
