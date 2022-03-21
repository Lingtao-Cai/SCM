package com.aowin.scm.service.impl;

import com.aowin.scm.dao.*;
import com.aowin.scm.pojo.*;
import com.aowin.scm.service.SoMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SoMainServiceImpl implements SoMainService {
    @Autowired
    private SoMainDao soMainDao;

    @Autowired
    private SoItemDao soItemDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private StockRecordDao stockRecordDao;

    @Autowired
    private PayRecordDao payRecordDao;


    @Override
    public List<SoMain> findAll(Map<String, Object> params) {
        return soMainDao.findAll(params);
    }

    @Override
    public int countAll(String account) {
        return soMainDao.countAll(account);
    }

    @Override
    public SoMain findBySoId(BigDecimal soId) {
        return soMainDao.findBySoId(soId);
    }

    @Override
    public void addSoMain(SoMain soMain) {
        soMainDao.addSoMain(soMain);
        for (SoItem soItem : soMain.getSoItems()) {
            Product product = productDao.findByCode(soItem.getProductCode());
            Integer soNum = product.getSoNum();
            soItemDao.addSoItem(soItem);
            soNum = soNum + soItem.getNum();
            product.setSoNum(soNum);
            productDao.updateProduct(product);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateSoMain(SoMain soMain) {
        for (SoItem soItem : soMain.getSoItems()) {
            int num = soItemDao.findNum(soItem);
            Product product = productDao.findByCode(soItem.getProductCode());
            Integer soNum = product.getSoNum();
            soItemDao.removeSoItem(soItem.getSoId(), soItem.getProductCode());
            soNum = soNum - num;
            soItemDao.addSoItem(soItem);
            soNum = soNum + soItem.getNum();
            product.setSoNum(soNum);
            productDao.updateProduct(product);
        }
        soMainDao.updateSoMain(soMain);
    }

    @Override
    public void removeSoMain(BigDecimal soId) {
        SoMain soMain = soMainDao.findBySoId(soId);
        for (SoItem soItem : soMain.getSoItems()) {
            int num = soItemDao.findNum(soItem);
            Product product = productDao.findByCode(soItem.getProductCode());
            Integer soNum = product.getSoNum();
            soItemDao.removeSoItem(soItem.getSoId(), soItem.getProductCode());
            soNum = soNum - num;
            product.setSoNum(soNum);
            productDao.updateProduct(product);
        }
        soMainDao.removeSoMain(soId);
    }

    @Override
    public List<SoMain> findBySoPayType(SoMain soMain) {
        return soMainDao.findBySoPayType(soMain);
    }

    @Override
    public void endSoMain(SoMain soMain) {
        soMainDao.endSoMain(soMain);
    }

    @Override
    public List<SoMain> queryByConditions(Map<String, Object> params) {
        return soMainDao.queryByConditions(params);
    }

    @Override
    public int countAllByCondition(QueryCondition queryCondition) {
        return soMainDao.countAllByCondition(queryCondition);
    }

    @Override
    public List<SoMain> findStockOutByPayType(SoMain soMain) {
        return soMainDao.findStockOutByPayType(soMain);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void stockSoMain(SoMain soMain) {
        soMainDao.stockSoMain(soMain);
        List<SoItem> items = soItemDao.findBySoId(soMain.getSoId());
        for (SoItem item : items) {
            int num = item.getNum();
            Product product = productDao.findByCode(item.getProductCode());
            int soNum = product.getSoNum() - num;
            product.setSoNum(soNum);
            productDao.updateSoNum(product);
            StockRecord stockRecord = new StockRecord();
            stockRecord.setProductCode(item.getProductCode());
            stockRecord.setOrderCode(soMain.getSoId().toString());
            stockRecord.setStockNum(num);
            stockRecord.setStockType(2);
            stockRecord.setStockTime(soMain.getStockTime());
            stockRecord.setCreateUser(soMain.getStockUser());
            stockRecordDao.addStockRecord(stockRecord);
        }

    }

    @Override
    public List<SoMain> findIncomeSoMain(Map<String, Object> params) {
        return soMainDao.findIncomeSoMain(params);
    }

    @Override
    public int countIncomeSoMain(SoMain soMain) {
        return soMainDao.countIncomeSoMain(soMain);
    }

    @Override
    public void receiveMoney(SoMain soMain) {
        soMainDao.receiveMoney(soMain);
        PayRecord payRecord = new PayRecord();
        List<SoItem> items = soItemDao.findBySoId(soMain.getSoId());
        for (SoItem item: items){
            BigDecimal itemPrice = BigDecimal.valueOf(item.getItemPrice());
            payRecord.setPay_price(itemPrice);
            payRecord.setAccount(soMain.getPayUser());
            payRecord.setPay_time(soMain.getPayTime());
            if (soMain.getPayType().equals("货到付款") || soMain.getPayType().equals("款到发货")){
                payRecord.setPay_type(1);
            }else {
                if (soMain.getStatus() == 1){
                    payRecord.setPay_type(3);
                }else {
                    payRecord.setPay_type(1);
                }
            }
            payRecord.setOrderCode(String.valueOf(soMain.getSoId()));
            payRecordDao.addPayRecord(payRecord);
        }
    }

    @Override
    public SoMainResult reportSoMainDetails(DateRange dateRange) {
        List<SoMain> soMains = soMainDao.findSoByMonth(dateRange);
        SoMainResult soMainResult = new SoMainResult();
        List<SoMain> result = new ArrayList<>();
        int endSoMainNum = 0;
        int totalSoMainNum = 0;
        float soMainTotalPrice = 0;
        float paidFee = 0;
        float unpaidFee = 0;
        for (SoMain soMain: soMains){
            totalSoMainNum+=1;
            soMainTotalPrice+=soMain.getSoTotal();
            if (soMain.getPayType().equals("货到付款") || soMain.getPayType().equals("款到发货")){
                if (soMain.getStatus() == 4){
                    endSoMainNum+=1;
                    paidFee+=soMain.getSoTotal();
                }else if (soMain.getStatus() == 3){
                    paidFee+=soMain.getSoTotal();
                } else {
                    soMain.setUnpaidFee(soMain.getSoTotal());
                }
            }else {
                if (soMain.getStatus() == 4){
                    endSoMainNum+=1;
                    paidFee+=soMain.getSoTotal();
                }else if (soMain.getStatus() == 5){
                    paidFee+=soMain.getPrePayFee();
                    soMain.setUnpaidFee(soMain.getSoTotal() - soMain.getPrePayFee());
                }else if (soMain.getStatus() == 3){
                    paidFee+=soMain.getSoTotal();
                }else {
                    soMain.setUnpaidFee(soMain.getSoTotal());
                }
            }
            result.add(soMain);
        }
        soMainResult.setEndSoMainNum(endSoMainNum);
        soMainResult.setTotalSoMainNum(totalSoMainNum);
        soMainResult.setPaidFee(paidFee);
        soMainResult.setUnpaidFee(unpaidFee);
        soMainResult.setSoMainTotalPrice(soMainTotalPrice);
        soMainResult.setSoMains(result);
        return soMainResult;
    }

}