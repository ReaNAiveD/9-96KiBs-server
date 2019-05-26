package com.nine96kibs.nine96kibsserver.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoVO {

    private Integer id;

    private String username;

    private String password;
}
