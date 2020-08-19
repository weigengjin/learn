package org.wei.rediscache.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wei.rediscache.domain.Person;
import org.wei.rediscache.service.RedisService;

@RestController
@RequestMapping("/")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("get")
    public Map<String, Object> getOne(Integer id) {
        Map<String, Object> response = new HashMap<>();
        if (id == null) {
            response.put("success", false);
            return response;
        }
        Person one = redisService.getOne(id);
        response.put("success", true);
        response.put("data", one);
        return response;
    }

    @RequestMapping("getBatch")
    public Map<String, Object> getBatch(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        Map<String, Object> response = new HashMap<>();
        if (CollectionUtils.isEmpty(ids)) {
            response.put("success", false);
            return response;
        }
        List<Person> batch = redisService.getBatch(ids);
        response.put("success", true);
        response.put("data", batch);
        return response;
    }

}
