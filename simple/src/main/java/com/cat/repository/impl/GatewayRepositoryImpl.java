package com.cat.repository.impl;

import com.cat.entity.Gateway;
import com.cat.repository.GatewayRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GatewayRepositoryImpl extends CommonRepositoryImpl<Gateway, Long> implements GatewayRepository {
}