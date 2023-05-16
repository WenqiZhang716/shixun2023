package com.bezkoder.springjwt.service;

//常用命名规则，前面要加I

import com.bezkoder.springjwt.models.User;

import java.util.List;

public interface IUserService {
   
// 在IUserService里只定义方法，不用实现

    int saveUser(String name,String password,int role_id);
    User findUserByName(String name);
    List<User> getUserList();
    int deleteUserByName(String name);
    int changePW(String name, String newPW);



}
