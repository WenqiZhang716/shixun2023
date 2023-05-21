package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.BankCard;
import com.bezkoder.springjwt.models.Bill;
import com.bezkoder.springjwt.models.Manifest;
import com.bezkoder.springjwt.repository.BankCardRepository;
import com.bezkoder.springjwt.repository.BillRepository;
import com.bezkoder.springjwt.repository.ManifestRepository;
import com.bezkoder.springjwt.service.IBillService;
import com.bezkoder.springjwt.service.IManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangwq
 */
@Service
public class BillServiceImpl implements IBillService {
    @Autowired
    IManifestService manifestService;
    @Autowired
    ManifestRepository manifestRepository;
    @Autowired
    BillRepository billRepository;
    @Autowired
    BankCardRepository bankCardRepository;

    @Override
    public int createOne(Long userId,String username, int manifestId) {
        Optional<Manifest> manifest = manifestRepository.findById(manifestId);
        if(manifest.isPresent()){
            Manifest mani=manifest.get();
            int payType=mani.getPayType();
            int status=mani.getStatus();
            if(status==0){
                Bill bill=new Bill();

                int transportType=mani.getTransportType();
                double pay=mani.getAmount();

                double payoff=0;
                if(transportType==0){
                    payoff=0.3;
                }else if(transportType==1){
                    payoff=0.1;
                }else {
                    payoff=0;
                }
                bill.setManifestId(manifestId);
                bill.setPayoff(payoff);
                bill.setPayment(pay-pay*payoff);
                bill.setUserId(userId);

                if(payType==0){
                    bill.setPayName(username);
                    bill.setPayWay(0);
                }else{
                    bill.setPayName(mani.getReceiverName());
                    bill.setPayWay(1);
                }
                billRepository.save(bill);
              //  manifestRepository.updateIsPay(mani.getId());
                return 1;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
    }

    @Override
    public int payOne(Long userId,int billId, int cardOrder, String password) {
        Optional<Bill>bill=billRepository.findById(billId);
        Optional<BankCard>bank=bankCardRepository.findByUserIdAndOrders(userId,cardOrder);
        if(bill.isPresent()&&bank.isPresent()){
            Bill bil=bill.get();
            BankCard card=bank.get();
            if(!card.getPassword().equals(password)){
                return 2;
            }
            if(bil.getStatus()!=0){
                return 3;
            }
            if(bil.getPayWay()==1){
                return 4;
            }
            double pay=bil.getPayment();
            int cardId=card.getId();
            int manifestId=bil.getManifestId();
            if(card.getAmount()<pay){
                return 5;
            }
            bankCardRepository.updateAmount(cardId,card.getAmount()-pay);
            billRepository.updatePayStatus(bil.getId(),cardId,1);
            manifestRepository.updateIsPay(manifestId);

            return 1;
        }
        return 0;
    }

    @Override
    public int cancelBill(int id){
        int flag=billRepository.updatePayStatus(id,0,2);
        return flag;
    }

    @Override
    public List<Bill> getBillByType(Long userId, int type) {
        List<Bill>list=new ArrayList<>() ;
        if(type==-1){
           list=billRepository.findAllByUserId(userId);
        }else if(type==3){
            list=billRepository.findAllByUserIdAndPayWay(userId,1);
        }else{
            list=billRepository.findAllByUserIdAndStatus(userId, type);
        }

        return list;
    }
}