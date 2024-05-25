package fit.nlu.cnpmbookshopweb.model;

import fit.nlu.cnpmbookshopweb.dto.UserDto;
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

    public UserDto get() {
        return new UserDto(
                this.getId(),
                this.getUsername(),
                this.getPassword(),
                this.getFullName(),
                this.getEmail(),
                this.getPhoneNumber(),
                this.getGender(),
                this.getAddress(),
                this.getRole()
        );
    }
}
