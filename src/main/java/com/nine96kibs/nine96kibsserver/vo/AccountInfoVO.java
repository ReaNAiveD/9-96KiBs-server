package com.nine96kibs.nine96kibsserver.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AccountInfoVO {

    private Integer id;

    private String username;

    private String password;
}
