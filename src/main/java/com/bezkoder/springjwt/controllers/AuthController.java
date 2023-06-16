package com.bezkoder.springjwt.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bezkoder.springjwt.payload.response.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	/**
	 *
	 * 登录
	 */
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			//SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());
			//0用户，1运送员
			int type=loginRequest.getType();
			if(type==0&&roles.get(0).equals("ROLE_USER")||
					type==1&&roles.get(0).equals("ROLE_TRANSPORT")){
				return ResponseEntity.ok(new DataResponse(0,new JwtResponse(jwt,
						userDetails.getId(),
						userDetails.getUsername(),
						roles)));
			}else{
				return ResponseEntity.ok(new MessageResponse(1,"该用户身份与登陆类型不匹配！"));
			}


		} catch (BadCredentialsException ex) {
		//	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			return ResponseEntity.ok(new MessageResponse(1,"用户名或密码不存在！"));
		}
	}

	/**
	 *
	 * 注册
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if(!signUpRequest.getUsername().matches("[1][34578][0-9]{9}")){

			return ResponseEntity.ok(new MessageResponse(1,"错误: 请输入正确手机号!"));
		}
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.ok(new MessageResponse(1,"错误: 手机号已注册!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				encoder.encode(signUpRequest.getPassword()));

		//Set<String> strRoles = signUpRequest.getRoll();
		Set<Role> roles = new HashSet<>();
		//只有用户可以注册，运货员由公司发放账号
		int oneRole=1;
		if (oneRole == -1) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: the role roll is not found."));
			roles.add(userRole);
		} else {
			switch (oneRole) {
				case 1:
					Role transRole = roleRepository.findByName(ERole.ROLE_TRANSPORT)
							.orElseThrow(() -> new RuntimeException("Error: the Role adm is not found."));
					roles.add(transRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: the Role usr is not found."));
					roles.add(userRole);
			}

		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
	}

	@PostMapping("/test")
	public ResponseEntity<?> test() {
	    Map<String,Object>map=new HashMap<>();
	    map.put("aaaa","测试测试测试测试");

		return ResponseEntity.ok(new DataResponse(0,map));
	}
}