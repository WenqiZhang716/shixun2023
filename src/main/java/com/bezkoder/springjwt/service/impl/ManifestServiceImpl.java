package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.Bill;
import com.bezkoder.springjwt.models.GoodType;
import com.bezkoder.springjwt.models.Manifest;
import com.bezkoder.springjwt.repository.BillRepository;
import com.bezkoder.springjwt.repository.GoodTypeRepository;
import com.bezkoder.springjwt.repository.ManifestRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.service.IManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhangwq
 */
@Service
public class ManifestServiceImpl implements IManifestService {

    @Autowired
    private ManifestRepository manifestRepository;

    @Autowired
    private GoodTypeRepository goodTypeRepository;

    @Autowired
    private BillRepository billRepository;


    @Override
    public int createManifest(Long userId, int goodsType, double weight, int transportType,
                              String beginAddress, String endAddress, int payType, String receiverName,
                              String receiverPhone,String beizhu) {
        try{
            Manifest mani=new Manifest();
            mani.setUserId(userId);
            mani.setGoodTypeId(goodsType+1);
            mani.setBeginAddress(beginAddress);
            mani.setEndAddress(endAddress);
            mani.setWeight(weight);
            mani.setTransportType(transportType+1);
            mani.setPayType(payType);
            mani.setReceiverName(receiverName);
            mani.setReceiverPhone(receiverPhone);
            GoodType goodType= goodTypeRepository.findById(goodsType+1);
            double cost=goodType.getCost();
            mani.setAmount(weight*cost);
            mani.setBeizhu(beizhu);
            manifestRepository.save(mani);
            return mani.getId();
        }catch(Exception e){
            return 0;
        }
    }

    @Override
    public int cancelOne(int manifestId) {
        Optional<Manifest>mani=manifestRepository.findById(manifestId);
        if (mani.isPresent()){
            Manifest manifest=mani.get();
            if(manifest.getStatus()==0){
                manifestRepository.cancelIt(manifestId);
                billRepository.updateStatusByManifestId(manifestId,2);
               return 1;
            }else{
                return 0;
            }
        }else{
            return 2;
        }

    }

    @Override
    public int changeAddress(int manifestId, String newAddress) {
        Optional<Manifest>mani=manifestRepository.findById(manifestId);
        if (mani.isPresent()){
            Manifest manifest=mani.get();
            if(manifest.getStatus()==0){
                manifestRepository.changeAddress(manifestId, newAddress);
                return 1;
            }else{
                return 0;
            }
        }else{
            return 2;
        }
    }

    @Override
    public List<Object> findAllManifestByStatus(int status,Long userId) {
        //0未发货、1进行中、2已完成、3已取消,4未支付，5已支付，6 无需支付
        ArrayList<Manifest>list=new ArrayList<>();
        if(status==0||status==1||status==2||status==3){
            list= (ArrayList<Manifest>) manifestRepository.findAllByStatusAndUserId(status,userId);
        }else if(status==-1){
            list= (ArrayList<Manifest>) manifestRepository.findAllByUserId(userId);
        }else if(status==4){
            list=(ArrayList<Manifest>)manifestRepository.findAllByStatusAndIsPayAndPayTypeAndUserId(0,0,0,userId);
        }else if(status==5){
            list=(ArrayList<Manifest>)manifestRepository.findAllByIsPayAndPayTypeAndUserId(1,0,userId);
        }else{
            list=(ArrayList<Manifest>)manifestRepository.findAllByPayTypeAndUserId(1,userId);
        }
        List<Object>list2=new ArrayList<>();
        for(Manifest manifest:list){
            Map<String,Object> map=new HashMap<>();
            String transport="";
            if( manifest.getTransportType()==0){
                transport="普通件";
            }else if( manifest.getTransportType()==1){
                transport="快件";
            }else{
                transport="超快件";
            }
            String zt="";
            String tag="";
            if(manifest.getStatus()==0){
                zt="未收件";
                tag="warning";
            }else if(manifest.getStatus()==1){
                zt="进行中";
                tag="primary";
            }else if(manifest.getStatus()==2){
                zt="已完成";
                tag="success";
            }else if(manifest.getStatus()==3){
                zt="已取消";
                tag="danger";
            }else if(manifest.getStatus()==4){
                zt="待寄件";
                tag="primary";
            }
            String payStatus="";
            String tag2="";
            if(manifest.getStatus()==0 && manifest.getIsPay()==0&&manifest.getPayType()==0){
                payStatus="未支付";
                tag2="warning";
            }else if(manifest.getIsPay()==1 && manifest.getPayType()==0){
                payStatus="已支付";
                tag2="success";
            }else if(manifest.getStatus()==3){
                payStatus="已取消";
                tag2="danger";
            }else if(manifest.getPayType()==1){
                payStatus="无需您支付";
                tag2="info";
            }
            map.put("id", manifest.getId());
            map.put("userId", manifest.getUserId());
            GoodType good=goodTypeRepository.findById(manifest.getGoodTypeId());
            map.put("goodTypeId",  good.getName());
            map.put("weight", manifest.getWeight());
            map.put("transportType", transport);
            map.put("beginAddress", manifest.getBeginAddress());
            map.put("beginId", manifest.getBeginId());
            map.put("endAddress", manifest.getEndAddress());
            map.put("endId", manifest.getEndId());
            map.put("payType", manifest.getPayType()==0?"先付后到":"先到后付");
            map.put("amount", manifest.getAmount());
            map.put("receiverPhone", manifest.getReceiverPhone());
            map.put("receiverName", manifest.getReceiverName());
            map.put("beizhu", manifest.getBeizhu());
            map.put("status", zt);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(manifest.getCreateDate());
            map.put("createDate", format);
            map.put("isPay", manifest.getIsPay());
            map.put("tag",tag);
            map.put("payStatus",payStatus);
            map.put("tag2",tag2);
            list2.add(map);
        }

        return list2;
    }

