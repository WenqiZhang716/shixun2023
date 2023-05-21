package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Bill;
import com.bezkoder.springjwt.payload.response.DataResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwq
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    IBillService billService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> createBill(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        String userName=jwtUtils.getUserNameFromJwtToken(token);
        int manifestId= Integer.parseInt(params.get("manifest-id"));
        try{
           int flag= billService.createOne(userId,userName,manifestId);
           if(flag==1){
               return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
           }else{
               return ResponseEntity.ok(new MessageResponse(1, "账单创建失败，货单不存在或已取消！"));
           }
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "账单创建失败,请勿重复创建！"));
        }

    }


    @PostMapping("/get-list")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> GetList(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        int type=params.get("type").equals("")?-1: Integer.parseInt(params.get("type"));
        try{
            List<Bill> billList=billService.getBillByType(userId,type);
            HashMap<String,Object> map=new HashMap<>();
            map.put("bill-list",billList);
            return ResponseEntity.ok(new DataResponse(0,map));

        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }
    }

    @PostMapping("/pay-bill")//限定只有先付后到需要
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> payBill(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        int billId = Integer.parseInt(params.get("bill-id"));
        int orders = Integer.parseInt(params.get("card-order"));
        String password=params.get("password");

        try{
            int flag=billService.payOne(userId,billId,orders,password);
            if(flag==2){
                return ResponseEntity.ok(new MessageResponse(1, "支付密码不正确！"));
            }else if(flag==1){
                return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
            }else if(flag==3){
                return ResponseEntity.ok(new MessageResponse(1, "订单已支付或已取消！"));
            }else if(flag==4){
                return ResponseEntity.ok(new MessageResponse(1, "该订单为先到后付订单，请等待收货人支付！"));
            }else if(flag==5) {
                return ResponseEntity.ok(new MessageResponse(1, "余额不足，无法支付！"));
            }else{
                    return ResponseEntity.ok(new MessageResponse(1, "支付失败,账单不存在！"));
            }


        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "支付失败"));
        }

    }

    @PostMapping("/cancel")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> cancelBill(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        int bill= Integer.parseInt(params.get("id"));
        try{
            int flag=billService.cancelBill(bill);
            if(flag==1){
                return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
            }else{
                return ResponseEntity.ok(new MessageResponse(1, "取消失败"));
            }

        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "取消失败，订单不存在"));
        }

    }

}
