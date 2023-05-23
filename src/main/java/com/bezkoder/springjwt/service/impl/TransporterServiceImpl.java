package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.Manifest;
import com.bezkoder.springjwt.models.Transport;
import com.bezkoder.springjwt.models.Transporter;
import com.bezkoder.springjwt.repository.BillRepository;
import com.bezkoder.springjwt.repository.ManifestRepository;
import com.bezkoder.springjwt.repository.TransportRepository;
import com.bezkoder.springjwt.repository.TransporterRepository;
import com.bezkoder.springjwt.service.ITransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangwq
 */
@Service
public class TransporterServiceImpl implements ITransporterService {

    @Autowired
    TransporterRepository transporterRepository;
    @Autowired
    TransportRepository transportRepository;
    @Autowired
    ManifestRepository manifestRepository;
    @Autowired
    BillRepository billRepository;
    @Override
    public List<Manifest> getListByStatus(Long userId, int status) {
        //-1全部，1需要取件，2需要送件，3已完成取件，4已完成送件
        Optional<Transporter>transporter = transporterRepository.findByUserId(userId);
        List<Manifest>maniList=new ArrayList<>();
        if(transporter.isPresent()){

            int belongId=transporter.get().getBelongId();
            List<Transport>list=transportRepository.findAllByStepId(belongId);
            if(status==1){
                //需要取件
                for(int i=0;i<list.size();i++){
                    Optional<Manifest>manifest=manifestRepository.findById(list.get(i).getManifestId());
                    if(manifest.isPresent()){
                        Manifest mani=manifest.get();
                        if(mani.getStatus()==0&&mani.getBeginId()==belongId&&(mani.getIsPay()==1||mani.getPayType()==1)){
                            maniList.add(mani);
                        }
                    }
                }

            }else if(status==2){
                //需要送件
                for(int i=0;i<list.size();i++){
                    Optional<Manifest>manifest=manifestRepository.findById(list.get(i).getManifestId());
                    if(manifest.isPresent()){
                        Manifest mani=manifest.get();
                        if(mani.getStatus()==4&&mani.getEndId()==belongId){
                            maniList.add(mani);
                        }
                    }
                }


            }else if(status==3){
                //已完成取件
                for(int i=0;i<list.size();i++){
                    Optional<Manifest>manifest=manifestRepository.findById(list.get(i).getManifestId());
                    if(manifest.isPresent()){
                        Manifest mani=manifest.get();
                        if(mani.getStatus()!=0&&mani.getStatus()!=3&&mani.getBeginId()==belongId&&mani.getIsPay()==1){
                            maniList.add(mani);
                        }
                    }
                }
            }else if(status==4){
                //已完成送件
                for(int i=0;i<list.size();i++){
                    Optional<Manifest>manifest=manifestRepository.findById(list.get(i).getManifestId());
                    if(manifest.isPresent()){
                        Manifest mani=manifest.get();
                        //2是已完成
                        if(mani.getStatus()==2&&mani.getEndId()==belongId){
                            maniList.add(mani);
                        }
                    }
                }
            }else{
                for(int i=0;i<list.size();i++){
                    Optional<Manifest>manifest=manifestRepository.findById(list.get(i).getManifestId());
                    manifest.ifPresent(maniList::add);
                }
            }
        }
        return maniList;
    }

    @Override
    public int changeManiStatus(int manifestId,int transporterId) {
        Optional<Manifest>manifest=manifestRepository.findById(manifestId);
        if(manifest.isPresent()){
            Manifest mani=manifest.get();
            List<Transport>transportList=transportRepository.findAllByManifestIdAndType(mani.getId(),1);
            if(mani.getStatus()==0){
                //将状态修改为1,transport里记录修改transporterId与时间
                manifestRepository.updateStatusByManiId(mani.getId(),1);
                for(Transport transport:transportList){
                    if(transport.getOrders()==1){
                        //更新运送者id与时间
                        int a=transportRepository.updateArriveTimeAndStatusAndTransporterIdById(transport.getId(),new Date(),1,transporterId);
                    }
                }
                //(开启定时服务)如果能实现
            }else if(mani.getStatus()==3){
                //将状态改为2，记录运送人与时间，账单支付状态修改（如果是先到后付）
                if(mani.getPayType()==1){
                    manifestRepository.updateIsPay(mani.getId());
                    manifestRepository.updateStatusByManiId(mani.getId(),2);//更新为已完成
                    billRepository.updateStatusByManifestId(mani.getId(),1);
                    for(Transport transport:transportList){
                        if(transport.getOrders()!=1){
                            //更新运送者id与时间
                            int a=transportRepository.updateArriveTimeAndStatusAndTransporterIdById(transport.getId(),new Date(),1,transporterId);
                            int b=transportRepository.updateStatusById(transport.getId()-1,2);
                        }
                    }
                }
            }
            return 1;
        }else{
            return 0;
        }

    }

    @Override
    public int getIdByUserId(Long userId) {
        Optional<Transporter> trans=transporterRepository.findByUserId(userId);
        if(trans.isPresent()){
            Transporter transporter=trans.get();
            return transporter.getId();
        }
        return -1;
    }
}
