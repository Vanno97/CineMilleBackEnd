package it.micael.vanini.cinemille.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
}