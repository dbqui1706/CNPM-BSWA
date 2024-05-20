package fit.nlu.cnpmbookshopweb.model;


import fit.nlu.cnpmbookshopweb.dao._interface.ITransfer;
import fit.nlu.cnpmbookshopweb.dto.WishlistItemDto;
import fit.nlu.cnpmbookshopweb.model.WishListItem;

public class TWishlistItem implements ITransfer<WishlistItemDto, WishListItem> {
    @Override
    public WishlistItemDto toDto(WishListItem wishListItem) {
        WishlistItemDto wishlistItemDto = new WishlistItemDto();
        wishlistItemDto.setId(wishListItem.getId());
        wishlistItemDto.setCreatedAt(wishListItem.getCreatedAt());
        return wishlistItemDto;
    }

    @Override
    public WishListItem toEntity(WishlistItemDto wishlistItemDto) {
        WishListItem wishListItem = new WishListItem();
        wishListItem.setId(wishlistItemDto.getId());
//        wishListItem.setUserId(wishlistItemDto.getUser().getId());
//        wishListItem.setProductId(wishlistItemDto.getProduct().getId());
        wishListItem.setCreatedAt(wishlistItemDto.getCreatedAt());
        return wishListItem;
    }
}
