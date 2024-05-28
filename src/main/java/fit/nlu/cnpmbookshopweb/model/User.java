package fit.nlu.cnpmbookshopweb.model;

import com.google.gson.Gson;

import lombok.*;

import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User extends AbstractModel{
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Integer gender;
    private String address;
    private String role;

    public User(Long id, String username, String password, String fullName, String email, String phoneNumber, Integer gender, String address, String role) {
        super.setId(id);
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.role = role;
    }
    @Override
    public String toString() {
        return new StringJoiner(",\n\t", User.class.getSimpleName() + "{\n", "\n}")
                .add("id:" + getId())
                .add("username:" + username)
                .add("password:" + password)
                .add("fullName:" + fullName)
                .add("email:" + email)
                .add("phoneNumber:" + phoneNumber)
                .add("gender:" + gender)
                .add("address:" + address)
                .add("createdAt:" + getCreatedAt())
                .add("updatedAt:" + getUpdatedAt())
                .toString();
    }
}
