package org.zwq.manifest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.zwq.manifest.entity.Bill;
import org.zwq.manifest.entity.Manifest;
import org.zwq.manifest.payload.request.CreateManiRequest;
import org.zwq.manifest.payload.response.DataResponse;
import org.zwq.manifest.payload.response.MessageResponse;
import org.zwq.manifest.repository.BillRepository;
import org.zwq.manifest.repository.ManifestRepository;
import org.zwq.manifest.service.BillServiceImpl;
import org.zwq.manifest.service.GoodsTypeServiceImpl;
import org.zwq.manifest.service.ManifestServiceImpl;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/mani")
public class ManifestController {

    @Autowired
    ManifestServiceImpl manifestService;

    @Autowired
    GoodsTypeServiceImpl goodsTypeService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ManifestRepository manifestRepository;

    @Autowired
    BillServiceImpl billService;

    @Autowired
    BillRepository billRepository;



    @PostMapping("/manifest-create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createMani(@Valid @RequestBody CreateManiRequest createManiRequest,
                                        @RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        int goodsType=createManiRequest.getGoods_type();
        double weight=createManiRequest.getWeight();
        int transportType=createManiRequest.getTransport_type();
        String beginAddress=createManiRequest.getBegin_address();
        String endAddress=createManiRequest.getEnd_address();
        int payType=createManiRequest.getPay_type();
        String receiverName=createManiRequest.getReceiver_name();
        String receiverPhone = createManiRequest.getReceiver_phone();
        String beizhu=createManiRequest.getBeizhu();

        int flag=manifestService.createManifest(userId,goodsType,weight,transportType,
        beginAddress,endAddress,payType, receiverName,receiverPhone,beizhu);
        System.out.println(flag);
        Map<String, Object> map=new HashMap<>();
        map.put("id",flag);
        if(flag>0){
            return ResponseEntity.ok(new DataResponse(0,map));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "创建失败!"));
        }
    }

    @PostMapping("/manifest-cancel")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>Cancel(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String tokenBearer){
        int id= Integer.parseInt(params.get("id"));
        String token=tokenBearer.substring(7, tokenBearer.length());
        int flag=manifestService.cancelOne(id);
        if(flag==1){
            return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
        }else if(flag==2){
            return ResponseEntity.ok(new MessageResponse(1, "货单不存在!"));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "已发货，不能取消!"));
        }
    }

    @PostMapping("/manifest-change-address")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>changeAddress(@RequestBody Map<String, String> params,
                                          @RequestHeader("Authorization") String tokenBearer){
        int id= Integer.parseInt(params.get("id"));
        String newAddress=params.get("new_address");
        String token=tokenBearer.substring(7, tokenBearer.length());
        int flag=manifestService.changeAddress(id,newAddress);
        if(flag==1){
            return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
        }else if(flag==2){
            return ResponseEntity.ok(new MessageResponse(1, "货单不存在!"));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "已发货，不能修改地址!"));
        }
    }

    @PostMapping("/manifest-find")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>findManifest(@RequestBody Map<String, String> params,
                                          @RequestHeader("Authorization") String tokenBearer){
        int type=params.get("type").equals("")?-1: Integer.parseInt(params.get("type"));
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        List<Object> list=manifestService.findAllManifestByStatus(type,userId);
        HashMap<String,Object> map=new HashMap<>();
        map.put("manifest_list",list);

        return ResponseEntity.ok(new DataResponse(0,map));

    }

    @PostMapping("/manifest-get-good-type-list")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>getGoodTypeList( @RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        List<Object>flag=goodsTypeService.getGoodsTypeList();
        if(!flag.isEmpty()){
            Map<String,Object> map=new HashMap<>();
            map.put("list",flag);
            return ResponseEntity.ok(new DataResponse(0,map));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "获取货物种类失败!"));
        }
    }


    @PostMapping("/manifest-get-one-detail")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>getOneDetail( @RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        int manifestId = Integer.parseInt(params.get("id"));
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        Map<String,Object> map=manifestService.getOneDetail(manifestId,userId);
        if(map!=null){
            Map<String,Object> map2=new HashMap<>();
            map2.put("info",map);
            return ResponseEntity.ok(new DataResponse(0,map2));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "获取货物种类失败!"));
        }
    }

    @PostMapping("/manifest-get-home-data")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?>getHomeData(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        Map<String,Object>map=manifestService.getHomeData(userId);
        if(map!=null){
            return ResponseEntity.ok(new DataResponse(0,map));
        }else{
            return ResponseEntity.ok(new MessageResponse(1, "获取首页数据失败!"));
        }
    }


    @GetMapping("/manifest-update-begin-and-end-id/{manifestId}&&{beginId}&&{endId}")
    public int updateBeginAndEndId(@PathVariable("manifestId")int manifestId,@PathVariable("beginId")int beginId,@PathVariable("endId")int endId){
      int a=manifestRepository.updateBeginAndEndId(manifestId, beginId, endId);
      return a;
    }

    @GetMapping("/manifest-get-begin-and-end-address/{id}")
    public Map<String,Object>getBeginAndEndAddress(@PathVariable("id") int id){
        Optional<Manifest> manifest = manifestRepository.findById(id);
        Map<String,Object>map=new HashMap<>();
        if(manifest.isPresent()){
            map.put("begin",manifest.get().getBeginAddress());
            map.put("end",manifest.get().getEndAddress());
            map.put("payType",manifest.get().getPayType());
        }
        return map;
    }

    @GetMapping("/manifest-finish-manifest/{id}")
    public int finishManifest(@PathVariable("id")int id){
        Optional<Manifest>mani=manifestRepository.findById(id);
        if(mani.isPresent()){
            Manifest manifest = mani.get();
            if(manifest.getStatus()==1){
                manifestRepository.updateStatusByManiId(manifest.getId(),2);
                manifestRepository.updateIsPay(manifest.getId());
                System.out.println("完成一条订单");
            }
         return 1;
        }
        return 0;
    }

    @GetMapping("/manifest-change-one-manifest/{id}")
    public int changeOneManifest(@PathVariable("id")int id){
        Optional<Manifest>mani=manifestRepository.findById(id);
        if(mani.isPresent()){
            Manifest manifest = mani.get();
            //修改成进行中
            if(manifest.getStatus()==0){
                manifestRepository.updateStatusByManiId(manifest.getId(),1);
                System.out.println("将一条订单状态修改为进行中");
            }
            return 1;
        }else{
            return 0;
        }

    }

    @PostMapping("/bill-create")
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


    @PostMapping("/bill-get-list")
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

    @PostMapping("/bill-pay-bill")//限定只有先付后到需要
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> payBill(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
        //改成用货单id了！！！
        int billId = Integer.parseInt(params.get("bill_id"));
        int orders = Integer.parseInt(params.get("card_order"));
        String password=params.get("password");

//        try{
            //需要和bankcard模块调，似乎成功了
            Optional<Bill> bill=billRepository.findByManifestId(billId);
            if(bill.isPresent()){
                Bill bil=bill.get();
                if(bil.getStatus()!=0){
                    return ResponseEntity.ok(new MessageResponse(1, "订单已支付或已取消！"));
                }
                if(bil.getPayWay()==1){
                    return ResponseEntity.ok(new MessageResponse(1, "该订单为先到后付订单，请等待收货人支付！"));
                }
                double pay=bil.getPayment();
                int cardId=restTemplate.getForObject("http://BANK-SERVICE:8002/api/bank-card/payOne/"+userId+"&&"+orders+"&&"+pay+"&&"+password,Integer.class);;
                int manifestId=bil.getManifestId();
                if(cardId==-1){
                    return ResponseEntity.ok(new MessageResponse(1, "银行卡不存在！"));
                }else if(cardId==-2){
                    return ResponseEntity.ok(new MessageResponse(1, "密码不正确！"));
                }else if(cardId== -5){
                    ResponseEntity.ok(new MessageResponse(1, "支付失败,余额不足！"));
                }
                billRepository.updatePayStatus(bil.getManifestId(),cardId,1,new Date());
                manifestRepository.updateIsPay(manifestId);
                //支付后才进入定时模拟阶段
                restTemplate.getForObject("http://TRANN-SERVICE:8029/api/transport/update-valid-by-manifest-id/"+manifestId,Integer.class);
                return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));

            }
            return ResponseEntity.ok(new MessageResponse(1, "支付失败,账单不存在！"));

//        }catch(Exception e){
//            return ResponseEntity.ok(new MessageResponse(1, "支付失败"));
//        }

    }

    @PostMapping("/bill-cancel")
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

    @PostMapping("/bill-getOne")
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
            return ResponseEntity.ok(new MessageResponse(1, "获取订单失败"));
        }

    }




}
