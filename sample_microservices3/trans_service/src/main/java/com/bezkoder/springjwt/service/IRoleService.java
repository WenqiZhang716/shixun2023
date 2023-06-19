package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;

import java.util.List;

/**
 * @author zhangwq
 */
public interface IRoleService {
    int saveRole(String position,String introduce);
    Role findPosition(String position);
    List<Role> getRoleList();
    int deleteRoleByPosition(String position);
    int changePosition(String position, String newPosition);
}
