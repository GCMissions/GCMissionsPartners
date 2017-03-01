package com.hengtiansoft.common.sequence.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liminghua on 16/6/13.
 */
public interface SequenceGeneratorService {
    @Transactional
    Long generate(String type, long increment);
}
