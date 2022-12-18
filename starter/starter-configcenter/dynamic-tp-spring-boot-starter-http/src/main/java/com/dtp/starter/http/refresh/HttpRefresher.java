package com.dtp.starter.http.refresh;

import com.dtp.common.properties.DtpProperties;
import com.dtp.core.refresh.AbstractRefresher;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * HttpRefresher related
 *
 * @author xuegao
 * @since 1.0.0
 */
@Slf4j
public class HttpRefresher extends AbstractRefresher {

    /**
     * requestRefresh
     *
     * @param dtpProperties: request json dtpProperties
     * @author xuegao
     * @date 2022/12/18 16:44
     */
    public void httpRefresher(final DtpProperties dtpProperties) {
        if (Objects.isNull(dtpProperties)) {
            log.warn("DynamicTp HttpRefresher, empty dtpProperties.");
            return;
        }

        try {
            doRefresh(dtpProperties);
        } catch (Exception e) {
            log.error("DynamicTp HttpRefresher, dtpProperties: {}", dtpProperties, e);
        }
    }
}
