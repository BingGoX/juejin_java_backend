package com.tsinghualei.juejin.model.param;

import jdk.jfr.DataAmount;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LoginRegister_Param {
    String phoneNumber;
    String smsCode;
}
