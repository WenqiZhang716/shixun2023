package org.ejavaexample.auth.controllers;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.ejavaexample.auth.payload.response.DataResponse;
import org.ejavaexample.auth.payload.response.ListData;
import org.ejavaexample.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.ejavaexample.auth.entity.ERole;
import org.ejavaexample.auth.entity.Role;
import org.ejavaexample.auth.entity.User;
import org.ejavaexample.auth.payload.request.LoginRequest;
import org.ejavaexample.auth.payload.request.SignupRequest;
import org.ejavaexample.auth.payload.request.ValidJwtRequest;
import org.ejavaexample.auth.payload.response.JwtResponse;
import org.ejavaexample.auth.payload.response.MessageResponse;
import org.ejavaexample.auth.repository.RoleRepository;
//import org.ejavaexample.auth.repository.UsrRepository;
import org.ejavaexample.auth.service.impl.UserServiceImpl;
import org.ejavaexample.auth.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  //@Autowired
  RoleRepository roleRepository;

  @Autowired
  JwtUtils jwtUtils;
  
  @Autowired
  UserServiceImpl userService;
  BCryptPasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;


  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	try {
		User user = userService.getUser(loginRequest.getUsername(), loginRequest.getPassword());
		if (user != null) {
			String jwt = jwtUtils.generateJwtToken(user.getUsername());
		    List<String> roles = user.getRoles().stream()
		            .map(item -> item.getName().toString())
		            .collect(Collectors.toList());
	
		    return ResponseEntity.ok(new JwtResponse(jwt, 
	                user.getId(), 
	                user.getUsername(),
	                roles));	
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(1,"Error: 用户不存在!"));

	}
	catch(Exception e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(1,"Error: !"));
	}
	
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      if(!signUpRequest.getUsername().matches("[1][34578][0-9]{9}")){
          return ResponseEntity.ok(new MessageResponse(1,"错误: 请输入正确手机号!"));
      }
    if (userService.userExist(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse(1,"手机号已注册!"));
    }
    // Create new user's account
	encoder = new BCryptPasswordEncoder();
    User user = new User(signUpRequest.getUsername(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = null;
    Set<Role> roles = new HashSet<>();
//根据最新版后端，去掉运输员的角色，只搞用户
    if (strRoles == null) {
      Role userRole = userService.roleExist(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = userService.roleExist(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = userService.roleExist(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = userService.roleExist(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
    user.setRoles(roles);
    userService.save(user);

    return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
  }
    @PostMapping("/get-info")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>getInfo(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        String username=jwtUtils.getUserNameFromJwtToken(token);
        Optional<User> IsUser=userRepository.findByUsername(username);
        HashMap<String,Object>map=new HashMap<String,Object>();
        if(IsUser.isPresent()){
            User user=IsUser.get();
            map.put("id",user.getId());
            map.put("name",user.getUsername());
            map.put("nickname",user.getNickName());
            map.put("age",user.getAge());
            map.put("sex",user.getSex());
            map.put("address",user.getDefaultAddress());
            map.put("isCheck",user.getIsCheck());
            map.put("realName",user.getRealName());

            return ResponseEntity.ok(new DataResponse(0,map));
        }else {
            return ResponseEntity.ok(new MessageResponse(1, "用户不存在!"));
        }

    }
  @PostMapping("/isjwtok")
  public ResponseEntity<?> validatejwt(@Valid @RequestBody ValidJwtRequest jwtRequest, @RequestHeader("Authorization") String tokenBearer) {

	//try {
		boolean isok = jwtUtils.validateJwtToken(jwtRequest.getJwt());
        String token=tokenBearer.substring(7, tokenBearer.length());
     String user_id=jwtUtils.getUserNameFromJwtToken(token);
		if (isok)
			return ResponseEntity.ok(new DataResponse(0,user_id));
		 
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(1,"False"));

  }

  @GetMapping("/getUserId/{token}")
    public Long userId(@PathVariable("token") String token){
      Long user_id=jwtUtils.getUserIdByJwtToken(token);
      return user_id;
  }

    @GetMapping("/getUserName/{token}")
    public String userName(@PathVariable("token") String token){
        String userName=jwtUtils.getUserNameFromJwtToken(token);
        return userName;
    }

    @PostMapping("/change-pw")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String tokenBearer){
        //String token=params.get("token");
//        try{
            String token;
            if(tokenBearer.startsWith("Bearer")){
                token=tokenBearer.substring(7, tokenBearer.length());
            }else{
                token=tokenBearer;
            }
            System.out.println(tokenBearer);
            String userName=jwtUtils.getUserNameFromJwtToken(token);
            String password=params.get("password");
            String newPassword=params.get("new-password");
            User flag=userService.getUser(userName,password);
            if(flag==null){
                return ResponseEntity.ok(new MessageResponse(1,"用户名或密码不正确,无法修改密码!"));
            }else{
                encoder = new BCryptPasswordEncoder();
                userRepository.updatePassword(userName,encoder.encode(newPassword));
                return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
            }
//        }catch (Exception e){
//            return ResponseEntity.ok(new MessageResponse(1,"修改失败，出现错误!"));
//
//        }

    }

    @GetMapping("/isCheck/{id}")
    @PreAuthorize("hasRole('USER') ")
    public int userCheck(@PathVariable("id") Long id) {
        Optional<User> IsUser = userRepository.findById(id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (IsUser.isPresent()) {
            if (IsUser.get().getIsCheck() == 0) {
                return 0;
            } else {
                return 1;
            }
        }
        return 2;
    }



    @PostMapping("/update-info")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>updateInfo(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token;
        if(tokenBearer.startsWith("Bearer")){
            token=tokenBearer.substring(7, tokenBearer.length());
        }else{
            token=tokenBearer;
        }

        String nickName=params.get("nickName");
        String realName=params.get("realName");
        int age= Integer.parseInt(params.get("age"));
        int sex=Integer.parseInt(params.get("sex"));
        String address=params.get("defaultAddress");
        String username=jwtUtils.getUserNameFromJwtToken(token);
        Optional<User> user=userRepository.findByUsername(username);
        if(user.isPresent()){
            if(userService.isUser(username)==0){
                age=age==-1?user.get().getAge():age;
                nickName= nickName.equals("") ? user.get().getNickName() : nickName;
                realName= realName.equals("") ? user.get().getRealName() : realName;
                sex= sex == -1 ? user.get().getSex() : sex;
                address= address.equals("") ?user.get().getDefaultAddress():address;
                int flag=userRepository.updateUserInfo(username,realName,nickName,age,sex,address);
                return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
            }else{
                return ResponseEntity.ok(new MessageResponse(1, "用户不存在!"));
            }

        }else {
            return ResponseEntity.ok(new MessageResponse(1, "用户不存在!"));
        }

    }


    @PostMapping("/identify")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>getCheck(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String realName=params.get("realName");
        String idNum=params.get("id_number");
        String token=tokenBearer.substring(7, tokenBearer.length());
        String username=jwtUtils.getUserNameFromJwtToken(token);
        int flag=userRepository.checkIt(username,realName);
        if(flag==1){
            return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "认证失败!"));
        }


    }



    @PostMapping("/delete")
    @PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
    public ResponseEntity<?>delete(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        String username=jwtUtils.getUserNameFromJwtToken(token);
        int flag=userRepository.deleteByUsername(username);
        if(flag==1){
            return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "删除失败!"));
        }
    }

    @PostMapping("/get-info-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>getInfoAll(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        String username=jwtUtils.getUserNameFromJwtToken(token);
        List<User> list=userRepository.findAll();
        if(!list.isEmpty()){
            return ResponseEntity.ok(new DataResponse(0,new ListData(list)));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "查询用户列表失败!"));
        }
    }




}
