package com.dtp.starter.http.controller;

import com.dtp.common.properties.DtpProperties;
import com.dtp.starter.http.refresh.HttpRefresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * HttpRefreshController related
 *
 * @author xuegao
 * @since 1.0.0
 */
@RestController
public class HttpRefreshController {

    private final HttpRefresher httpRefresher;

    @Autowired
    public HttpRefreshController(HttpRefresher httpRefresher) {
        this.httpRefresher = httpRefresher;
    }

    /**
     * httpRefresh
     *
     * @author xuegao
     * @date 2022/12/18 16:53
     */
    @PostMapping(path = "/dynamicTp/getDtpProperties")
    public DtpProperties getDtpProperties() {
        return httpRefresher.getDtpProperties();
    }

    /**
     * httpRefresh
     *
     * @author xuegao
     * @date 2022/12/18 16:53
     */
    @PostMapping(path = "/dynamicTp/httpRefresh")
    public void refresh(@RequestBody DtpProperties dtpProperties) {
        httpRefresher.httpRefresher(dtpProperties);
    }

}
