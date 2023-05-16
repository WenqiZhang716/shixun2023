//package com.example.manager.service.impl;
//
//import com.example.manager.entity.User;
//import com.example.manager.repository.UserRepository;
//import com.example.manager.service.IUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * 用户模块业务层的实现类
// * @author zhangwq
// */
////将当前类的对象交给spring管理
//@Service
//public class UserServiceImpl implements IUserService {
//
//    @Autowired(required = false)
//    private UserRepository userRepository;
//
//    @Override
//    public int saveUser(String name, String password,int role_id) {
//        User user=new User();
//        user.setName(name);
//        user.setPassword(password);
//        user.setRoleId(role_id);
//
//        List<User> list =userRepository.findByName(name);
//        if(list!=null&& !list.isEmpty()){
//            return 0;
//        }else{
//            userRepository.save(user);
//            return 1;
//        }
//    }
//
//    @Override
//    public User findUserByName(String name) {
//        List<User> list =userRepository.findByName(name);
//        if(list!=null&& !list.isEmpty()){
//            return list.get(0);
//        }else{
//            return null;
//        }
//    }
//
//    @Override
//    public List<User> getUserList() {
//        List<User> list = userRepository.findAll();
//        System.out.println(list);
//        return list;
//    }
//
//    @Override
//    public int deleteUserByName(String name) {
//        int a=userRepository.deleteByName(name);
//        return a;
//    }
//
//    @Override
//    public int changePW(String name, String newPW) {
//        int res=userRepository.updateName(name,newPW);
//        return res;
//    }
//}
