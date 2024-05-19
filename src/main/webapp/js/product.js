import createToast, {toastComponent} from "./toast.js";
import {setTotalCartItemsQuantity} from "./header.js";

// STATIC DATA
const contextPathMetaTag = document.querySelector("meta[name='contextPath']");
const currentUserIdMetaTag = document.querySelector("meta[name='currentUserId']");
const productIdMetaTag = document.querySelector("meta[name='productId']");

const quantityInput = document.querySelector("#cart-item-quantity");
const productTitleElement = document.querySelector(".title");

// MESSAGES
const REQUIRED_SIGNIN_MESSAGE = "Vui lòng đăng nhập để thực hiện thao tác!";
const SUCCESS_ADD_CART_ITEM_MESSAGE = (quantity, productTitle) =>
    `Đã thêm thành công ${quantity} sản phẩm ${productTitle} vào giỏ hàng!`;
const FAILED_ADD_CART_ITEM_MESSAGE = "Đã có lỗi truy vấn!";
const SUCCESS_ADD_WISHLIST_ITEM_MESSAGE = (productTitle) =>
    `Đã thêm thành công sản phẩm ${productTitle} vào danh sách yêu thích!`;
const FAILED_ADD_WISHLIST_ITEM_MESSAGE = "Đã có lỗi truy vấn!";

// UTILS

// EVENT HANDLERS
function noneSigninEvent() {
    createToast(toastComponent(REQUIRED_SIGNIN_MESSAGE));
}


// MAIN
const buyNowBtn = document.querySelector("#buy-now");

if (currentUserIdMetaTag) {

} else {
    buyNowBtn.addEventListener("click", noneSigninEvent);
}
