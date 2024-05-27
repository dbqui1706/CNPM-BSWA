// STATIC DATA
const contextPathMetaTag = document.querySelector("meta[name='contextPath']");
const currentUserIdMetaTag = document.querySelector("meta[name='currentUserId']");

// ROOTS/ELEMENTS
const totalCartItemsQuantityRootElement = document.querySelector("#total-cart-items-quantity");

// UTILS

// STATE
const state = {
  totalCartItemsQuantity: 0,
  setTotalCartItemsQuantity: (value) => {
    if (typeof value === "string") {
      state.totalCartItemsQuantity += Number(value);
    } else {
      state.totalCartItemsQuantity = value.cartItems
        .map((cartItem) => cartItem.quantity)
        .reduce((partialSum, cartItemQuantity) => partialSum + cartItemQuantity, 0);
    }
  },

}

// RENDER
function render() {
  totalCartItemsQuantityRootElement.innerHTML = state.totalCartItemsQuantity;
}

// MAIN
if (currentUserIdMetaTag) {
  // void state.initState();
}

export const setTotalCartItemsQuantity = state.setTotalCartItemsQuantity;
