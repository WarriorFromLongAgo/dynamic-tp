package com.dtp.core.notify.filter;

import cn.hutool.core.collection.CollUtil;
import com.dtp.common.dto.ExecutorWrapper;
import com.dtp.common.dto.NotifyItem;
import com.dtp.common.pattern.filter.Invoker;
import com.dtp.core.context.BaseNotifyCtx;
import com.dtp.core.notify.manager.NotifyItemManager;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Objects;

/**
 * NoticeBaseFilter related
 *
 * @author yanhom
 * @since 1.1.0
 **/
@Slf4j
public class NoticeBaseFilter implements NotifyFilter {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void doFilter(BaseNotifyCtx context, Invoker<BaseNotifyCtx> nextFilter) {

        val executorWrapper = context.getExecutorWrapper();
        NotifyItem notifyItem = NotifyItemManager.getNotifyItem(executorWrapper, context.getNotifyItemEnum());
        if (Objects.isNull(notifyItem) || !satisfyBaseCondition(notifyItem, executorWrapper)) {
            log.debug("DynamicTp refresh, change notification is not enabled, threadPoolName: {}",
                    executorWrapper.getThreadPoolName());
            return;
        }
        nextFilter.invoke(context);
    }

    public boolean satisfyBaseCondition(NotifyItem notifyItem, ExecutorWrapper executor) {
        return executor.isNotifyEnabled()
                && notifyItem.isEnabled()
                && CollUtil.isNotEmpty(notifyItem.getPlatforms());
    }
}
