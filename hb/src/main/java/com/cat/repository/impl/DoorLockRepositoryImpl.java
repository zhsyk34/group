package com.cat.repository.impl;

import com.cat.entity.DoorLock;
import com.cat.repository.DoorLockRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DoorLockRepositoryImpl extends CommonRepositoryImpl<DoorLock, Long> implements DoorLockRepository {
}
