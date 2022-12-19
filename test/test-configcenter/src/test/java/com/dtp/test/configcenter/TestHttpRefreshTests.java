package com.dtp.test.configcenter;

import com.dtp.common.properties.DtpProperties;
import com.dtp.common.properties.SimpleTpProperties;
import com.dtp.common.properties.ThreadPoolProperties;
import com.dtp.starter.http.controller.HttpRefreshController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHttpRefreshTests {
    private static final Logger log = LoggerFactory.getLogger(TestHttpRefreshTests.class);

    @SpringBootConfiguration
    @ComponentScan({"com.dtp.test.configcenter", "com.dtp.starter.http"})
    public static class TestConfig {
    }

    @Autowired
    private HttpRefreshController refreshController;

    @Test
    public void httpRefresher() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DtpProperties dtpProperties = refreshController.getDtpProperties();
        log.info("[dtpProperties={}]", objectMapper.writeValueAsString(dtpProperties));
        dtpProperties.setEnabledCollect(false);
        SimpleTpProperties tomcatTp = dtpProperties.getTomcatTp();
        tomcatTp.setCorePoolSize(96);
        List<ThreadPoolProperties> executors = dtpProperties.getExecutors();
        ThreadPoolProperties threadPoolProperties = executors.get(0);
        threadPoolProperties.setCorePoolSize(96);
        refreshController.refresh(dtpProperties);

        DtpProperties dtpPropertiesV2 = refreshController.getDtpProperties();
        log.info("[dtpPropertiesV2={}]", objectMapper.writeValueAsString(dtpPropertiesV2));
    }

}
