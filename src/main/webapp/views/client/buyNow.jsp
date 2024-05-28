<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">

<head>
    <%--    <%@include file="/common/meta.jsp"%>--%>
    <jsp:include page="/common/meta.jsp"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
    <script src="<c:url value="/js/dghcvn.js"/>" type="module"></script>

    <title>Trang chủ</title>
</head>

<body>
<jsp:include page="/common/client/header.jsp"/>

<section class="section-content mb-5">
    <div class="container">
        <header class="section-heading py-3 d-flex justify-content-center">
            <h2 class="section-title">Thanh toán</h2>
        </header> <!-- section-heading.// -->

        <div class="row">
            <div class="col-md-8">
                <form id="form-order">
                    <div class="form-group mb-3">
                        <div class="d-flex flex-column mb-3">
                            <div class="mb-3">
                                <label class="subtitle">ĐỊA CHỈ GIAO HÀNG</label>
                            </div>
                            <div class="d-flex">
                                <label>*Các trường bắt buộc</label>
                            </div>
                        </div>
                        <div class="row gx-5">
                            <div class="col">
                                <input type="text" class="form-control" id="first-name" placeholder="Tên*" name="firstname">
                                <div class="invalid-feedback">Nhập tên của bạn</div>
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" id="last-name" placeholder="Họ*" name="lastname">
                                <div class="invalid-feedback">Nhập họ của bạn</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <label for="address"><b>Địa điểm</b>: Việt Nam</label>
                        <input type="text" class="form-control" name="address"  id="address" placeholder="Địa chỉ">
                        <div class="invalid-feedback">Nhập địa chỉ</div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Tỉnh/TP*:</label>
                                <select class="form-control" id="city" aria-label=".form-select-sm" name="city">
                                    <option>Chọn Tỉnh/TP</option>
                                </select>
                                <div class="invalid-feedback">Chọn thành phố</div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Quận/Huyện*:</label>
                                <select class="form-control" id="district" aria-label=".form-select-sm" name="district">
                                    <option>Chọn Quận/Huyện</option>
                                </select>
                                <div class="invalid-feedback">Chọn quận huyện</div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Phường/Xã*:</label>
                                <select class="form-control" id="ward" aria-label=".form-select-sm" name="ward">
                                    <option>Chọn Phường/Xã</option>
                                </select>
                                <div class="invalid-feedback">Chọn phường xã</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row g-3 align-items-center">
                            <div class="col-auto">
                                <label for="phone-number" class="col-form-label">Số liên lạc*:</label>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" id="phone-number" name="phoneNumber"
                                       placeholder="Số điện thoại liên hệ" aria-describedby="passwordHelpInline">
                                <div class="invalid-feedback">Nhập số liên lạc</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <label for="email">THÔNG TIN LIÊN HỆ</label>
                        <input type="email" class="form-control" id="email" placeholder="Địa chỉ email*" name="email">
                        <div class="invalid-feedback">Nhập địa chỉ email của bạn</div>
                    </div>
                    <div class="form-group mb-3">
                        <label class="subtitle">PHƯƠNG THỨC GIAO HÀNG</label>
                        <div class="d-flex flex-column justify-content-between">
                            <div class="form-check mt-2 ps-5">
                                <input class="form-check-input" type="radio" name="delivery" id="save-order" value="SAVE"
                                       checked>
                                <label class="form-check-label" for="save-order">
                                    Giao hàng tiêt kiệm
                                </label>
                            </div>
                            <div class="form-check mb-2 ps-5">
                                <input class="form-check-input" type="radio" name="delivery" id="express-order" value="EXPRESS">
                                <label class="form-check-label" for="express-order">
                                    Giao hàng nhanh
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <label class="subtitle">PHƯƠNG THỨC THANH TOÁN</label>
                        <div class="form-check card w-50">
                            <div class="card-body" style="padding: 0.5rem;">
                                <input class="form-check-input" type="radio" id="payment" checked>
                                <label class="form-check-label" for="payment">
                                        <span>
                                            Thanh toán khi giao hàng*
                                        </span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-check mb-3">
                        <input type="checkbox" class="form-check-input" id="checkbox-agree">
                        <label class="form-check-label" for="checkbox-agree">Tôi đồng ý với Điều khoản và Chính sách
                            của BookShop</label>
                    </div>
                    <div class="d-flex justify-content-center p-4 mb-3">
                        <div class="d-grid gap-2 col-6 mx-auto">
                            <button type="button" class="btn btn-primary" id="btn-order">ĐẶT HÀNG</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-4">
                <div class="d-flex card justify-content-between mb-4" style="background-color: #F0F0F0;">
                    <div class="p-3">
                        <div class="d-flex justify-content-center">
                            <h4 class="mb-2"><b>THÔNG TIN ĐƠN HÀNG</b></h4>
                        </div>
                        <hr>
                        <div class="d-flex justify-content-between">
                            <p>Tạm tính:</p>
                            <p id="temp-price">
                                <span class="price">
                                    <fmt:formatNumber pattern="#,##0" value="${tempPrice}"/>₫
                                </span>
                            </p>
                        </div>
                        <div class="d-flex justify-content-between">
                            <p>Vận chuyển xã xử lý:</p>
                            <p id="delivery-fee">15.000 đ</p>
                        </div>
                        <hr>
                        <div class="d-flex justify-content-between total">
                            <p><b>Tổng:</b></p>
                            <p id="total-product"><b>
                                <fmt:formatNumber pattern="#,##0" value="${tempPrice + 15.000}"/>₫
                            </b></p>
                        </div>
                    </div>
                </div>
                <div class="d-flex card justify-content-between" style="background-color: #F0F0F0;">
                    <div class="p-3">
                        <div class="d-flex justify-content-center">
                            <h4 class="mt-2"><b>THÔNG TIN CHI TIẾT SẢN PHẨM</b></h4>
                        </div>
                        <hr>
                        <c:forEach var="productDetail" items="${requestScope.productDetails}">
                            <div class="d-flex align-items-center mb-3" id="product-${productDetail.productID}">
                                <div class="flex-shrink-0">
                                    <c:choose>
                                        <c:when test="${empty productDetail.imageName}">
                                            <img width="120" height="160"
                                                 src="<c:url value="/img/50px.png"/>"
                                                 alt="50px.png" class="mr-3">
                                        </c:when>
                                        <c:otherwise>
                                            <img width="120" height="160"
                                                 src="<c:url value="/image/${productDetail.imageName}"/>"
                                                 alt="${productDetail.imageName}" class="mr-3">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="flex-grow-1 ms-4">
                                    <p><b>${productDetail.nameProduct}</b></p>
                                    <p><fmt:formatNumber pattern="#,##0" value="${productDetail.price}"/>đ |
                                        <b>Số lượng</b>:
                                        <span class="quantity">${productDetail.quantityBuy}</span>
                                    </p>
                                    <p><b>Tổng:</b>
                                        <fmt:formatNumber pattern="#,##0" value="${productDetail.total}"/>₫
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <!-- row.// -->
    </div> <!-- container.// -->
</section> <!-- section-content.// -->
<jsp:include page="/common/client/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<style>
    .input-wrapper.error .error-text {
        display: block; /* Hiển thị đoạn text cảnh báo khi có lỗi */
        position: absolute;
        bottom: -20px;
        left: 0;
    }
</style>
<script src="<c:url value="/js/handleBuyNowForm.js"/>" type="module"></script>
</body>
</html>