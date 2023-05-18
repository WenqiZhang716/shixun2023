//package com.bezkoder.springjwt.controllers;
//
//
//
//import com.bezkoder.springjwt.models.*;
//import com.bezkoder.springjwt.payload.request.GetPositionRequest;
//import com.bezkoder.springjwt.payload.request.StoreFilterRequest;
//import com.bezkoder.springjwt.service.*;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author zhangwq
// */
//
//import com.bezkoder.springjwt.payload.response.DataResponse;
//import com.bezkoder.springjwt.payload.response.ListData;
//
//import com.bezkoder.springjwt.payload.request.AddGoodsRequest;
//import com.bezkoder.springjwt.payload.response.MessageResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/goods")
//public class GoodsController {
//
//    @Autowired
//    IGoodsInfoService goodsInfoService;
//
//    @Autowired
//    IGoodsTypeService goodsTypeService;
//
//    @Autowired
//    IStoreInfoService storeInfoService;
//
//    @Autowired
//    IApplyInfoService applyInfoService;
//
//    @Autowired
//    IAreaInfoService areaInfoService;
//
//
//
//
//
//
//    @PostMapping("/get-goods-list")
//    @PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
//    public ResponseEntity<?> getGoodsList() {
//
//        List<GoodsInfo> goodsInfoList = goodsInfoService.getList();
//        if (goodsInfoList.isEmpty()) {
//            return ResponseEntity.ok(new MessageResponse(1, "无工单信息!"));
////                return ResponseEntity
////                        .badRequest()
////                        .body(new MessageResponse(1,"Error: There is no goods here!"));
//        } else {
//            return ResponseEntity.ok(new DataResponse(0,new ListData(goodsInfoList)));
//        }
//    }
//
//    @PostMapping("/get-types")
//    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
//    public ResponseEntity<?> queryGoodsType(){
//        try {
//            List<GoodsType> goodsTypeList = goodsTypeService.getList();
//        }catch (Exception e){
//            return ResponseEntity.ok(new MessageResponse(1,"goodsType查询失败"));
//        }
//        return  ResponseEntity.ok(new DataResponse(0,new ListData(goodsTypeService.getList())));
//    }
//
//    @PostMapping("add-goods")
//    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
//    public ResponseEntity<?> addGoods(@RequestBody AddGoodsRequest addGoodsRequest){
//        String name = addGoodsRequest.getName();
//        String description = addGoodsRequest.getDescription();
//        int size = addGoodsRequest.getSize();
//        int type = addGoodsRequest.getType();
//
//        GoodsInfo goodsInfo = new GoodsInfo();
//        goodsInfo.setName(name);
//        goodsInfo.setType(type);
//        goodsInfo.setDescription(description);
//        goodsInfo.setSize(size);
//
//        try{
//            goodsInfoService.saveGoodsInfo(goodsInfo);
//        }catch (Exception e){
//            return ResponseEntity.ok(new MessageResponse(1,"goodsInfo添加失败"));
//        }
//        return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
//    }
//
//
//    @PostMapping("get-store-list")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getStoreList(@RequestBody StoreFilterRequest condition){
//        int areaId=condition.getArea_id();
//        int x=condition.getShelve_x();
//        int y=condition.getShelve_y();
//        int z=condition.getShelve_z();
//        System.out.println(areaId);
//        System.out.println(x);
//        System.out.println(y);
//        System.out.println(z);
//        try{
//            List<Map<String,Object>>maps=new ArrayList<>();
//            List<StoreInfo>list=storeInfoService.getStoreInfoList();
//            for(StoreInfo info:list){
//                int areaIdNew=(areaId==0?info.getAreaId():areaId);
//                int xNew=(x==0?info.getShelveX():x);
//                int yNew=(y==0?info.getShelveY():y);
//                int zNew=(z==0?info.getShelveZ():z);
//                System.out.println(areaIdNew);
//                System.out.println(xNew);
//                System.out.println(yNew);
//                System.out.println(zNew);
//                if(info.getAreaId()==areaIdNew&&info.getShelveX()==xNew&&
//                        info.getShelveY()==yNew&&info.getShelveZ()==zNew){
//                    Map<String,Object> item = new HashMap<>();
//                    item.put("id",info.getId());
//                    item.put("apply_id",info.getApplyId());
//                    item.put("area_id",info.getAreaId());
//                    item.put("x",info.getShelveX());
//                    item.put("y",info.getShelveY());
//                    item.put("z",info.getShelveZ());
//                    item.put("fee",info.getFee());
//                    item.put("time",info.getTime());
//
//                    ApplyInfo apply= applyInfoService.findByApplyId(info.getApplyId());
//                    if (apply!=null){
//                        GoodsInfo goods=goodsInfoService.findById(apply.getGoodsId());
//                        item.put("goodsInfo",goods);
//                    }else{
//                        item.put("goodsInfo",new HashMap<String,Object>());
//                    }
//                    maps.add(item);
//                }
//            }
//            Map<String,Object>ans=new HashMap<>();
//            ans.put("list",maps);
//            return ResponseEntity.ok(new DataResponse(0,ans));
//        }catch (Exception e){
//            return ResponseEntity.ok(new MessageResponse(1,e.getMessage()));
//        }
//
//    }
//
//    @PostMapping("get-statistics")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getStatistics(){
//
//        int inApply=applyInfoService.getTypeCount(0);
//        int outApply=applyInfoService.getTypeCount(1);
//        List<AreaInfo> areaList=areaInfoService.getAreaInfoList();
//        int totalSpace=0;
//        int remainSpace=0;
//        for (AreaInfo area:areaList){
//            totalSpace += area.getTotalSpace();
//            remainSpace += area.getRemainSpace();
//        }
//        double rate=(totalSpace-remainSpace)*1.0/totalSpace;
//
//        Map<String,Object> map=new HashMap<>();
//        map.put("inApply",inApply);
//        map.put("outApply",outApply);
//        map.put("rate",rate);
//        map.put("remainSpace",remainSpace);
//        return ResponseEntity.ok(new DataResponse(0,map));
//
//    }
//
//    @PostMapping("get-position")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<?> getPosition(@RequestBody GetPositionRequest getPositionRequest){
//        int id=getPositionRequest.getId();
//        List<StoreInfo> list=storeInfoService.findStoreInfoByApplyId(id);
//        if(list.isEmpty()){
//            return ResponseEntity.ok(new MessageResponse(1,"无此订单!"));
//        }
//        return ResponseEntity.ok(new DataResponse(0,new ListData(list)));
//    }
//
//}
