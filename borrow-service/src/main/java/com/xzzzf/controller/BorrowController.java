package com.xzzzf.controller;

import com.alibaba.fastjson.JSONObject;
import com.xzzzf.entity.BorrowDetails;
import com.xzzzf.service.BorrowService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BorrowController {

    @Resource
    private BorrowService borrowService;

    @RequestMapping("/borrow/{uid}")
    public BorrowDetails getUserBorrows(@PathVariable("uid") Integer uid) {
        return borrowService.getUserBorrowDetails(uid);
    }

    @RequestMapping("/blocked")
    JSONObject blocked() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 403);
        jsonObject.put("msg", "您的请求过于频繁，请稍后再试！");
        return jsonObject;
    }

}
