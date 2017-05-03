package com.cat.service.impl;

import com.cat.repository.CommonRepository;
import com.cat.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class CommonServiceImpl<Entity, Id extends Serializable> implements CommonService<Entity, Id> {

    @Autowired
    private CommonRepository<Entity, Id> commonRepository;

}
