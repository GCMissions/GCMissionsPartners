package com.hengtiansoft.common.sequence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface SequenceRepository extends JpaRepository<SequenceEntity, String> {

    @Query("update SequenceEntity set lastNumber = ?3 where sequenceName =?1 and  lastNumber = ?2")
    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Integer updateLastNumber(String typeName, Long lastNumber, Long nextValue);
}
