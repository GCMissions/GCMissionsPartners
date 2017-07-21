package com.hengtiansoft.common.sequence;

import com.hengtiansoft.common.sequence.service.SequenceGeneratorService;
import com.hengtiansoft.common.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class Name: SequenceGenerator Description: Sequence Generator
 * 
 * @author taochen
 */
@Service
public class SequenceGenerator {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    /**
     * Description: Take the sequence from RAM
     *
     * @param sequenceType
     * @return
     */

    public Long take(SequenceType sequenceType) {
        return sequenceGeneratorService.generate(sequenceType.getCode(), 1);
    }

    /**
     * Description: Take the order number
     *
     * @param sequenceType
     * @return
     */
    public String getOrderId(SequenceType sequenceType) {
        Long orderTimeStamp = DateTimeUtil.getOrderTimeStamp();

        return sequenceType.getHead() + (orderTimeStamp * 10000000 + take(sequenceType));

    }
}
