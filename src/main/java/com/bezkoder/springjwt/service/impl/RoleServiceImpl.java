//package com.bezkoder.springjwt.service.impl;
//
//import com.example.manager.entity.Role;
//import com.example.manager.entity.User;
//import com.example.manager.repository.RoleRepository;
//import com.example.manager.service.IRoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * @author zhangwq
// */
//@Service
//public class RoleServiceImpl implements IRoleService {
//    @Autowired(required = false)
//    private RoleRepository roleRepository;
//
//    @Override
//    public int saveRole(String position, String introduce) {
//        Role role =new Role();
//        role.setPosition(position);
//        role.setIntroduce(introduce);
//        List<Role> list =roleRepository.findByPosition(position);
//        if(list!=null&& !list.isEmpty()){
//            return 0;
//        }else{
//            roleRepository.save(role);
//            return 1;
//        }
//    }
//
//    @Override
//    public Role findPosition(String position) {
//        List<Role> role=roleRepository.findByPosition(position);
//        return role.get(0);
//    }
//
//    @Override
//    public List<Role> getRoleList() {
//        List<Role> role=roleRepository.findAll();
//        return role;
//    }
//
//    @Override
//    public int deleteRoleByPosition(String position) {
//        int d=roleRepository.deleteByPosition(position);
//        return d;
//    }
//
//    @Override
//    public int changePosition(String name, String newName) {
//        int a=roleRepository.updatePosition(name,newName);
//        return a;
//    }
//}