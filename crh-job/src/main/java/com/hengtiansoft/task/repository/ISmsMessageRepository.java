package com.hengtiansoft.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.task.domain.SmsMessage;

/**
 * @author yigesong
 */
public interface ISmsMessageRepository extends JpaRepository<SmsMessage, Long> {

    @Query(value = "select * from M_SMS where STATUS = ?1 and (TIMES < ?2 or TIMES is null) AND IFNULL(SEND_TS, now()) <= now() limit 50", nativeQuery = true)
    List<SmsMessage> findByStatusAndTimesLessThanOrTimesIsNull(String status, Integer maxSendTime);

}
