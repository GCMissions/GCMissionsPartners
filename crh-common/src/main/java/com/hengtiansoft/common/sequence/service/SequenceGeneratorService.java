package com.hengtiansoft.common.sequence.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by taochen.
 */
public interface SequenceGeneratorService {
    @Transactional
    Long generate(String type, long increment);
}
