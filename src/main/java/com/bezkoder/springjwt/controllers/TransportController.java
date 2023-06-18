package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Bill;
import com.bezkoder.springjwt.models.Manifest;
import com.bezkoder.springjwt.models.Transport;
import com.bezkoder.springjwt.models.TransportStep;
import com.bezkoder.springjwt.payload.response.DataResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.service.ITransportService;
import com.bezkoder.springjwt.service.ITransportStepService;
import com.bezkoder.springjwt.service.ITransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
    @Autowired
    ITransportService transportService;
    @Autowired
    ITransporterService transporterService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/path-planning")
    public ResponseEntity<?> planPath(@RequestBody Map<String, String> params){

        try{
//            String token=tokenBearer.substring(7, tokenBearer.length());
//            Long userId=jwtUtils.getUserIdByJwtToken(token);
            int id= Integer.parseInt(params.get("manifest_id"));
            transportService.deleteAll(id);
            List<Transport> transportSteps = transportService.pathPlan(id);
            HashMap<String, Object> map=new HashMap<>();
            map.put("path",transportSteps);
            return ResponseEntity.ok(new DataResponse(0,map));

        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }
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
        Long userId=jwtUtils.getUserIdByJwtToken(token);
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
        Long userId=jwtUtils.getUserIdByJwtToken(token);
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
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        int manifestId= Integer.parseInt(params.get("manifest_id"));
        try{
            int flag=transportService.deleteAll(manifestId);
            HashMap<String, Object> map=new HashMap<>();
            return ResponseEntity.ok(new DataResponse(0,map));
        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "删除失败！"));
        }

    }


    //更改订单状态
    @PostMapping("/change-status")
    @PreAuthorize("hasRole('TRANSPORT') ")
    public ResponseEntity<?> changeStatus(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        int manifestId= Integer.parseInt(params.get("manifest_id"));
        try{
        int transporterId=transporterService.getIdByUserId(userId);
        if(transporterId==-1){
            return ResponseEntity.ok(new MessageResponse(1, "运输员不存在！"));
        }else{
            int flag=transporterService.changeManiStatus(manifestId,transporterId);
            if(flag==0){
                return ResponseEntity.ok(new MessageResponse(1, "订单不存在！"));
            }else{
                return ResponseEntity.ok(new DataResponse(0,new HashMap<>()));
            }
        }


        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "更新失败"));
        }

    }
    //根据条件查询所负责订单
    @PostMapping("/find-by-type")
    @PreAuthorize("hasRole('TRANSPORT') ")
    public ResponseEntity<?> findByType(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
        //-1全部，1需要取件，2需要送件，3已完成取件，4已完成送件
        int type=params.get("type").equals("")?-1: Integer.parseInt(params.get("type"));
        try{
           List<Manifest>list=transporterService.getListByStatus(userId,type);
            HashMap<String,Object> map=new HashMap<>();
            map.put("bill_list",list);
            return ResponseEntity.ok(new DataResponse(0,map));

        }catch(Exception e){
            return ResponseEntity.ok(new MessageResponse(1, "获取列表失败"));
        }
    }


    @PostMapping("/big-step-list")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<?> getBigStep(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        Long userId=jwtUtils.getUserIdByJwtToken(token);
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
        Long userId=jwtUtils.getUserIdByJwtToken(token);
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




}
