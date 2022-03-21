package com.aowin.scm.controller;

import com.aowin.scm.pojo.Customer;
import com.aowin.scm.pojo.QueryResult;
import com.aowin.scm.pojo.Result;
import com.aowin.scm.pojo.Vender;
import com.aowin.scm.service.CustomerService;
import com.aowin.scm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("findAllCustomer")
    public Result findAllCustomer(){
        List<Customer> list = customerService.findAllCustomer();
//        System.out.println("customers are" + list);
        return Result.ok(list);
    }

    @RequestMapping("customers")
    public QueryResult getAllCustomer(Integer currentPage, Integer pageSize, HttpServletRequest request){
        Page page = new Page(currentPage, pageSize);
        int total = customerService.countAll();
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
        List<Customer> list = customerService.findAll(params);
        return QueryResult.ok(total, list);
    }

    @PostMapping("addCustomer")
    public Result addVender(Customer customer){
        if (customerService.findCustomerByCode(customer.getCustomerCode()) != null){
            return Result.error("客户编号已存在");
        }else {
            customerService.addCustomer(customer);
            return Result.ok(null, "添加客户成功");
        }
    }

    @PostMapping("removeCustomer")
    public Result removeVender(String customerCode){
        customerService.removeCustomer(customerCode);
        return Result.ok(null, "删除客户成功");
    }

    @PostMapping("updateCustomer")
    public Result updateVender(Customer customer){
        System.out.println("客户"+customer);
        customerService.updateCustomer(customer);
        return Result.ok(null, "修改客户成功");
    }

    @PostMapping("findCustomerByCode")
    public Result findVenderByCode(String customerCode){
        Customer customer = customerService.findCustomerByCode(customerCode);
        return Result.ok(customer);
    }

    @RequestMapping("searchCustomer")
    public QueryResult searchVender(Integer currentPage, Integer pageSize, Customer customer){
        Page page = new Page(currentPage, pageSize);
        Map<String, Object> params = new HashMap<>();
        params.put("first", page.getFirst());
        params.put("max", page.getMax());
//        params.put("vender",vender);
        params.put("customerCode",customer.getCustomerCode());
        System.out.println("customerCode= "+customer.getCustomerCode());
        params.put("name",customer.getName());
        params.put("contactor",customer.getContactor());
        params.put("address",customer.getAddress());
        params.put("postcode",customer.getPostcode());
        params.put("tel",customer.getTel());
        params.put("fax",customer.getFax());
        params.put("createDate",customer.getCreateDate());
        List<Customer> list = customerService.searchCustomer(params);
        int total = customerService.countAllResults(params);
        //total = venderService.countAll();
        //List<Vender> list = venderService.findAll(params);
        return QueryResult.ok(total, list);
//        List<Vender> list = venderService.searchVender(vender);
//        if (list.isEmpty()){
//            return Result.ok(null, "没有查询到结果");
//        }else {
//            return Result.ok(list, "查询成功");
//        }

    }
}
