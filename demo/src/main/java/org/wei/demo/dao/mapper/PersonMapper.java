package org.wei.demo.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.wei.demo.dao.domain.Person;

@Mapper
public interface PersonMapper {

    Person getOne(int id);

    int updateAge(int id, String name, int age);

}
