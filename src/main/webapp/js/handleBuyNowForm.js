$(document).ready(function () {
    const productIdMetaTag = document.querySelector("meta[name='productId']");

    const FormOrder = {
        $form: null,
        $inputs: null,
        $btnOrder: null,
        init: function () {
            this.$form = $('#form-order');
            this.$inputs = this.$form.find('input[type="text"], input[type=radio], input[type="email"], input[type="checkbox"], select');
            this.$btnOrder = $('#btn-order');

            this.bindEvents();
        },

        bindEvents: function () {
            this.$inputs.on('focus', (e) => this.validateUntilFocused(e));
            this.$btnOrder.on('click', (e) => this.handleSubmit(e));
            $('input[name="delivery"]').on('change', (e) => this.updateDeliveryFee(e));
        },

        validateUntilFocused: function (event) {
            let $focusedInput = $(event.target);
            let index = this.$inputs.index($focusedInput);
            this.$inputs.slice(0, index).each((i, input) => {
                this.validateInput($(input));
            });
        },

        validateInput: function ($input) {
            let isInvalid = $input.val().trim() === '' ||
                $input.val().trim() === 'Chọn Tỉnh/TP' ||
                $input.val().trim() === 'Chọn Quận/Huyện' ||
                $input.val().trim() === 'Chọn Phường/Xã' ||
                ($input.is(':checkbox') && !$input.prop('checked'));

            if (isInvalid) {
                this.markInvalid($input);
            } else {
                this.markValid($input);
            }
        },

        markInvalid: function ($input) {
            $input.addClass('is-invalid');
            $input.closest('.form-group').find('.invalid-feedback').show();
        },

        markValid: function ($input) {
            $input.removeClass('is-invalid');
            $input.closest('.form-group').find('.invalid-feedback').hide();
        },

        handleSubmit: function (event) {
            event.preventDefault();

            let isValid = true;
            this.$inputs.each((i, input) => {
                let $input = $(input);
                this.validateInput($input);
                if ($input.hasClass('is-invalid')) {
                    isValid = false;
                }
            });

            if (isValid) {
                this.postOrderForm(this.getFormDataAsJSON())
            } else {
                alert('Please fill in all required fields.');
            }
        },

        updateDeliveryFee: function (event) {
            let fee = $(event.target).attr('id') === 'express-order' ? 50000 : 15000;
            $('#delivery-fee').text(fee.toLocaleString('vi-VN') + ' đ');

            let temPrice = parseFloat($('#temp-price').text().replace(" đ", "").replace(".", ""));
            console.log("temp:", temPrice)
            let totalPrice = fee + temPrice;
            $('#total-product').html("<b>" + totalPrice.toLocaleString('vi-VN') + " đ</b>");
        },
        postOrderForm: (data) => {
            Swal.fire({
                title: "Bạn đã đã kiểm tra lại thông tin?",
                text: "Kiểm tra lại thông tin nếu đã chắc chắn thì hãy \"Xác nhận\"",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Xác nhận",
                cancelButtonText: "Hủy"
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        url: "/buy-now",
                        type: "POST",
                        contentType: "application/json",
                        data: data,
                        success: (result) => {
                            Swal.fire({
                                title: "Xác nhận đơn hàng!",
                                text: "Đơn hàng của bạn đã hoàn tất.",
                                icon: "success"
                            }).then(() => {
                                window.location.href = '/';
                            });
                        },
                        error: (error) => {
                            Swal.fire({
                                title: "Xác nhận đơn hàng!",
                                text: "Xin bạn thông cảm có thể là lỗi bên hệ thống nên đơn hàng chưa hoàn tất.",
                                icon: "error"
                            }).then(() => {
                                window.location.href = '/buy-now';
                            });
                        }
                    })

                }
            });
        },

        getFormDataAsJSON: function () {
            let formData = {};
            formData['productId'] = Number(productIdMetaTag.content);
            formData['quantity'] = Number($('.quantity').text());
            this.$inputs.each(function () {
                let $input = $(this);
                let name = $input.attr('name');
                if (name) {
                    if ($input.is(':radio')) {
                        if ($input.prop('checked')) {
                            formData[name] = $input.val();
                        }
                    } else if ($input.is('select')) {
                        formData[name] = $input.find('option:selected').text();
                    } else if ($input.is(':checkbox')) {
                        formData[name] = $input.prop('checked');
                    } else {
                        formData[name] = $input.val();
                    }
                }
            });
            console.log(formData)
            return JSON.stringify(formData);
        }
    };

    FormOrder.init();
});
