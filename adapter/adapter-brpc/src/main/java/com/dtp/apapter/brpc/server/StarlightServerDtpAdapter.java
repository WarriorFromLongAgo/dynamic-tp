package com.dtp.apapter.brpc.server;

import com.baidu.cloud.starlight.api.common.URI;
import com.baidu.cloud.starlight.api.rpc.StarlightServer;
import com.baidu.cloud.starlight.api.rpc.threadpool.ThreadPoolFactory;
import com.baidu.cloud.starlight.api.transport.ServerPeer;
import com.baidu.cloud.starlight.core.rpc.DefaultStarlightServer;
import com.baidu.cloud.starlight.core.rpc.ServerProcessor;
import com.baidu.cloud.starlight.transport.netty.NettyServer;
import com.dtp.adapter.common.AbstractDtpAdapter;
import com.dtp.common.ApplicationContextHolder;
import com.dtp.common.dto.ExecutorWrapper;
import com.dtp.common.properties.DtpProperties;
import com.dtp.common.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Objects;

/**
 * StarlightServerDtpAdapter related
 *
 * @author yanhom
 * @since 1.1.0
 */
@Slf4j
public class StarlightServerDtpAdapter extends AbstractDtpAdapter {

    private static final String NAME = "brpcServerTp";

    private static final String URI_FIELD = "uri";

    private static final String SERVER_PEER_FIELD = "serverPeer";

    private static final String THREAD_POOL_FACTORY_FIELD = "threadPoolFactory";

    @Override
    public void refresh(DtpProperties dtpProperties) {
        refresh(NAME, dtpProperties.getBrpcTp(), dtpProperties.getPlatforms());
    }

    @Override
    protected void initialize() {
        super.initialize();

        val bean = ApplicationContextHolder.getBean(StarlightServer.class);
        if (!(bean instanceof DefaultStarlightServer)) {
            return;
        }
        val starlightServer = (DefaultStarlightServer) bean;
        val uri = (URI) ReflectionUtil.getFieldValue(DefaultStarlightServer.class,
                URI_FIELD, starlightServer);
        val serverPeer = (ServerPeer) ReflectionUtil.getFieldValue(DefaultStarlightServer.class,
                SERVER_PEER_FIELD, starlightServer);

        if (Objects.isNull(uri) || Objects.isNull(serverPeer) || !(serverPeer instanceof NettyServer)) {
            return;
        }
        val processor = (ServerProcessor) serverPeer.getProcessor();
        if (Objects.isNull(processor)) {
            return;
        }
        val threadPoolFactory = (ThreadPoolFactory) ReflectionUtil.getFieldValue(ServerProcessor.class,
                THREAD_POOL_FACTORY_FIELD, processor);
        if (Objects.isNull(threadPoolFactory)) {
            return;
        }
        String bizThreadPoolName = uri.getParameter("biz_thread_pool_name") + "#server";
        val executor = threadPoolFactory.defaultThreadPool();
        if (Objects.nonNull(executor)) {
            val executorWrapper = new ExecutorWrapper(bizThreadPoolName, executor);
            initNotifyItems(bizThreadPoolName, executorWrapper);
            executors.put(bizThreadPoolName, executorWrapper);
        }
        log.info("DynamicTp adapter, brpc server executors init end, executors: {}", executors);
    }
}
