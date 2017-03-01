package com.hengtiansoft.common.later.thread;

import java.util.List;

import com.hengtiansoft.common.later.fork.IForkFunction;

/**
 * Created by liminghua on 16/4/22.
 */
public interface ForkThread<T> {
    void run(IForkFunction<T> forkFunction);

    List<T> getResult();

    void join() throws InterruptedException;
}
