package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.response.DataResponse;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.ListData;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import com.bezkoder.springjwt.service.IUserService;
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
	IUserService userService;

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
		String newPassword=params.get("new-password");
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
			return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
		}


	}

	/**
	 *
	 *更新用户信息
	 */

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

	/**
	 *
	 *获取用户信息
	 */
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
		List<User>list=userRepository.findAll();
		if(!list.isEmpty()){
			return ResponseEntity.ok(new DataResponse(0,new ListData(list)));
		}else{
			return ResponseEntity.ok(new MessageResponse(1, "查询用户列表失败!"));
		}
	}





}
