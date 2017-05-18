package com.cat.repository;

import com.cat.entity.Common;
import com.cat.entity.Gateway;
import com.cat.entity.GatewayVersion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-bean.xml")
public class DoorLockRepositoryTest {

    @Autowired
    private DoorLockRepository doorLockRepository;
    @Resource
    private GatewayVersionRepository gatewayVersionRepository;

    @Test
    public void save() throws Exception {
        GatewayVersion version = new GatewayVersion();
        version.setMajor("Z01");
        double no = new Random().nextDouble();
        String minor = String.valueOf(no).substring(0, 5);
        version.setMinor(minor);
        version.setInfo("Z01-" + minor);
        version.setDownloadAddress("google.com" + minor);

        gatewayVersionRepository.save(version);
        System.out.println(version);
    }

    @Test
    public void save2() throws Exception {

    }

    @Test
    public void find() throws Exception {
        System.out.println(gatewayVersionRepository.findById(1L));
    }

    @Test
    public void list() throws Exception {
        System.out.println(gatewayVersionRepository.findById(1L));
    }

    @Test
    public void update() throws Exception {
        GatewayVersion version = new GatewayVersion();
        version.setId(1L);
        version.setMajor("aa");
        version.setMinor("xx");
        version.setInfo("aa-xx");
        version.setDownloadAddress("g.cn.fj");
//        version.setCreateTime(LocalDateTime.now());
        version.setUpdateTime(LocalDateTime.now());

        gatewayVersionRepository.update(version);
    }

    @Test
    public void merge() throws Exception {
        GatewayVersion version = new GatewayVersion();
        version.setMajor("aa13");
        version.setMinor("xx13");
        version.setInfo("aa-xx13");
        version.setDownloadAddress("g.cn.fj23");
//        version.setCreateTime(LocalDateTime.now());
//        version.setUpdateTime(LocalDateTime.now());

        gatewayVersionRepository.merge(version);
    }

    @Test
    public void delete1() throws Exception {
        System.out.println(gatewayVersionRepository.deleteById(5L));
    }

    @Test
    public void delete2() throws Exception {
        System.out.println(gatewayVersionRepository.deleteByIds(Arrays.asList(3L, 7L)));
    }

    @Test
    public void delete4() throws Exception {
        GatewayVersion version = new GatewayVersion();
        version.setId(99L);
        System.out.println(gatewayVersionRepository.deleteByEntity(version));
    }

    @Test
    public void delete5() throws Exception {
        List<GatewayVersion> list = Stream.iterate(9L, i -> i + 1).map(i -> {
            GatewayVersion version = new GatewayVersion();
            version.setId(i);
            return version;
        }).limit(5).collect(Collectors.toList());
        System.out.println(gatewayVersionRepository.deleteByEntities(list));
    }

    @Test
    public void delete6() throws Exception {
        System.out.println(gatewayVersionRepository.deleteByIds(new Long[]{4L, 5L, 9L}));
    }

    @Test
    public void contains1() throws Exception {
        GatewayVersion version = new GatewayVersion();
        version.setId(1L);
        System.out.println(gatewayVersionRepository.contains(version));
    }

    @Test
    public void contains2() throws Exception {
        GatewayVersion version = gatewayVersionRepository.findById(1L);
        System.out.println(gatewayVersionRepository.contains(version));//false?
    }

    //TODO:http://www.concretepage.com/java/jpa/jpa-entitymanager-and-entitymanagerfactory-example-using-hibernate-with-persist-find-contains-detach-merge-and-remove#contains
    @Test
    public void contains3() throws Exception {
        EntityManager manager = gatewayVersionRepository.manager();
        GatewayVersion version = manager.find(GatewayVersion.class, 1L);
        System.out.println(manager.contains(version));//false?
    }

    @Test
    public void generate() throws Exception {
//        Stream.generate();
//        Stream.iterate(9, i -> i + 1);

        Entity entity = AnnotationUtils.findAnnotation(Gateway.class, Entity.class);
        System.out.println(entity);
        System.out.println(Common.class.getSuperclass());
    }
}