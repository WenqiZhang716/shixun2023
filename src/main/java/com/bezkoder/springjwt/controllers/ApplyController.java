//package com.bezkoder.springjwt.controllers;
//
//import com.bezkoder.springjwt.models.*;
//import com.bezkoder.springjwt.payload.request.ApplyItem;
//import com.bezkoder.springjwt.payload.request.ApplyRetriveRequest;
//import com.bezkoder.springjwt.payload.request.GetTicketsRequest;
//import com.bezkoder.springjwt.payload.response.ListData;
//import com.bezkoder.springjwt.payload.response.MessageResponse;
//import com.bezkoder.springjwt.payload.response.DataResponse;
//
//import com.bezkoder.springjwt.payload.request.ApplySaveRequest;
//
//import com.bezkoder.springjwt.security.jwt.JwtUtils;
//import com.bezkoder.springjwt.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import java.util.HashMap;
//
//
//
///**
// * @author zhangwq
// */
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/apply")
//public class ApplyController {
//    @Autowired
//    JwtUtils jwtUtils;
//
//    IUserService userService;
//    @Autowired
//    IGoodsInfoService goodsInfoService;
//    @Autowired
//    IApplyGoodsService applyGoodsService;
//
//    @Autowired
//    IGoodsTypeService goodsTypeService;
//    @Autowired
//    IApplyInfoService applyInfoService;
//    @Autowired
//    IStoreInfoService storeInfoService;
//
//    @Autowired
//    IAreaInfoService areaInfoService;
//
////    @Autowired
////    GoodsInfoRepository goodsInfoRepository;
////    @Autowired
////    ApplyInfoRepository applyInfoRepository;
////
////    @Autowired
////    StoreInfoRepository storeInfoRepository;
//
//
//    /**
//     *
//     * 出库申请
//     */
//    @PostMapping("/apply-retrieve")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<?> applyRetrieve(@RequestBody ApplyRetriveRequest applyRetriveRequest, @RequestHeader("Authorization") String tokenBearer){
//
//        String token=tokenBearer.substring(7, tokenBearer.length());
//        String userName=jwtUtils.getUserNameFromJwtToken(token);
//        Long user_id=jwtUtils.getUserIdByJwtToken(token);
//        ApplyItem []list=applyRetriveRequest.getGoods_list();
//        for (ApplyItem item:list){
//            int goods_id=item.getGoods_id();
//            int num=item.getNum();
//
//            int newNum =0;
//            //通过货物名称在goodsinfo中改对应数量
//            if(goodsInfoService.findById(goods_id)==null)
//            {
//                //返回错误--仓库中没有该货物
//                return ResponseEntity.ok(new MessageResponse(1,"仓库中没有该货物!"));
//           /* return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse(1,"Error: There is no such goods here!"));*/
//            }
//            else {
//                newNum =goodsInfoService.findById(goods_id).getSum()-num;
//                if(newNum<0)
//                {
//                    //返回错误--货物数量不足
//                    return ResponseEntity.ok(new MessageResponse(1,"仓库中该货物数量不足!"));
//                /*return ResponseEntity
//                        .badRequest()
//                        .body(new MessageResponse(1,"Error: This kind of goods is insufficient!"));*/
//                }
//                else {
//                    //减少货物数量
//                    goodsInfoService.changeSum(goods_id,newNum);
//                    //更新申请信息
//                    ApplyInfo applyInfo = new ApplyInfo(Math.toIntExact(user_id),goods_id,1,num);
//                    applyInfoService.saveApplyInfo(applyInfo);
//                    //更新区域剩余空间
//                    int size = goodsInfoService.findById(goods_id).getSize();
//                    double space=0;
//                    switch (size){
//                        case 1:
//                            space=10;
//                            break;
//                        case 2:
//                            space=20;
//                            break;
//                        case 3:
//                            space=50;
//                            break;
//                        case 4:
//                            space=100;
//                            break;
//                        default:
//                            space=0;
//                            break;
//                    }
//                    List<StoreInfo> list1=storeInfoService.limitFindStoreInfoByGoodsId(goods_id,num);
//                    for(int i=0;i<list1.size();i++)
//                    {
//                        int area_id=list1.get(i).getAreaId();
//                        //areaInfoService.changeRemainSpaceById(area_id,space);
//                        //更新areainfo表
//                        areaInfoService.updateAreaInfoForRetrieve(area_id,space,size,num);
//                        //删除货物存储信息
//                        storeInfoService.deleteStoreInfoById(list1.get(i).getId());
//                    }
//                    return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
//                }
//            }
//        }
//        return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
//    }
//
//
//    /**
//     *
//     * 获取工单信息
//     */
//    @PostMapping("/get-tickets")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<?> getTickets(@RequestBody GetTicketsRequest getTicketsRequest, @RequestHeader("Authorization") String tokenBearer){
//        int type=getTicketsRequest.getType();
//        String token=tokenBearer.substring(7, tokenBearer.length());
//        String userName=jwtUtils.getUserNameFromJwtToken(token);
//        Long user_id=jwtUtils.getUserIdByJwtToken(token);
//        List<ApplyInfo> applyInfoList=applyInfoService.findApplyByUserId(Math.toIntExact(user_id));
//        //商品名和size
//        List<Ticket> tickets = new ArrayList<>();
//
//        for(int i=0;i<applyInfoList.size();i++)
//        {
//            int goods_id=applyInfoList.get(i).getGoodsId();
//            if(applyInfoList.get(i).getType()==type)
//            {
//                Ticket ticket =new Ticket(goodsInfoService.findById(goods_id).getName(),goodsInfoService.findById(goods_id).getSize(),applyInfoList.get(i).getNum(),applyInfoList.get(i).getDate(),0);
//                tickets.add(ticket);
//            }
//        }
//        return ResponseEntity.ok(new DataResponse(0,new ListData(tickets)));
//    }
//
//    /**
//     *
//     */
//    @PostMapping("/get-ticket-all")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getTicketAll(@RequestBody GetTicketsRequest getTicketsRequest, @RequestHeader("Authorization") String tokenBearer){
//        try {
//            int type = getTicketsRequest.getType();
//            String token = tokenBearer.substring(7, tokenBearer.length());
//            List<ApplyInfo> applyInfoList = applyInfoService.getApplyList();
//            //商品名和size
//            List<Ticket> tickets = new ArrayList<>();
//
//            for (int i = 0; i < applyInfoList.size(); i++) {
//                int goods_id = applyInfoList.get(i).getGoodsId();
//                if (applyInfoList.get(i).getType() == type) {
//                    Ticket ticket = new Ticket(goodsInfoService.findById(goods_id).getName(), goodsInfoService.findById(goods_id).getSize(), applyInfoList.get(i).getNum(), applyInfoList.get(i).getDate(), 0);
//                    tickets.add(ticket);
//                }
//            }
//            return ResponseEntity.ok(new DataResponse(0,new ListData(tickets)));
//        }catch (BadCredentialsException ex) {
//            //	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            return ResponseEntity.ok(new MessageResponse(1,"权限问题！"));
//        }
//
//    }
//
//
//    /**
//     *
//     * 申请入库
//     */
//    @PostMapping("/apply-save")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<?> applyWareHousing(@RequestBody ApplySaveRequest applySaveRequest, @RequestHeader("Authorization") String tokenBearer){
//        String token=tokenBearer.substring(7, tokenBearer.length());
//        String userName=jwtUtils.getUserNameFromJwtToken(token);
//        Long user_id=jwtUtils.getUserIdByJwtToken(token);
//        ApplyItem[] list=applySaveRequest.getGoods_list();
//        //提前判断是否有余量，避免非原子操作
//        for(ApplyItem judge:list){
//            int goods_id=judge.getGoods_id();
//            String goodsName=goodsInfoService.getName(goods_id);
//            int num=judge.getNum();
//            int Type=0;
//            int size=0;
//            GoodsType TypeName;
//            try{
//                //获取货物种类以确定存储区域
//                Type = goodsInfoService.getType(goods_id);
//                TypeName=goodsTypeService.getById(Type);
//                size = goodsInfoService.getSize(goods_id);
//            }catch (Exception e){
//                return ResponseEntity.ok(new MessageResponse(1,"从GoodsInfo中获取信息失败"));
//            }
//            try{
//                int remain=0;
//                List<AreaInfo>areaInfoList = areaInfoService.getByAreaType(Type);
//                for(AreaInfo area:areaInfoList){
//                    switch(size){
//                        case 1:
//                            remain=remain+area.getRemain1();
//                            break;
//                        case 2:
//                            remain=remain+area.getRemain2();
//                            break;
//                        case 3:
//                            remain=remain+area.getRemain3();
//                            break;
//                        case 4:
//                            remain=remain+area.getRemain4();
//                            break;
//                        default:
//                            remain=remain+0;
//                            break;
//                    }
//                }
//                if(remain<num){
//                    return ResponseEntity.ok(new MessageResponse(1,TypeName+"余量不足！"+goodsName+"无法存储！"));
//                }
//            }catch (Exception e){
//                return ResponseEntity.ok(new MessageResponse(1,"areaInfo查询失败"));
//            }
//
//
//        }
//
//        for(ApplyItem item:list){
//            int goods_id=item.getGoods_id();
//            int num=item.getNum();
//            int numHelp1=num;
//            int Type=0;
//            int size=0;
//
//        try{
//            //获取货物种类以确定存储区域
//             Type = goodsInfoService.getType(goods_id);
//            size = goodsInfoService.getSize(goods_id);
//        }catch (Exception e){
//            return ResponseEntity.ok(new MessageResponse(1,"从GoodsInfo中获取信息失败"));
//        }
//
//        ApplyInfo applyInfo = new ApplyInfo(Math.toIntExact(user_id),goods_id,Type,num);
//        try {
//            applyInfoService.saveApplyInfo(applyInfo);
//        }catch (Exception exception){
//            System.out.println(exception);
//            return ResponseEntity.ok(new MessageResponse(1,"applyInfo存储失败"));
//        }
//        int applyId = applyInfo.getId();
//
//        List<AreaInfo> areaInfoList;
//        try{
//            areaInfoList = areaInfoService.getByAreaType(Type);
//        }catch (Exception e){
//            return ResponseEntity.ok(new MessageResponse(1,"areaInfo查询失败"));
//        }
//            //执行申请货物数量次
//            for (int i=0;i<num;i++){
//                //遍历每一个存储区域
//                areaCycle:for (int j=0;j<areaInfoList.size();j++){
//                    if (areaInfoList.get(j).getRemainSpace()>0) {
//                        List<Integer> remainList = new ArrayList<>();
//                        remainList.add(areaInfoList.get(j).getRemain1());
//                        remainList.add(areaInfoList.get(j).getRemain2());
//                        remainList.add(areaInfoList.get(j).getRemain3());
//                        remainList.add(areaInfoList.get(j).getRemain4());
//                        //找到对应货架
//                        if (remainList.get(size - 1) > 0) {
//                            int shelve[] = new int[40];
//                            List<StoreInfo> storeInfoList = storeInfoService.findByAreaAndShelveX(areaInfoList.get(j).getId(), size-1);
//                            //初始化货架存储情况
//                            for (StoreInfo storeInfo : storeInfoList) {
//                                shelve[storeInfo.getShelveY() * 10 + storeInfo.getShelveZ()] = 1;
//                            }
//
//                            for (int m = 0; m < shelve.length; m++) {
//                                //找到空位并添加货物
//                                if (shelve[m] == 0) {
//                                    StoreInfo storeInfo1 = new StoreInfo(goods_id, areaInfoList.get(j).getId(), size-1, m / 10, m % 10, 0, 0, applyId);
//                                    try {
//                                        storeInfoService.saveStoreInfo(storeInfo1);
//                                    } catch (Exception exception) {
//                                        System.out.println(exception);
//                                        return ResponseEntity.ok(new MessageResponse(1, "storeInfo存储失败"));
//                                    }
//
//
//                                    AreaInfo areaInfo1 = areaInfoList.get(j);
//                                    remainList.set(size - 1, remainList.get(size - 1) - 1);
//                                    areaInfo1.setRemain1(remainList.get(0));
//                                    areaInfo1.setRemain2(remainList.get(1));
//                                    areaInfo1.setRemain3(remainList.get(2));
//                                    areaInfo1.setRemain4(remainList.get(3));
//                                    areaInfo1.setRemainSpace(areaInfo1.getRemainSpace() - 7200 / (4 * 40));
//                                    areaInfoService.updateAreaInfo(areaInfo1);
//                                    numHelp1--;
//                                    break areaCycle;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//
//
//            if(numHelp1!=0){
//                return ResponseEntity.ok(new MessageResponse(1,"还有"+numHelp1+"个货物没有存储"));
//            }
//
//            try {
//                goodsInfoService.updateSum(goods_id, num);
//            }catch (Exception exception){
//                System.out.println(exception);
//                return ResponseEntity.ok(new MessageResponse(1,"goodsInfo修改失败"));
//            }
//
////            int apply_id = applyInfo.getId();
////            int store_id = storeInfo.getId();
////            applyGoodsService.saveApplyGoods(apply_id,store_id);
//        }
//
//        return ResponseEntity.ok(new DataResponse(0,new HashMap<String,Object>()));
//    }
//}
