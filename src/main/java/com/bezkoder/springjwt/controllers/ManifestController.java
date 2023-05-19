package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.payload.request.CreateManiRequest;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.response.DataResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.ManifestRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.service.IManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mani")
public class ManifestController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ManifestRepository manifestRepository;

    @Autowired
    IManifestService manifestService;


    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createMani(@Valid @RequestBody CreateManiRequest createManiRequest,
                                        @RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        int goodsType=createManiRequest.getGoods_type();
        double weight=createManiRequest.getWeight();
        int transportType=createManiRequest.getTransport_type();
        String beginAddress=createManiRequest.getBegin_address();
        String endAddress=createManiRequest.getEnd_address();
        int payType=createManiRequest.getPay_type();
        String receiverName=createManiRequest.getReceiver_name();
        String receiverPhone = createManiRequest.getReceiver_phone();

        int flag=manifestService.createManifest(userId,goodsType,weight,transportType,
        beginAddress,endAddress,payType, receiverName,receiverPhone);
        if(flag==1){
            return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "创建失败!"));
        }
    }

    @PostMapping("/cancel")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>Cancel(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String tokenBearer){
        int id= Integer.parseInt(params.get("id"));
        String token=tokenBearer.substring(7, tokenBearer.length());
        String username=jwtUtils.getUserNameFromJwtToken(token);
        int flag=manifestService.cancelOne(id);
        if(flag==1){
            return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
        }else if(flag==2){
            return ResponseEntity.ok(new MessageResponse(1, "货单不存在!"));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "已发货，不能取消!"));
        }
    }

}
