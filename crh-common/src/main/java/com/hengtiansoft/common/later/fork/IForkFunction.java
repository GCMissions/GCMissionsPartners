package com.hengtiansoft.common.later.fork;

import java.util.List;

/**
 * Created by liminghua on 16/4/22.
 */
public interface IForkFunction<R> {
    List<R> run();
}
