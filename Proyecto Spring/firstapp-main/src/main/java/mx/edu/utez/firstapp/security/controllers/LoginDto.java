package mx.edu.utez.firstapp.security.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class LoginDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;


}
