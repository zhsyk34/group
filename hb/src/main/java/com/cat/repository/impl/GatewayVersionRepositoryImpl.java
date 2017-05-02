package com.cat.repository.impl;

import com.cat.entity.GatewayVersion;
import com.cat.repository.GatewayVersionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GatewayVersionRepositoryImpl extends CommonRepositoryImpl<GatewayVersion, Long> implements GatewayVersionRepository {
}
