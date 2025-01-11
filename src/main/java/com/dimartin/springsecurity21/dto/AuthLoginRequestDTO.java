package com.dimartin.springsecurity21.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(@NotBlank String username, 
		 						  @NotBlank String password) {

}
