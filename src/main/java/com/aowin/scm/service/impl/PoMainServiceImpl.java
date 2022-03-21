package com.aowin.scm.service.impl;

import com.aowin.scm.dao.*;
import com.aowin.scm.pojo.*;
import com.aowin.scm.service.PoMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PoMainServiceImpl implements PoMainService {

    @Autowired
    private PoMainDao poMainDao;

    @Autowired
    private PoItemDao poItemDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private StockRecordDao stockRecordDao;

    @Autowired
    private PayRecordDao payRecordDao;

    @Override
    public List<PoMain> findAll(Map<String, Object> params) {
        return poMainDao.findAll(params);
    }

    @Override
    public int countAll(String account) {
        return poMainDao.countAll(account);
    }

    @Override
    public PoMain findByPoId(BigDecimal poId) {
        return poMainDao.findByPoId(poId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addPoMain(PoMain poMain) {
        poMainDao.addPoMain(poMain);
        for (PoItem poItem: poMain.getPoItems()){
            poItemDao.addPoItem(poItem);
            Product product = productDao.findByCode(poItem.getProductCode());
            Integer poNum = product.getPoNum() + poItem.getNum();
            product.setPoNum(poNum);
            productDao.updateProduct(product);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateMain(PoMain poMain) {
        BigDecimal poId = poMain.getPoId();
        for (PoItem poItem: poMain.getPoItems()){
            int num = poItemDao.findNum(poItem);
            Product product = productDao.findByCode(poItem.getProductCode());
            Integer poNum = product.getPoNum() - num;
            product.setPoNum(poNum);
            productDao.updateProduct(product);
        }
        while (!poItemDao.findByPoId(poId).isEmpty()){
            poItemDao.removePoItem(poId);
        }
        for (PoItem poItem: poMain.getPoItems()){
            poItemDao.addPoItem(poItem);
            Product product = productDao.findByCode(poItem.getProductCode());
            Integer poNum = product.getPoNum() + poItem.getNum();
            product.setPoNum(poNum);
            productDao.updateProduct(product);
        }
        poMainDao.updateMain(poMain);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removePoMain(BigDecimal poId) {
        for (PoItem poItem: poMainDao.findByPoId(poId).getPoItems()){
            int num = poItemDao.findNum(poItem);
            Product product = productDao.findByCode(poItem.getProductCode());
            Integer poNum = product.getPoNum() - num;
            product.setPoNum(poNum);
            productDao.updateProduct(product);
        }
        poItemDao.removePoItem(poId);
        poMainDao.removePoMain(poId);
    }

    @Override
    public List<PoMain> findByPayType(PoMain poMain) {
        return poMainDao.findByPayType(poMain);
    }

    @Override
    public void endPoMain(PoMain poMain) {
        poMainDao.endPoMain(poMain);
    }

    @Override
    public List<PoMain> queryByConditions(Map<String, Object> params) {
        return poMainDao.queryByConditions(params);
    }

    @Override
    public int countAllByCondition(QueryCondition queryCondition) {
        return poMainDao.countAllByCondition(queryCondition);
    }

    @Override
    public List<PoMain> findStockPoByPayType(PoMain poMain) {
        return poMainDao.findStockPoByPayType(poMain);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void stockPoMain(PoMain poMain) {
        poMainDao.stockPoMain(poMain);
        List<PoItem> items = poItemDao.findByPoId(poMain.getPoId());
        for (PoItem item: items){
            int num = item.getNum();
            Product product = productDao.findByCode(item.getProductCode());
            int poNum = product.getPoNum() - num;
            product.setPoNum(poNum);
            productDao.updatePoNum(product);
            StockRecord stockRecord = new StockRecord();
            stockRecord.setProductCode(item.getProductCode());
            stockRecord.setOrderCode(poMain.getPoId().toString());
            stockRecord.setStockNum(num);
            stockRecord.setStockType(1);
            stockRecord.setStockTime(poMain.getStockTime());
            stockRecord.setCreateUser(poMain.getStockUser());
            stockRecordDao.addStockRecord(stockRecord);
        }
    }

    @Override
    public List<SoMain> findOutcomePoMain(Map<String, Object> params) {
        return poMainDao.findOutcomePoMain(params);
    }

    @Override
    public int countOutcomePoMain(PoMain poMain) {
        return poMainDao.countOutcomePoMain(poMain);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void payMoney(PoMain poMain) {
        poMainDao.payMoney(poMain);
        PayRecord payRecord = new PayRecord();
        List<PoItem> items = poItemDao.findByPoId(poMain.getPoId());
        for (PoItem item: items){
            BigDecimal itemPrice = BigDecimal.valueOf(item.getItemPrice());
            payRecord.setPay_price(itemPrice);
            payRecord.setAccount(poMain.getPayUser());
            payRecord.setPay_time(poMain.getPayTime());
            if (poMain.getPayType().equals("货到付款") || poMain.getPayType().equals("款到发货")){
                payRecord.setPay_type(2);
            }else {
                if (poMain.getStatus() == 1){
                    payRecord.setPay_type(4);
                }else {
                    payRecord.setPay_type(2);
                }
            }
            payRecord.setOrderCode(String.valueOf(poMain.getPoId()));
            payRecordDao.addPayRecord(payRecord);
        }
    }

    @Override
    public PoMainResult reportPoMainSum(String startDate, String endDate) {
        DateRange dateRange = new DateRange();
        dateRange.setStartDate(startDate);
        dateRange.setEndDate(endDate);
        List<PoMain> poMains = poMainDao.findAllPoByMonth(dateRange);
        PoMainResult poMainResult = new PoMainResult();
        int endPoMainNum = 0;
        int totalPoMainNum = 0;
        float poMainTotalPrice = 0;
        float paidFee = 0;
        for (PoMain poMain:poMains){
            totalPoMainNum+=1;
            poMainTotalPrice+=poMain.getPoTotal();
            if (poMain.getPayType().equals("货到付款") || poMain.getPayType().equals("款到发货")){
                if (poMain.getStatus() == 3){
                    paidFee+=poMain.getPoTotal();
                }else if (poMain.getStatus() ==4){
                    paidFee+=poMainTotalPrice;
                    endPoMainNum+=1;
                }
            }else {
                if (poMain.getStatus() == 5 || poMain.getStatus() ==2){
                    paidFee+=poMain.getPrePayFee();
                }else if (poMain.getStatus() == 3){
                    paidFee+=poMain.getPoTotal();
                }else if (poMain.getStatus() == 4){
                    paidFee+=poMain.getPoTotal();
                    endPoMainNum+=1;
                }
            }
        }
        poMainResult.setTotalPoMainNum(totalPoMainNum);
        poMainResult.setEndPoMainNum(endPoMainNum);
        poMainResult.setPaidFee(paidFee);
        poMainResult.setPoMainTotalPrice(poMainTotalPrice);
        return poMainResult;
    }

    @Override
    public List<PoMain> reportPoMainDetail(Map<String, Object> params) {
        List<PoMain> poMains = poMainDao.reportPoMainDetail(params);
        for (int i=0;i<poMains.size();i++){
            PoMain poMain = poMains.get(i);
            if (poMain.getPayType().equals("货到付款") || poMain.getPayType().equals("款到发货")){
                if (poMain.getStatus() == 3 || poMain.getStatus() == 4){
                    poMain.setUnpaidFee(poMain.getPoTotal());
                    poMains.set(i,poMain);
                }
            }else {
                if (poMain.getStatus() == 2 || poMain.getStatus() == 5){
                    poMain.setUnpaidFee((poMain.getPoTotal()-poMain.getPrePayFee()));
                }else if (poMain.getStatus() == 1){
                    poMain.setUnpaidFee(poMain.getPoTotal());
                }
                poMains.set(i, poMain);
            }
        }
        return poMains;
    }

    @Override
    public int countPoMainDetail(DateRange dateRange) {
        return poMainDao.countPoMainDetail(dateRange);
    }



}
