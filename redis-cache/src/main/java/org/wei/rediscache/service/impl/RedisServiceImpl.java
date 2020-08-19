package org.wei.rediscache.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.wei.rediscache.domain.Person;
import org.wei.rediscache.mapper.PersonMapper;
import org.wei.rediscache.service.RedisService;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    // redis key 前缀
    private static final String PERSON_PRE = "person-";
    @Autowired
    PersonMapper personMapper;
    @Autowired
    @Qualifier("personRedisTemplate")
    RedisTemplate<String, Person> redisTemplate;


    public Person setOne(Person person) {
        redisTemplate.opsForValue().set(PERSON_PRE + person.getId(), person);
        log.info("key: {}", PERSON_PRE + person.getId());
        return person;
    }

    /**
     * 单条数据
     * <p>
     * 单条数据查询，使用{@code get}和{@code set}方法
     * </p>
     */
    public Person getOne(Integer id) {
        // 先查缓存
        Person person = redisTemplate.opsForValue().get(PERSON_PRE + id);
        if (person == null) {
            log.info("get from db, id = {}", id);
            person = personMapper.getOne(id);
            if (person != null) {
                redisTemplate.opsForValue().set(PERSON_PRE + id, person);
                log.info("set redis, key: {}", PERSON_PRE + id);
            }
        }
        log.info("person: {}", person);
        return person;
    }

    /**
     * 批量数据
     * <p>
     * 批量数据查询和插入，使用{@code multiGet }和{@code multiSet }方法 查询缓存未命中的继续查询数据库，从数据库中取得数据后存入redis
     * </p>
     */
    public List<Person> getBatch(List<Integer> ids) {
        List<String> queryList = new ArrayList<>();
        for (Integer id : ids) {
            queryList.add(PERSON_PRE + id);
        }
        List<Person> personList = redisTemplate.opsForValue().multiGet(queryList);
        if (!CollectionUtils.isEmpty(personList)) {
            personList.removeAll(Collections.singleton(null));
            log.debug("get from redis: {}", personList);
            log.debug("personList: {}", personList);
            if (personList.size() == ids.size()) {
                return personList;
            }
            Set<Integer> redisSet = personList.stream().map(Person::getId)
                .collect(Collectors.toSet());

            Set<Integer> paramSet = new HashSet<>(ids);
            paramSet.removeAll(redisSet);
            ids = new ArrayList<>(paramSet);
        } else {
            personList = new ArrayList<>();
        }
        log.debug("get from db: {}", ids);
        List<Person> batch = personMapper.getBatch(ids);
        if (!CollectionUtils.isEmpty(batch)) {
            Map<String, Person> personMap = batch.stream().filter(Objects::nonNull).collect(
                Collectors.toMap(person -> PERSON_PRE + person.getId(), Function.identity()));
            redisTemplate.opsForValue().multiSet(personMap);
            log.debug("get from db: {}", personMap);
        }
        batch.addAll(personList);
        return batch;
    }


}
