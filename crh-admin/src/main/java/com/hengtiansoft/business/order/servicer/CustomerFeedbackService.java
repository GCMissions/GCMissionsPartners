package com.hengtiansoft.business.order.servicer;

import com.hengtiansoft.business.order.dto.CustomerFeedbackPageDto;

public interface CustomerFeedbackService {

    void searchFeedback(CustomerFeedbackPageDto customerFeedbackPageDto);

    String changeStatus(Long id);
}
