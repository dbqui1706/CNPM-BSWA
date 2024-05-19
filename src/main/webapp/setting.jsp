<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">

<head>
    <jsp:include page="/common/meta.jsp"/>
    <title>Thiết đặt</title>
</head>

<body>
<style>
    .error {
        border-color: red;
    }
    .error-message {
        color: red;
        font-size: 0.875em;
    }
</style>
<jsp:include page="/common/client/header.jsp"/>

<section class="section-pagetop bg-light">
    <div class="container">
        <h2 class="title-page">Thiết đặt</h2>
    </div> <!-- container.// -->
</section> <!-- section-pagetop.// -->

<section class="section-content padding-y">
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${empty sessionScope.currentUser}">
                    <p>
                        Vui lòng <a href="${pageContext.request.contextPath}/signin">đăng nhập</a> để sử dụng chức năng thiết đặt.
                    </p>
                </c:when>
                <c:otherwise>
                    <jsp:include page="/common/client/navPanel.jsp">
                        <jsp:param name="active" value="SETTING"/>
                    </jsp:include>

                    <main class="col-md-9">
                        <article class="card">
                            <div class="card-body">
                                <c:if test="${not empty requestScope.successMessage}">
                                    <div class="alert alert-success" role="alert">${requestScope.successMessage}</div>
                                </c:if>
                                <c:if test="${not empty requestScope.errorMessage}">
                                    <div class="alert alert-danger" role="alert">${requestScope.errorMessage}</div>
                                </c:if>
                                <div class="col-lg-6">
                                    <form action="${pageContext.request.contextPath}/setting" method="post">
                                        <div class="mb-3">
                                            <label for="inputUsername" class="form-label">Tên đăng nhập</label>
                                            <input type="text"
                                                   class="form-control"
                                                   id="inputUsername"
                                                   name="username"
                                                   value="${sessionScope.currentUser.username}">
                                        </div>
                                        <div class="mb-3">
                                            <label for="inputFullname" class="form-label">Họ và tên</label>
                                            <input type="text"
                                                   class="form-control"
                                                   id="inputFullname"
                                                   name="fullname"
                                                   value="${sessionScope.currentUser.fullName}">
                                        </div>
                                        <div class="mb-3">
                                            <label for="inputEmail" class="form-label">Email</label>
                                            <input type="email"
                                                   class="form-control"
                                                   id="inputEmail"
                                                   name="email"
                                                   value="${sessionScope.currentUser.email}">
                                        </div>
                                        <div class="mb-3">
                                            <label for="inputPhoneNumber" class="form-label">Số điện thoại</label>
                                            <input type="text"
                                                   class="form-control"
                                                   id="inputPhoneNumber"
                                                   name="phoneNumber"
                                                   value="${sessionScope.currentUser.phoneNumber}">
                                        </div>
                                        <div class="mb-3">
                                            <div class="form-check d-inline-block me-4">
                                                <input class="form-check-input"
                                                       type="radio"
                                                       name="gender"
                                                       id="radioGender1"
                                                       value="0"
                                                    ${sessionScope.currentUser.gender == 0 ? 'checked' : ''}>
                                                <label class="form-check-label" for="radioGender1">Nam</label>
                                            </div>
                                            <div class="form-check d-inline-block">
                                                <input class="form-check-input"
                                                       type="radio"
                                                       name="gender"
                                                       id="radioGender2"
                                                       value="1"
                                                    ${sessionScope.currentUser.gender == 1 ? 'checked' : ''}>
                                                <label class="form-check-label" for="radioGender2">Nữ</label>
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="inputAddress" class="form-label">Địa chỉ</label>
                                            <input type="text"
                                                   class="form-control"
                                                   id="inputAddress"
                                                   name="address"
                                                   value="${sessionScope.currentUser.address}">
                                        </div>
                                        <button type="submit" class="btn btn-primary w-100">Cập nhật thông tin mới</button>
                                    </form>
                                </div>
                            </div> <!-- card-body.// -->
                        </article>
                    </main> <!-- col.// -->
                </c:otherwise>
            </c:choose>
        </div> <!-- row.// -->
    </div> <!-- container.// -->
</section> <!-- section-content.// -->

<jsp:include page="/common/client/footer.jsp"/>

<script>
    function clearError(input) {
        var errorMessage = input.parentNode.querySelector('.error-message');
        if (errorMessage) {
            errorMessage.parentNode.removeChild(errorMessage);
        }
        input.classList.remove('error');
    }

    function showError(input, message) {
        clearError(input);
        var error = document.createElement('div');
        error.className = 'error-message';
        error.innerText = message;
        input.classList.add('error');
        input.parentNode.appendChild(error);
    }

    function validateFullName() {
        var fullName = document.getElementById("inputFullname");
        var fullNameRegex = /^[a-zA-Z\s]+$/;
        if (!fullNameRegex.test(fullName.value)) {
            showError(fullName, "Họ và tên không được chứa ký tự đặc biệt.");
        } else {
            clearError(fullName);
        }
    }

    function validateEmail() {
        var email = document.getElementById("inputEmail");
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email.value)) {
            showError(email, "Email không hợp lệ.");
        } else {
            clearError(email);
        }
    }

    function validatePhoneNumber() {
        var phoneNumber = document.getElementById("inputPhoneNumber");
        var phoneNumberRegex = /^[0-9]+$/;
        if (!phoneNumberRegex.test(phoneNumber.value)) {
            showError(phoneNumber, "Số điện thoại chỉ được chứa số.");
        } else {
            clearError(phoneNumber);
        }
    }

    function validateAddress() {
        var address = document.getElementById("inputAddress");
        if (address.value.trim() === "") {
            showError(address, "Địa chỉ không được để trống.");
        } else {
            clearError(address);
        }
    }

    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("inputFullname").addEventListener("input", validateFullName);
        document.getElementById("inputEmail").addEventListener("input", validateEmail);
        document.getElementById("inputPhoneNumber").addEventListener("input", validatePhoneNumber);
        document.getElementById("inputAddress").addEventListener("input", validateAddress);
    });
</script>




</body>

</html>
