package fit.nlu.cnpmbookshopweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderBuyNow {
    private Long productId;
    private int quantity;
    private String address;
    private String city;
    private String delivery;
    private String district;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String ward;
}