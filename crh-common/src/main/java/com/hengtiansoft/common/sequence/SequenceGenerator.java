package com.hengtiansoft.common.sequence;

import com.hengtiansoft.common.sequence.service.SequenceGeneratorService;
import com.hengtiansoft.common.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class Name: SequenceGenerator Description: 序列生成器
 * 
 * @author jialiangli
 */
@Service
public class SequenceGenerator {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    /**
     * Description: 从内存中取序列
     *
     * @param sequenceType
     * @return
     */
    
    public Long take(SequenceType sequenceType) {
        return sequenceGeneratorService.generate(sequenceType.getCode(), 1);
    }

    /**
     * Description: 取订单编号
     *
     * @param sequenceType
     * @return
     */
    public String getOrderId(SequenceType sequenceType) {
        Long orderTimeStamp = DateTimeUtil.getOrderTimeStamp();

        return sequenceType.getHead() + (orderTimeStamp * 10000000 + take(sequenceType));

    }
}
