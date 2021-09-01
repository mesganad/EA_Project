package ars.cs.miu.edu.authentication;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginModel {
    private String username;
    private String password;
}