    @Override
    public Map<String, Object> getOneDetail(int id, Long userId) {
        Optional<Manifest> mani =manifestRepository.findById(id);
        Map<String, Object> mapAll = new HashMap<>();
        if(mani.isPresent()){
            Manifest manifest =mani.get();
            if(manifest.getUserId().equals(userId)){
                Map<String,Object> map=new HashMap<>();
                String transport="";
                if( manifest.getTransportType()==0){
                    transport="普通件";
                }else if( manifest.getTransportType()==1){
                    transport="快件";
                }else{
                    transport="超快件";
                }
                String zt="";
                String tag="";
                if(manifest.getStatus()==0){
                    zt="未收件";
                    tag="warning";
                }else if(manifest.getStatus()==1){
                    zt="进行中";
                    tag="primary";
                }else if(manifest.getStatus()==2){
                    zt="已完成";
                    tag="success";
                }else if(manifest.getStatus()==3){
                    zt="已取消";
                    tag="danger";
                }else if(manifest.getStatus()==4){
                    zt="待寄件";
                    tag="primary";
                }
                map.put("id", manifest.getId());
                map.put("userId", manifest.getUserId());
                GoodType good=goodTypeRepository.findById(manifest.getGoodTypeId());
                map.put("goodTypeId",  good.getName());
                map.put("weight", manifest.getWeight());
                map.put("transportType", transport);
                map.put("beginAddress", manifest.getBeginAddress());
                map.put("beginId", manifest.getBeginId());
                map.put("endAddress", manifest.getEndAddress());
                map.put("endId", manifest.getEndId());
                map.put("payType", manifest.getPayType()==0?"先付后到":"先到后付");
                map.put("amount", manifest.getAmount());
                map.put("receiverPhone", manifest.getReceiverPhone());
                map.put("receiverName", manifest.getReceiverName());
                map.put("beizhu", manifest.getBeizhu());
                map.put("status", zt);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String format = simpleDateFormat.format(manifest.getCreateDate());
                map.put("createDate", format);
                map.put("isPay", manifest.getIsPay());
                map.put("tag",tag);
                mapAll.put("manifest",map);
                Optional<Bill> bill=billRepository.findByManifestId(id);
                Map<String,Object>map2=new HashMap<>();
                if(bill.isPresent()){
                    Bill b=bill.get();
                    String zt2="";
                    String tag2="";
                    if(b.getStatus()==0){
                        zt2="未支付";
                        tag2="warning";
                    }else if(b.getStatus()==1){
                        zt2="已支付";
                        tag2="success";
                    }else if(b.getStatus()==2){
                        zt2="已取消";
                        tag2="danger";
                    }else if(b.getStatus()==3){
                        zt2="您无需支付";
                        tag2="info";
                    }
                    map2.put("id", b.getId());
                    map2.put("manifestId", b.getManifestId());
                    map2.put( "payoff", b.getPayoff());
                    map2.put( "amount", b.getAmount());
                    map2.put( "payment", b.getPayment());
                    map2.put( "bankCardId", b.getBankCardId());
                    map2.put( "status", zt2);
                    map2.put( "payWay", b.getPayWay()==0?"先付后到":"先到后付");
                    map2.put( "payName", b.getPayName());
                    map2.put( "payPhone", b.getPayPhone());
                    map2.put( "userId",b.getUserId());
                    map2.put( "tag",tag2);
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    if(b.getPayDate()!=null){
                        String format2 = simpleDateFormat2.format(b.getPayDate());
                        map.put( "date", format2);
                    }else{
                        String format2="";
                        map.put( "date", format2);
                    }
                }
                mapAll.put("bill",map2);
                return mapAll;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getHomeData(Long userId) {
        List<Manifest>list1=manifestRepository.findAllByUserId(userId);
        List<Manifest>list2=manifestRepository.findAllByIsPayAndPayTypeAndUserId(0,0,userId);
        List<Manifest>list3=manifestRepository.findAllByStatusAndUserId(1,userId);
        List<Manifest>list4=manifestRepository.findAllByStatusAndUserId(2,userId);
        Map<String,Object> map=new HashMap<>();
        map.put("value1",list1.size());
        map.put("value2",list2.size());
        map.put("value3",list3.size());
        map.put("value4",list4.size());
        return map;
    }

}
