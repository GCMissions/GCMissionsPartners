package com.hengtiansoft.common.later.fork;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.later.thread.ForkThread;
import com.hengtiansoft.common.util.ApplicationContextUtil;

/**
 * Created by liminghua on 16/4/22.
 */
public class Fork<R>{
    private static final Logger LOGGER = LoggerFactory.getLogger(Fork.class);

    private List<IForkFunction> forkTasks = new ArrayList<>();

    public Fork(){}

    @SuppressWarnings("unchecked")
    private List<R> compute(){
        List<ForkThread<R>> forkThreads = new ArrayList<>();
        for(IForkFunction forkFunction : forkTasks){
            ForkThread<R> forkThread = ApplicationContextUtil.getBean(ForkThread.class);
            forkThread.run(forkFunction);
            forkThreads.add(forkThread);
        }
        forkThreads.forEach(forkThread -> {
            try {
                forkThread.join();
            } catch (InterruptedException e) {
                LOGGER.error("Fork error", e);
            }
        });
        List<R> list = new ArrayList<>();
        forkThreads.forEach(forkThread -> {
            LOGGER.debug("Result Count:{}", forkThread.getResult().size());
            list.addAll(forkThread.getResult());
        });
        return list;
    }

    public Fork add(IForkFunction forkTask){
        forkTasks.add(forkTask);
        return this;
    }

    public List<R> run(){
        return compute();
    }

}
