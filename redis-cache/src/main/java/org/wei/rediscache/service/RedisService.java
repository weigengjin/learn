package org.wei.rediscache.service;

import java.util.List;
import org.wei.rediscache.domain.Person;

public interface RedisService {

    Person getOne(Integer id);

    List<Person> getBatch(List<Integer> ids);

}
