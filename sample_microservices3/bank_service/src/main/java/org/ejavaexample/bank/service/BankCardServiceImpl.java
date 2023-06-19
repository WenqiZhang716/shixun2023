package org.ejavaexample.bank.service;


import org.ejavaexample.bank.entity.BankCard;
import org.ejavaexample.bank.repository.BankCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BankCardServiceImpl  {

    @Autowired
    BankCardRepository bankCardRepository;

    public int addOne(Long userId, String bankCardNum, String bankName) {

        BankCard bank=new BankCard();
//        if(userService.isChecked(userId)==1){
            bank.setUserId(userId);
            bank.setCardNum(bankCardNum);
            bank.setBankName(bankName);
            //理论上该从银行接口获得
            bank.setAmount(1000);
            bank.setPassword("123456");
            List<BankCard> list=bankCardRepository.findAllByUserId(userId);
            int a=list.size()+1;
            bank.setOrders(a);

            bankCardRepository.save(bank);
            return 1;
//        }else{
//            return 0;
//        }
    }

    public int deleteOne(Long userId, int order) {
        int flag=bankCardRepository.deleteByOrdersAndUserId(order,userId);
        bankCardRepository.updateOrderByOrder(userId,order);
        return 1;
    }

    public List<Object> getList(Long userId) {
        List<BankCard>list=bankCardRepository.findAllByUserId(userId);
        List<Object>list2=new ArrayList<>();
        for(BankCard bank:list){
            bank.setPassword("*********");
            Map<String,Object> map=new HashMap<>();
            map.put("id",bank.getId());
            map.put("userId",bank.getUserId());
            map.put("cardNum",bank.getCardNum());
            map.put("bankName",bank.getBankName());
            map.put("amount",String.format("%.2f", bank.getAmount()));
            map.put("password",bank.getPassword());
            map.put("orders",bank.getOrders());
            list2.add(map);
        }
        return list2;
    }

    public int getIdByOrder(Long userId, int order) {
        Optional<BankCard> bank=bankCardRepository.findByUserIdAndOrders(userId,order);
        return 0;
    }

    public List<Object> getListItem(Long userId) {
        List<BankCard>list=bankCardRepository.findAllByUserId(userId);
        List<Object>list2=new ArrayList<>();
        for(BankCard bank:list){
            bank.setPassword("*********");
            Map<String,Object> map=new HashMap<>();
            map.put("value",bank.getOrders());
            String label=bank.getBankName()+": "+bank.getCardNum().substring(0,3)+"...";
            map.put("label",label);
            list2.add(map);
        }
        return list2;
    }

}
