package com.hengtiansoft.common.sequence.service.impl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import com.hengtiansoft.common.sequence.service.SequenceGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liminghua on 16/6/13.
 */
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceGeneratorServiceImpl.class);

    private static final Map<String, Queue<Long>> SEQUENCE_MAP = new ConcurrentHashMap<>();

    @Autowired
    private DataSource dataSource;

    @Override
    @Transactional
    public Long generate(String type, long increment) {
        if (!SEQUENCE_MAP.containsKey(type)) {
            synchronized (type.intern()) {
                if (!SEQUENCE_MAP.containsKey(type)) {
                    SEQUENCE_MAP.put(type, new LinkedBlockingQueue<Long>());
                }
            }
        }

        Queue<Long> queue = SEQUENCE_MAP.get(type);

        Long sequence = queue.poll();

        if (sequence == null) {
            synchronized (type.intern()) {
                sequence = queue.poll();
                if (sequence == null) {
                    queue.addAll(getNext(type, increment));
                }
                sequence = queue.poll();
            }
        }

        return sequence;
    }

    private List<Long> getNext(final String type, final Long increment) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Long>> future = executorService.submit(new Callable<List<Long>>() {
            @Override
            public List<Long> call() throws Exception {

                List<Long> list = new ArrayList<>(increment.intValue());

                try (Connection connection = DataSourceUtils.getConnection(dataSource);
                     PreparedStatement preparedStatement = connection.prepareStatement("SELECT seq(?, ?)")) {

                    preparedStatement.setString(1, type);
                    preparedStatement.setLong(2, increment);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            Long next = resultSet.getLong(1);
                            for (long i = 0; i < increment; i++) {
                                list.add(next + i);
                            }
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                return list;
            }
        });

        executorService.shutdown();
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }
}
