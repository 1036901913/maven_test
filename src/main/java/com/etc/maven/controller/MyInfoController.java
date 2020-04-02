package com.etc.maven.controller;

import com.etc.maven.domain.MyInfo;
import com.etc.maven.service.MyInfoService;
import com.etc.maven.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyInfoController {
    @Autowired
    private MyInfoService infoService;

    //@RestController  代替 @Controller和@ResponseBody

    @RequestMapping("/insert_info")
    public Map<String, Object> insertInfo(MyInfo info) {
        int result = infoService.insertInfo(info);
        Map<String, Object> map = new HashMap<>();
        if (result != -1) {
            map.put("code", "200");
            map.put("msg", "success");
            CacheUtil.del("infoList");
        } else {
            map.put("code", "201");
            map.put("msg", "fail");
        }
        return map;
    }

    @RequestMapping("/queryAllInfo")
    public Map<String, Object> queryAllInfo() {

        Map<String, Object> map = new HashMap<>();

        System.out.println("infoService.queryInfo() = " + infoService.queryInfo());

        if (CacheUtil.getList("infoList").isEmpty()) {
            List<MyInfo> infoList = infoService.queryInfo();
            CacheUtil.setList("infoList", infoList);
        }


        List<MyInfo> infos = CacheUtil.getList("infoList");
        if (infos.size() > 0) {
            map.put("code", "200");
            map.put("msg", "success");
            map.put("result", infos);
        } else {
            map.put("code", "201");
            map.put("msg", "success,no data");
        }
        return map;
    }
}
