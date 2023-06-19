package org.ejavaexample.manifest.controller;


import org.ejavaexample.manifest.entity.Bill;
import org.ejavaexample.manifest.payload.response.DataResponse;
import org.ejavaexample.manifest.payload.response.MessageResponse;
import org.ejavaexample.manifest.service.BillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    BillServiceImpl billService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> createBill(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        String userName=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserName/"+token,String.class);
        int manifestId= Integer.parseInt(params.get("manifest_id"));
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
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        int type=params.get("type").equals("")?-1: Integer.parseInt(params.get("type"));
        try{
            List<Object> billList=billService.getBillByType(userId,type);
            HashMap<String,Object> map=new HashMap<>();
            map.put("bill_list",billList);
            return ResponseEntity.ok(new DataResponse(0,map));

        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }
    }

    @PostMapping("/pay-bill")//限定只有先付后到需要
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> payBill(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        //改成用货单id了！！！
        int billId = Integer.parseInt(params.get("bill_id"));
        int orders = Integer.parseInt(params.get("card_order"));
        String password=params.get("password");

        try{
            //需要和bankcard模块调
           // int flag=billService.payOne(userId,billId,orders,password);
            int flag=0;
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

    @PostMapping("/getOne")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> getOneBill(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        int bill= Integer.parseInt(params.get("manifest_id"));
        try{
            Bill flag=billService.getOneBill(bill, Math.toIntExact(userId));
            Map<String, Object> map=new HashMap<>();
            map.put("info",flag);
            System.out.println(bill+"    "+userId);
            if(flag!=null){
                return ResponseEntity.ok(new DataResponse(0,map));
            }else{
                return ResponseEntity.ok(new MessageResponse(1, "获取订单aaa失败"));
            }

        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取订单aaa失败"));
        }

    }



}
