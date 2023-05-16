package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.response.DataResponse;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.ListData;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhangwq
 */ //modify的不启用
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;


	/**
	 *
	 *修改密码
	 */
	@PostMapping("/change-pw")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
		//String token=params.get("token");
		String token;
		if(tokenBearer.startsWith("Bearer")){
			token=tokenBearer.substring(7, tokenBearer.length());
		}else{
			token=tokenBearer;
		}
		System.out.println(tokenBearer);
		//String token=tokenBearer.substring(7, tokenBearer.length());
		String userName=jwtUtils.getUserNameFromJwtToken(token);
		Long id=jwtUtils.getUserIdByJwtToken(token);
		String password=params.get("password");
		String newPassword=params.get("newPassword");
		System.out.println(userName);
		System.out.println(id);
        int flag=0;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userName, password));
		} catch (BadCredentialsException ex) {
			flag = 1;
		}
		if(flag==1){
			return ResponseEntity.ok(new MessageResponse(1,"密码不正确,无法修改密码!"));
		}else{
               userRepository.updatePassword(userName,encoder.encode(newPassword));
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(userName, newPassword));
//
//			String jwt = jwtUtils.generateJwtToken(authentication);
//			HashMap<String, Object>a=new HashMap<String, Object>();
//			a.put("token",jwt);
			return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
		}


	}

	/**
	 *
	 *更新用户信息
	 */

	@PostMapping("/update-info")
	@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
	public ResponseEntity<?>updateInfo(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
		String token;
		if(tokenBearer.startsWith("Bearer")){
			token=tokenBearer.substring(7, tokenBearer.length());
		}else{
			token=tokenBearer;
		}
		String realName=params.get("realName");
		String workId=params.get("work_id");
		String depart=params.get("department");
		String phone=params.get("phone");
		String username=jwtUtils.getUserNameFromJwtToken(token);
		Optional<User> user=userRepository.findByUsername(username);
		if(user.isPresent()){
			int flag=userRepository.updateUserInfo(username,realName,workId,depart,phone);
			return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
		}else {
			return ResponseEntity.ok(new MessageResponse(1, "用户不存在!"));
		}

	}

	/**
	 *
	 *获取用户信息
	 */
	@PostMapping("/get-info")
	@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
	public ResponseEntity<?>getInfo(@RequestHeader("Authorization") String tokenBearer){
		String token=tokenBearer.substring(7, tokenBearer.length());
		String username=jwtUtils.getUserNameFromJwtToken(token);
		Optional<User> IsUser=userRepository.findByUsername(username);
		HashMap<String,Object>map=new HashMap<String,Object>();
		if(IsUser.isPresent()){
			User user=IsUser.get();
			map.put("id",user.getId());
			map.put("userName",user.getUsername());
			map.put("realName",user.getRealName());
			map.put("work_id",user.getWorkId());
			map.put("department",user.getDepart());
			map.put("phone",user.getPhone());

			return ResponseEntity.ok(new DataResponse(0,map));
		}else {
			return ResponseEntity.ok(new MessageResponse(1, "用户不存在!"));
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
		List<User>list=userRepository.findAll();
		if(!list.isEmpty()){
			return ResponseEntity.ok(new DataResponse(0,new ListData(list)));
		}else{
			return ResponseEntity.ok(new MessageResponse(1, "查询用户列表失败!"));
		}
	}





}
