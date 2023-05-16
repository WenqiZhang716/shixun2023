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
			int type=loginRequest.getType();
			if(type==1&& roles.get(0).equals("ROLE_USER")) {
				return ResponseEntity.ok(new MessageResponse(1,"该用户不是管理员！请使用用户身份登录"));
			}
			if(type==0&&roles.get(0).equals("ROLE_ADMIN")){
				return ResponseEntity.ok(new MessageResponse(1,"该用户是管理员！请使用管理员身份登录"));
			}

			return ResponseEntity.ok(new DataResponse(0,new JwtResponse(jwt,
					userDetails.getId(),
					userDetails.getUsername(),
					userDetails.getEmail(),
					roles)));

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
        //暂时不启用邮箱，先自动生成一串
		String str="qjieu6uyafncgsxb4673hydscbcsd3434751945cfdcnn0i7jm5bxm97ijisaw4qqqpk57mxpklazaw";
		Random random1=new Random();
		//指定字符串长度，拼接字符并toString
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < 10; i++) {
			//获取指定长度的字符串中任意一个字符的索引值
			int number=random1.nextInt(str.length());
			//根据索引值获取对应的字符
			char charAt = str.charAt(number);
			sb.append(charAt);
		}
		String email = sb.toString()+"@qq.com";
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.ok(new MessageResponse(1,"错误: 用户名已注册!"));
		}

		if (userRepository.existsByEmail(email)) {
			return ResponseEntity.ok(new MessageResponse(1,"Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				email,
				encoder.encode(signUpRequest.getPassword()));

		//Set<String> strRoles = signUpRequest.getRoll();
		Set<Role> roles = new HashSet<>();
		int oneRole=signUpRequest.getRole();
		if (oneRole == -1) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: the role roll is not found."));
			System.out.println("ssss");
			roles.add(userRole);
		} else {
			switch (oneRole) {
				case 1:
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: the Role adm is not found."));
					roles.add(adminRole);

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
}