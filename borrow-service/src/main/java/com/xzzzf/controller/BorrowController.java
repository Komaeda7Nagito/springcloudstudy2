package com.xzzzf.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONObject;
import com.xzzzf.entity.BorrowDetails;
import com.xzzzf.service.BorrowService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

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

    @RequestMapping("/test")
    @SentinelResource(value = "test",
            fallback = "except", // fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。
            exceptionsToIgnore = IOException.class) // 忽略某些异常，不再进入 fallback 逻辑中，而是直接原样抛出
    String test(){
        throw new RuntimeException("HelloWorld");
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    String except(Throwable e) {
        return "Oops, error occurred at " + e.getMessage();
    }

    @RequestMapping("/test2")
    @SentinelResource("test2")
    String findUserBorrow2(@RequestParam(value = "a",required = false) String a,
                           @RequestParam(value = "b",required = false) String b,
                           @RequestParam(value = "c",required = false) String c){
        return "请求成功！a = " + a + ", b = " + b + ", c = " + c;
    }

}
