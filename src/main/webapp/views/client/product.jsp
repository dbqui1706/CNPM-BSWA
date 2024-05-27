<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">

<head>
    <jsp:include page="/common/meta.jsp"/>
    <title>${requestScope.product.name}</title>


</head>

<body>
<jsp:include page="/common/client/header.jsp"/>

<section class="section-pagetop-2 bg-light">
    <div class="container">
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item" aria-current="page">
                    <a href="#">Trang chủ</a>
                </li>
                <li class="breadcrumb-item" aria-current="page">
                    <a href="#">Trinh thám</a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">${requestScope.product.name}</li>
            </ol>
        </nav>
    </div> <!-- container.// -->
</section> <!-- section-pagetop-2.// -->

<section class="section-content padding-y">
    <div class="container">
        <div class="row">

            <aside class="col-md-5 mb-md-0 mb-4 d-flex justify-content-center align-items-center">
                <c:choose>
                    <c:when test="${empty requestScope.product.imageName}">
                        <img width="280"
                             height="280"
                             class="img-fluid"
                             src="${pageContext.request.contextPath}/img/280px.png"
                             alt="280px.png">
                    </c:when>
                    <c:otherwise>
                        <img width="280"
                             height="280"
                             class="img-fluid"
                             src="${pageContext.request.contextPath}/image/${requestScope.product.imageName}"
                             alt="${requestScope.product.imageName}">
                    </c:otherwise>
                </c:choose>
            </aside>

            <main class="col-md-7">

                <h2 class="title">${requestScope.product.name}</h2>

                <div class="rating-wrap my-3">
          <span class="rating-stars me-2">
            <c:forEach begin="1" end="5" step="1" var="i">
                <i class="bi bi-star-fill ${i <= 4 ? 'active' : ''}"></i>
            </c:forEach>
          </span>
                    <small class="label-rating text-muted me-2">2 đánh giá</small>
                    <small class="label-rating text-success">
                        <i class="bi bi-bag-check-fill"></i> 45 đã mua
                    </small>
                </div>

                <div class="mb-4">
                    <c:choose>
                        <c:when test="${requestScope.product.discount == 0}">
              <span class="price h4">
                <fmt:formatNumber pattern="#,##0" value="${requestScope.product.price}"/>₫
              </span>
                        </c:when>
                        <c:otherwise>
              <span class="price h4">
                <fmt:formatNumber
                        pattern="#,##0"
                        value="${requestScope.product.price * (100 - requestScope.product.discount) / 100}"/>₫
              </span>
                            <span class="ms-2 text-muted text-decoration-line-through">
                <fmt:formatNumber pattern="#,##0" value="${requestScope.product.price}"/>₫
              </span>
                            <span class="ms-2 badge bg-info">
                -<fmt:formatNumber pattern="#,##0" value="${requestScope.product.discount}"/>%
              </span>
                        </c:otherwise>
                    </c:choose>
                </div>

                <dl class="row mb-4">
                    <dt class="col-xl-4 col-sm-5 col-6">Tác giả</dt>
                    <dd class="col-xl-8 col-sm-7 col-6">${requestScope.product.author}</dd>

                    <dt class="col-xl-4 col-sm-5 col-6">Số trang</dt>
                    <dd class="col-xl-8 col-sm-7 col-6">${requestScope.product.pages}</dd>

                    <dt class="col-xl-4 col-sm-5 col-6">Nhà xuất bản</dt>
                    <dd class="col-xl-8 col-sm-7 col-6">${requestScope.product.publisher}</dd>

                    <dt class="col-xl-4 col-sm-5 col-6">Năm xuất bản</dt>
                    <dd class="col-xl-8 col-sm-7 col-6">${requestScope.product.yearPublishing}</dd>

                    <dt class="col-xl-4 col-sm-5 col-6">Số lượng</dt>
                    <dd class="col-xl-8 col-sm-7 col-6">
                        <input type="number" id="cart-item-quantity" class="form-control w-50" value="1" min="1"
                               max="${requestScope.product.quantity}" step="1"/>
                    </dd>
                </dl>

                <div>
                    <button type="button" class="btn btn-danger" id="add-wishlist-item"
                            title="Thêm vào danh sách yêu thích" ${requestScope.isWishlistItem == 1 ? 'disabled' : ''}>
                        <i class="bi bi-heart"></i>
                    </button>
                    <%--                    <a href="<c:url value="/checkout?productId=${param.id}"/>" class="btn btn-primary ms-2">Mua ngay</a>--%>
                    <button type="button" class="btn btn-primary ms-2" id="buy-now">Mua ngay</button>
                    <button type="button" class="btn btn-light ms-2" id="add-cart-item">Thêm vào giỏ hàng</button>
                </div>

            </main>

        </div>
    </div> <!-- container.// -->
</section> <!-- section-content.// -->

<section class="section-content mb-4">
    <div class="container">
        <div class="row">
            <div class="col">
                <h3 class="pb-2">Mô tả sản phẩm</h3>
                <div>${requestScope.product.description}</div>
            </div>
        </div>
    </div> <!-- container.// -->
</section> <!-- section-content.// -->

<jsp:include page="/common/client/footer.jsp"/>

<div class="toast-container position-fixed bottom-0 start-0 p-3"></div> <!-- toast-container.// -->
<!-- Custom Scripts -->
<script src="<c:url value="/js/toast.js"/>" type="text/javascript"></script>
<%--<script src="<c:url value="/js/product.js"/>" type="text/javascript">--%>
<script>
    // import createToast, {toastComponent} from "./toast.js";

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

    // EVENT HANDLERS
    function noneSigninEvent() {
        createToast(toastComponent(REQUIRED_SIGNIN_MESSAGE));
    }

    // MAIN
    const buyNowBtn = document.querySelector("#buy-now");
    console.log(buyNowBtn)
    if (currentUserIdMetaTag) {
        buyNowBtn.addEventListener("click", async function() {
            window.location.href = contextPathMetaTag.content + "/buy-now?productId="
                + productIdMetaTag.content + "&quantity=" + quantityInput.value;
        });
    } else {
        buyNowBtn.addEventListener("click", noneSigninEvent);
    }


</script>
</body>

</html>
