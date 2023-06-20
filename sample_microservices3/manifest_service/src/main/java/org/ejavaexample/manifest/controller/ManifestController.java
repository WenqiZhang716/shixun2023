package org.ejavaexample.manifest.controller;

import jakarta.validation.Valid;
import org.ejavaexample.manifest.entity.Manifest;
import org.ejavaexample.manifest.payload.request.CreateManiRequest;
import org.ejavaexample.manifest.payload.response.DataResponse;
import org.ejavaexample.manifest.payload.response.MessageResponse;
import org.ejavaexample.manifest.repository.ManifestRepository;
import org.ejavaexample.manifest.service.GoodsTypeServiceImpl;
import org.ejavaexample.manifest.service.ManifestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/mani/manifest")
public class ManifestController {

    @Autowired
    ManifestServiceImpl manifestService;

    @Autowired
    GoodsTypeServiceImpl goodsTypeService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ManifestRepository manifestRepository;


    @PostMapping("/create")
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

    @PostMapping("/cancel")
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

    @PostMapping("/change-address")
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

    @PostMapping("/find")
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

    @PostMapping("/get-good-type-list")
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


    @PostMapping("/get-one-detail")
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

    @PostMapping("/get-home-data")
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


    @GetMapping("/update-begin-and-end-id/{manifestId}&&{beginId}&&{endId}")
    public int updateBeginAndEndId(@PathVariable("manifestId")int manifestId,@PathVariable("beginId")int beginId,@PathVariable("endId")int endId){
      int a=manifestRepository.updateBeginAndEndId(manifestId, beginId, endId);
      return a;
    }

    @GetMapping("/get-begin-and-end-address/{id}")
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

    @GetMapping("/finish-manifest/{id}")
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

    @GetMapping("/change-one-manifest/{id}")
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



}
