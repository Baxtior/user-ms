package uz.agro.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "email не должен быть пустым")
    private String email;
    @NotBlank(message = "пароль не должен быть пустым")
    private String password;
}
