package org.wqzz.transport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.wqzz.transport.entity.Transport;
import org.wqzz.transport.entity.TransportStep;
import org.wqzz.transport.repository.TransportRepository;
import org.wqzz.transport.repository.TransportStepRepository;
import org.wqzz.transport.service.TransportServiceImpl;
import org.wqzz.transport.service.TransportStepServiceImpl;
import org.wqzz.transport.payload.response.*;

import java.util.*;

/**
 * @author zhangwq
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/transport")
public class TransportController {

    @Autowired
    TransportStepServiceImpl transportStepService;
    @Autowired
    TransportServiceImpl transportService;

    @Autowired
    TransportRepository transportRepository;

    @Autowired
    TransportStepRepository transportStepRepository;

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping("/path-planning")
    public ResponseEntity<?> planPath(@RequestBody Map<String, String> params){

     //   try{
            int manifestId= Integer.parseInt(params.get("manifest_id"));
            transportService.deleteAll(manifestId);
            List<TransportStep>list=new ArrayList<>();
            Map<String,Object>maniInfo=restTemplate.getForObject("http://MANI-SERVICE:8003/api/mani/manifest-get-begin-and-end-address/"+manifestId,Map.class);
            if(!maniInfo.isEmpty()){
                String beginA=(String)maniInfo.get("begin");
                String endA=(String)maniInfo.get("end");
                int payType=(int)maniInfo.get("payType");
                List<TransportStep> beginList =transportStepService.getStepIdByString(beginA);
                List<TransportStep>endList=transportStepService.getStepIdByString(endA);
                int len1=beginList.size();
                int len2=endList.size();
                for(int i=len1-1;i>=0;i--){
                    list.add(beginList.get(i));
                }
                for (TransportStep transportStep : endList) {
                    if (list.contains(transportStep)) {
                        list.remove(transportStep);
                    } else {
                        list.add(transportStep);
                    }
                }
                transportService.CreateOne(manifestId,beginA,0,0,1,1);
                int o=1;
                for (TransportStep transportStep : list) {
                    o++;
                    transportService.CreateOne(manifestId,transportStep.getName()+"中转站",transportStep.getId(),0,0,o);
                }
                transportService.CreateOne(manifestId,endA,0,0,1,++o);
                //先付后到，默认直接进入定时刷新状态
                if(payType==1){
                    transportRepository.updateValidByManifestId(manifestId);
                }
                List<Transport>tList=transportService.getPathList(manifestId);
                int beginId=tList.get(1).getStepId();
                int endId=tList.get(tList.size()-2).getStepId();
                restTemplate.getForObject("http://MANI-SERVICE:8003/api/mani/manifest-update-begin-and-end-id/"+manifestId+"&&"+beginId+"&&"+endId,Integer.class);
                HashMap<String, Object> map=new HashMap<>();
                map.put("path",tList);
                return ResponseEntity.ok(new DataResponse(0,map));
            }else{
                return ResponseEntity.ok(new MessageResponse(1, "货单不存在！"));
            }



//        }catch(Exception e){
//            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
//        }
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
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> GetStatusList(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
          int manifestId= Integer.parseInt(params.get("manifest_id"));
        try{
            List<Transport>list=transportService.getPathList(manifestId);
            HashMap<String, Object> map=new HashMap<>();
            map.put("path",list);
            return ResponseEntity.ok(new DataResponse(0,map));
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }

    }

    @PostMapping("/get-step-info-list")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> GetStepInfoList(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        int manifestId= Integer.parseInt(params.get("manifest_id"));
        try{
            return ResponseEntity.ok(new DataResponse(0,transportService.getStepInfo(manifestId)));
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }

    }



    @PostMapping("/deleteOne")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> deleteOne(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        int manifestId= Integer.parseInt(params.get("manifest_id"));
        try{
            int flag=transportService.deleteAll(manifestId);
            HashMap<String, Object> map=new HashMap<>();
            return ResponseEntity.ok(new DataResponse(0,map));
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "删除失败！"));
        }

    }


  //  更改订单状态,运货员方法，先不启动
//    @PostMapping("/change-status")
//    @PreAuthorize("hasRole('TRANSPORT') ")
//    public ResponseEntity<?> changeStatus(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
//        String token=tokenBearer.substring(7, tokenBearer.length());
//        Long userId=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,Long.class);
//        int manifestId= Integer.parseInt(params.get("manifest_id"));
//        try{
//        int transporterId=transporterService.getIdByUserId(userId);
//        if(transporterId==-1){
//            return ResponseEntity.ok(new MessageResponse(1, "运输员不存在！"));
//        }else{
//            int flag=transporterService.changeManiStatus(manifestId,transporterId);
//            if(flag==0){
//                return ResponseEntity.ok(new MessageResponse(1, "订单不存在！"));
//            }else{
//                return ResponseEntity.ok(new DataResponse(0,new HashMap<>()));
//            }
//        }
//
//
//        }catch(Exception e){
//            return ResponseEntity.ok(new MessageResponse(1, "更新失败"));
//        }
//
//    }

//    根据条件查询所负责订单，云货源方法，先不启动
//    @PostMapping("/find-by-type")
//    @PreAuthorize("hasRole('TRANSPORT') ")
//    public ResponseEntity<?> findByType(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
//        String token=tokenBearer.substring(7, tokenBearer.length());
//        Long userId=jwtUtils.getUserIdByJwtToken(token);
//        //-1全部，1需要取件，2需要送件，3已完成取件，4已完成送件
//        int type=params.get("type").equals("")?-1: Integer.parseInt(params.get("type"));
//        try{
//           List<Manifest>list=transporterService.getListByStatus(userId,type);
//            HashMap<String,Object> map=new HashMap<>();
//            map.put("bill_list",list);
//            return ResponseEntity.ok(new DataResponse(0,map));
//
//        }catch(Exception e){
//            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
//        }
//    }


    @PostMapping("/big-step-list")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> getBigStep(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        try{
            HashMap<String, Object> map=new HashMap<>();
            List<Object>list=transportStepService.getBigStepList();
            map.put("info",list);
            return ResponseEntity.ok(new DataResponse(0,map));
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取省/市列表失败！"));
        }

    }

    @PostMapping("/small-step-list")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> getSmallStep(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        int id= Integer.parseInt(params.get("id"));
        String token=tokenBearer.substring(7, tokenBearer.length());
        try{
            if(id==-1){
                return ResponseEntity.ok(new MessageResponse(1, "请先选择省/直辖市！"));
            }
            HashMap<String, Object> map=new HashMap<>();
            List<Object>list=transportStepService.getSmallList(id);
            map.put("id",list);
            return ResponseEntity.ok(new DataResponse(0,map));
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取省/直辖市列表失败！"));
        }

    }

    @GetMapping("/update-valid-by-manifest-id/{id}")
    public int updateValidByManifestId(@PathVariable("id") int id){
      int a=transportRepository.updateValidByManifestId(id);
      return a;
    }









}
