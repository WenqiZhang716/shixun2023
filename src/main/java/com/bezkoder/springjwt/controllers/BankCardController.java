package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.BankCard;
import com.bezkoder.springjwt.payload.response.DataResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.service.IBankCardService;
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
@RequestMapping("/bank-card")
public class BankCardController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    IBankCardService bankCardService;

    @PostMapping("/add-bankcard")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> AddBankCard(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String tokenBearer){
        String bankCard = params.get("bank_card");
        String bankName=params.get("bank_name");
        System.out.println(bankName+" :"+bankCard);
        if(bankCard.equals("")||bankName.equals("")){
            return ResponseEntity.ok(new MessageResponse(1, "银行卡号与开户行名称不能为空!"));
        }
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        try{
            int flag=bankCardService.addOne(userId,bankCard,bankName);
            if(flag==1){
                return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
            }else{
                return ResponseEntity.ok(new MessageResponse(1, "添加失败,请先实名认证!"));
            }
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "该银行卡号已添加"));
        }

    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> delete(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String tokenBearer){
       int order = Integer.parseInt(params.get("order"));
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        try{
            int flag=bankCardService.deleteOne(userId,order);
            if(flag==1){
                return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
            }else{
                return ResponseEntity.ok(new MessageResponse(1, "删除失败，卡不存在!"));
            }
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "删除失败，请检查卡是否存在！"));
        }

    }

    @PostMapping("/get-card-list")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> getCardList(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        try{
            List<Object> list=bankCardService.getList(userId);
            HashMap<String,Object>a=new HashMap<>();
            a.put("bankCardList",list);
            return ResponseEntity.ok(new DataResponse(0,a));

        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }

    }


    @PostMapping("/get-card-choose-list")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> getCardChooseList(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        try{
            List<Object> list=bankCardService.getListItem(userId);
            HashMap<String,Object>a=new HashMap<>();
            a.put("bankCardList",list);
            return ResponseEntity.ok(new DataResponse(0,a));

        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }

    }

}
