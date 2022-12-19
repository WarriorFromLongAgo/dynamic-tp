package com.dtp.common.dto;

import com.dtp.common.em.NotifyItemEnum;
import lombok.Data;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Executor wrapper
 *
 * @author yanhom
 * @since 1.0.3
 **/
@Data
public class ExecutorWrapper {

    private String threadPoolName;

    private Executor executor;

    private String threadPoolAliasName;

    /**
     * Notify items, see {@link NotifyItemEnum}.
     */
    private List<NotifyItem> notifyItems;

    private boolean notifyEnabled = true;

    public ExecutorWrapper(String threadPoolName, Executor executor) {
        this.threadPoolName = threadPoolName;
        this.executor = executor;
        this.notifyItems = NotifyItem.getSimpleNotifyItems();
    }

    public ExecutorWrapper(String threadPoolName, Executor executor, boolean notifyEnabled) {
        this.threadPoolName = threadPoolName;
        this.executor = executor;
        this.notifyItems = NotifyItem.getSimpleNotifyItems();
        this.notifyEnabled = notifyEnabled;
    }

    public ExecutorWrapper(String threadPoolName, Executor executor,
                           List<NotifyItem> notifyItems, boolean notifyEnabled) {
        this.threadPoolName = threadPoolName;
        this.executor = executor;
        this.notifyItems = notifyItems;
        this.notifyEnabled = notifyEnabled;
    }
}
