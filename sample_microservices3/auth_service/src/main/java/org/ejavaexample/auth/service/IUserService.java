package org.ejavaexample.auth.service;

import org.ejavaexample.auth.entity.ERole;
import org.ejavaexample.auth.entity.Role;
import org.ejavaexample.auth.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public List<User> getUsers();
    public User getUser(String username, String password);
    public int isUser(String username);

    public int isChecked(long userId);
    public User save(User user);

    public Boolean userExist(String username);
    public Optional<Role> roleExist(ERole r);
}
