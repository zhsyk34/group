package com.cat.repository;

import com.alibaba.fastjson.JSON;
import com.cat.entity.Gateway;
import com.cat.persistence.Order;
import com.cat.persistence.Page;
import com.cat.persistence.Sort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/spring.xml")
public class GatewayRepositoryTest {

    @Resource
    private GatewayRepository gatewayRepository;

    @Test
    public void save() throws Exception {
        Gateway gateway = new Gateway();
        gateway.setName("gw001");
        gateway.setAppId("app001");
        gateway.setSn("1-1-1-1");
        gateway.setUdid("sn1001");
        gateway.setMajor("major");
        System.out.println(gatewayRepository.save(gateway));
        System.out.println(gateway);

    }

    @Test
    public void getById() throws Exception {
        Gateway gateway = gatewayRepository.getById(1L);
        System.out.println(JSON.toJSONString(gateway));
    }

    @Test
    public void update() throws Exception {
        Gateway gateway = gatewayRepository.getById(1L);
        gateway.setUpdateTime(LocalDateTime.now());
        gateway.setName("new_name");
        System.out.println(gatewayRepository.update(gateway));
    }

    @Test
    public void delete() throws Exception {
        System.out.println(gatewayRepository.deleteById(1L));
    }

    @Test
    public void delete2() throws Exception {
        System.out.println(gatewayRepository.deleteByIds(new Long[]{1L, 3L, 5L}));
    }

    @Test
    public void list() throws Exception {
        gatewayRepository.list();
    }

    @Test
    public void list2() throws Exception {
        gatewayRepository.list(Sort.of("id", Order.ASC), null);
        gatewayRepository.list(Sort.from("id"), null);
        gatewayRepository.list(Sort.from("id"), Page.from(1, 10));
    }

    @Test
    public void list3() throws Exception {
        gatewayRepository.list(Sort.from("id"), Page.from(1, 10));
    }

    @Test
    public void list4() throws Exception {
        org.springframework.data.domain.Sort.Order id = new org.springframework.data.domain.Sort.Order("id");
        org.springframework.data.domain.Sort.Order name = new org.springframework.data.domain.Sort.Order("name");
        org.springframework.data.domain.Sort sort = new org.springframework.data.domain.Sort(id, name);
//        gatewayRepository.listAndSort(sort);
    }
}