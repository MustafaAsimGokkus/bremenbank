package com.bank.bremen.controller.response;

import com.bank.bremen.controller.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private UserDTO user;
}