package uz.agro.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "username не должен быть пустым")
    private String username;
    @NotBlank(message = "email не должен быть пустым")
    private String email;
    @NotBlank(message = "пароль не должен быть пустым")
    private String password;
    @NotBlank(message = "Имя не должен быть пустым")
    private String firstName;
    @NotBlank(message = "Фамилия не должен быть пустым")
    private String lastName;

    @Override
    public String toString() {
        return "UserRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
