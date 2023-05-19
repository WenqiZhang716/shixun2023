package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户模块业务层的实现类
 * @author zhangwq
 */
//将当前类的对象交给spring管理
@Service
public class UserServiceImpl implements IUserService {

    @Autowired(required = false)
    private UserRepository userRepository;


    @Override
    public int isUser(String username) {
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isPresent()) {
            User user=optional.get();
            Role role= user.getRoles().iterator().next();
            if (role.getName()== ERole.ROLE_USER){
                return 0;
            }else{
                return 1;
            }
        }
        return 0;
    }
}
