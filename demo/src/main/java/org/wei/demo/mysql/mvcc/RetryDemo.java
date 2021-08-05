package org.wei.demo.mysql.mvcc;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wei.demo.dao.domain.Person;
import org.wei.demo.dao.mapper.PersonMapper;

@Slf4j
@Component
public class RetryDemo {

    @Autowired
    private PersonMapper personMapper;

    @Transactional
    public void tryUpdate() throws InterruptedException {
        int tryTime = 2;
        for (;;) {
            Person person = personMapper.getOne(5);
            System.out.println(person);
            if (tryTime == 2) {
                System.out.println("sleep!");
                Thread.sleep(10000);
            }
            log.debug("person:{}", person);
            int row = personMapper.updateAge(person.getId(), person.getName(), person.getAge() + 1);
            if (row == 1 || tryTime == 0) {
                System.out.println("更新成功");
                break;
            } else {
                System.out.println("更新失败，重试中！");
                log.debug("更新失败，重试中！");
                tryTime--;
            }
        }
    }

}
