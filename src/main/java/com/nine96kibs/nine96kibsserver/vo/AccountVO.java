package com.nine96kibs.nine96kibsserver.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountVO implements Serializable {

    private String username;

    private String password;

}
