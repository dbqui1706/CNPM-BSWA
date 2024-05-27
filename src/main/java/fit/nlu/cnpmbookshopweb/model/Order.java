package fit.nlu.cnpmbookshopweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTest extends AbstractModel {
    public User user;
    private int status;
    private String nameReceiver;
    private String addressReceiver;
    private String emailReceiver;
    private String phoneReceiver;
    private Integer deliveryMethod;
    private Double deliveryPrice;
    private List<OrderItem> orderItems = new ArrayList<>();
    private Double totalPrice;
    @Override
    public String toString() {

        return new StringJoiner(",\n\t", OrderTest.class.getSimpleName() + "{\n", "\n}")
                .add("id:" + getId())
                .add("user:" + user)
                .add("nameReceiver:" + nameReceiver)
                .add("addressReceiver:" + addressReceiver)
                .add("emailReceiver:" + emailReceiver)
                .add("phoneReceiver:" + phoneReceiver)
                .add("status:" + status)
                .add("deliveryMethod:" + deliveryMethod)
                .add("deliveryPrice:" + deliveryPrice)
                .add("createdAt:" + getCreatedAt())
                .add("updatedAt:" + getUpdatedAt())
                .toString();
    }
}
