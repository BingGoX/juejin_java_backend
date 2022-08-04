package com.tsinghualei.juejin.model.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserVo implements Serializable {
    private String userId;

    private String userName;

    private String headerUrl;

    private Long level;

    private Long followerCount;

    private Long likeCount;

    private Long badgeCount;

    private String sex;

    private String phoneNumber;

    private Date createTime;

    private Date modifyTime;

    private String password;

    private String position;

    private String company;

    private String blogAddr;

    private static final long serialVersionUID = 1L;

    private String uToken;
}