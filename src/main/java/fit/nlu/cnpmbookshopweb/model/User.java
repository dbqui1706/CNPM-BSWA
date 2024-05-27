package fit.nlu.cnpmbookshopweb.model;

import lombok.*;

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
}
