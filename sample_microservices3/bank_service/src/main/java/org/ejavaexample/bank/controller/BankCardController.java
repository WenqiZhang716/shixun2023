package org.ejavaexample.bank.controller;

import org.ejavaexample.bank.entity.BankCard;
import org.ejavaexample.bank.payload.response.DataResponse;
import org.ejavaexample.bank.payload.response.MessageResponse;
import org.ejavaexample.bank.repository.BankCardRepository;
import org.ejavaexample.bank.service.BankCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhangwq
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/bank-card")
public class BankCardController {


    @Autowired
    BankCardServiceImpl bankCardService;

    @Autowired
    BankCardRepository bankCardRepository;

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/test2")
    public String getUserId(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
       // String aaa=restTemplate.getForObject("http://PRODUCT-SERVICE:8083/api/product/test",String.class);
        return userId + "hello!" ;
    }

    @PostMapping("/add-bankcard")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> AddBankCard(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String tokenBearer){
        try{
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        int check=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/isCheck/"+userId,Integer.class);
        String bankCard = params.get("bank_card");
        String bankName=params.get("bank_name");
        System.out.println(bankName+" :"+bankCard);
        if(check==0){
            return ResponseEntity.ok(new MessageResponse(1, "请先进行实名认证!"));
        }else if (check==2){
            return ResponseEntity.ok(new MessageResponse(1, "用户不存在!"));
        }else{
            if(bankCard.equals("")||bankName.equals("")){
                return ResponseEntity.ok(new MessageResponse(1, "银行卡号与开户行名称不能为空!"));
            }
            int flag=bankCardService.addOne(userId,bankCard,bankName);
            return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
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
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
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
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
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
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        try{
            List<Object> list=bankCardService.getListItem(userId);
            HashMap<String,Object>a=new HashMap<>();
            a.put("bankCardList",list);
            return ResponseEntity.ok(new DataResponse(0,a));

        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }

    }

    @GetMapping("/payOne/{userId}&&{order}&&{amount}&&{password}")
    public int PayOneCard(@PathVariable("userId") Long userId,@PathVariable("order") int order,
                                        @PathVariable("amount") double amount,@PathVariable("password") String password){
        Optional<BankCard> bank=bankCardRepository.findByUserIdAndOrders(userId,order);
        if(bank.isPresent()){
            BankCard card=bank.get();
            if(!card.getPassword().equals(password)){
                return -2;
            }
            int cardId=card.getId();
            if(card.getAmount()<amount){
                return -5;
            }
            bankCardRepository.updateAmount(cardId,card.getAmount()-amount);
            return cardId;
        }else{
            return -1;
        }


    }



}
