package org.wei.demo.mysql.mvcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wei.demo.dao.domain.Person;
import org.wei.demo.dao.mapper.PersonMapper;

@Component
public class RetryDemo {

    @Autowired
    private PersonMapper personMapper;

    /**
     * <ol>
     *     <li>获取id为4, name为"tom"的用户的当前age</li>
     *     <li>尝试CAS方式更新年龄：当且仅当age为上一步中获取的age时能成功</li>
     *     <li>失败则重试，最多3次</li>
     * </ol>
     */
    @Transactional
    public void tryUpdate() throws InterruptedException {
        int tryTime = 0;
        while (tryTime < 3) {
            Person person = personMapper.getOne(4);
            System.out.println("获取id为4的person信息:" + person);
            if (tryTime == 0) {
                System.out.println("first sleep!");
                Thread.sleep(10000);
            }
            // UPDATE `person` SET `age`=#{newAge} WHERE `id`=#{id} AND `age`=#{oldAge}
            int row = personMapper.updateAge(person.getId(), person.getAge(), person.getAge() + 1);
            if (row == 1) {
                System.out.println("更新成功，新的person信息:");
                Person afterUpdate = personMapper.getOne(4);
                System.out.println(afterUpdate);
                break;
            } else {
                tryTime++;
                System.out.println("更新失败，重试中！重试第" + tryTime + "次");
            }
            if (tryTime == 3) {
                System.out.println("重试次数耗尽，程序结束");
            }
        }
    }

}
