package org.wei.rediscache.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.wei.rediscache.domain.Person;


@Mapper
public interface PersonMapper {

    Person getOne(Integer id);

    List<Person> getBatch(@Param("ids") List<Integer> ids);

}
