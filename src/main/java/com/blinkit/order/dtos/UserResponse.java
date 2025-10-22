package com.blinkit.order.dtos;

import com.blinkit.order.models.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {
    Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
    private AddressDTO addressdto;
}