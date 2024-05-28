package fit.nlu.cnpmbookshopweb.model;

import fit.nlu.cnpmbookshopweb.utils.annotation.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractModel{
    private Long userIdOrdered;
    private int status;
    private String nameReceiver;
    private String addressReceiver;
    @NotNull(message = "Email is not null")
    @Email(regex = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String emailReceiver;
    private String phoneReceiver;
    private Integer deliveryMethod;
    private Double deliveryPrice;
    private List<OrderItem> orderItems;
    private Double totalPrice;
}