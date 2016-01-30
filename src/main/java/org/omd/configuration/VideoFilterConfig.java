package org.omd.configuration;

import org.omd.VideoFilterApp;
import org.omd.services.FilterChain;
import org.omd.services.FilterThread;
import org.opencv.core.Core;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by pbolle on 16.01.16.
 */

@Configuration
@ImportResource("classpath:filterChainConfig.xml")
public class VideoFilterConfig {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Bean
    public FilterThread filterThread(FilterChain filterChain){
        FilterThread filterThread = new FilterThread();
        filterThread.setFilterChain(filterChain);
        filterThread.start();
        return filterThread;
    }


}
