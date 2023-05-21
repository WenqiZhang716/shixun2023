package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Bill;
import com.bezkoder.springjwt.models.TransportStep;
import com.bezkoder.springjwt.payload.response.DataResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.service.ITransportStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwq
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/transport")
public class TransportController {

    @Autowired
    ITransportStepService transportStepService;

    @PostMapping("/path-planning")
    public ResponseEntity<?> planPath(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String tokenBearer){
//        String token=tokenBearer.substring(7, tokenBearer.length());
//        Long userId=jwtUtils.getUserIdByJwtToken(token);
//        int type=params.get("type").equals("")?-1: Integer.parseInt(params.get("type"));
//        try{
//            List<Bill> billList=billService.getBillByType(userId,type);
//            HashMap<String,Object> map=new HashMap<>();
//            map.put("bill-list",billList);
//            return ResponseEntity.ok(new DataResponse(0,map));
//
//        }catch(Exception e){
//            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
//        }
        return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody Map<String, String> params){
        try{
            String address=params.get("address");
            List<TransportStep>list=transportStepService.getStepIdByString(address);
            return ResponseEntity.ok(new DataResponse(0,list));
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }
    }

    @PostMapping("/get-status-list")
    @PreAuthorize("hasRole('TRANSPORT') ")
    public ResponseEntity<?> GetStatusList(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
//        String token=tokenBearer.substring(7, tokenBearer.length());
//        Long userId=jwtUtils.getUserIdByJwtToken(token);
//        int type=params.get("type").equals("")?-1: Integer.parseInt(params.get("type"));
//        try{
//            List<Bill> billList=billService.getBillByType(userId,type);
//            HashMap<String,Object> map=new HashMap<>();
//            map.put("bill-list",billList);
//            return ResponseEntity.ok(new DataResponse(0,map));
//
//        }catch(Exception e){
//            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
//        }
        return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
    }
    @PostMapping("/change-status")
    @PreAuthorize("hasRole('TRANSPORT') ")
    public ResponseEntity<?> changeStatus(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
//        String token=tokenBearer.substring(7, tokenBearer.length());
//        Long userId=jwtUtils.getUserIdByJwtToken(token);
//        int type=params.get("type").equals("")?-1: Integer.parseInt(params.get("type"));
//        try{
//            List<Bill> billList=billService.getBillByType(userId,type);
//            HashMap<String,Object> map=new HashMap<>();
//            map.put("bill-list",billList);
//            return ResponseEntity.ok(new DataResponse(0,map));
//
//        }catch(Exception e){
//            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
//        }
        return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
    }
    @PostMapping("/findByType")
    @PreAuthorize("hasRole('TRANSPORT') ")
    public ResponseEntity<?> findByType(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
//        String token=tokenBearer.substring(7, tokenBearer.length());
//        Long userId=jwtUtils.getUserIdByJwtToken(token);
//        int type=params.get("type").equals("")?-1: Integer.parseInt(params.get("type"));
//        try{
//            List<Bill> billList=billService.getBillByType(userId,type);
//            HashMap<String,Object> map=new HashMap<>();
//            map.put("bill-list",billList);
//            return ResponseEntity.ok(new DataResponse(0,map));
//
//        }catch(Exception e){
//            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
//        }
        return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
    }
}