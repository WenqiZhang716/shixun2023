package org.ejavaexample.auth.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.Service;
import org.ejavaexample.auth.entity.Role;
import org.ejavaexample.auth.entity.ERole;
import org.ejavaexample.auth.entity.User;
import org.ejavaexample.auth.repository.RoleRepository;
import org.ejavaexample.auth.repository.UserRepository;

@Service
public class UserServiceImpl {
	
    @Autowired
    UserRepository userRepository;
    
    @Autowired 
    RoleRepository roleRepository;

    //@Autowired
    //@Qualifier("encoder")
    BCryptPasswordEncoder encoder;
	
	   /* PRODUCT */
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    
    public User getUser(String username, String password){
    	encoder = new BCryptPasswordEncoder();
    	//String psw_encode = encoder.encode(password);
    	User usr = userRepository.findByUsername(username).get();
    	if (usr != null) {
    		boolean match = encoder.matches(password, usr.getPassword());
    		if (match) return usr;
    	}
    	
        return null;
    }
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

    public int isChecked(long userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            User user=optional.get();
            if(user.getIsCheck()==1){
                return 1;
            }else{
                return 0;
            }
        }
        return 0;

    }
    public User save(User user){
        return userRepository.save(user);
    }

    public Boolean userExist(String username) {
    	return userRepository.existsByUsername(username);
    }
    
    public Optional<Role> roleExist(ERole r) {
    	return roleRepository.findByName(r);
    }
}
