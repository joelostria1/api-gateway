package com.examen.ms.apigateway.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class ClientDto {

    private Long id;

    @NotBlank(message = "Nombre obligatorio")
    private String nombre;

    @NotBlank(message = "Apellido paterno obligatorio")
    private String apellidoPaterno;

    private String apellidoMaterno;

    @NotNull(message = "Fecha de nacimiento obligatoria")
    private LocalDate fechaNacimiento;

    @NotNull
    private Sexo sexo;

    @Email(message = "El correo debe tener un formato valido")
    private String correo;

    private String telefono;
}
