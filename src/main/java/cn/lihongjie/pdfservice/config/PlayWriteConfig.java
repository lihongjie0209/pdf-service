package cn.lihongjie.pdfservice.config;

import com.microsoft.playwright.Browser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PlayWriteConfig {


    @Bean(name = "chromePoolConfig")
    @ConfigurationProperties(prefix = "playwrite.pool.chrome")
    @ConditionalOnProperty(name = "playwrite.pool.chrome.enabled", havingValue = "true", matchIfMissing = false)
    public GenericObjectPoolConfig chrome() {

        return new GenericObjectPoolConfig();

    }

    @Bean(name = "firefoxPoolConfig")
    @ConfigurationProperties(prefix = "playwrite.pool.firefox")
    @ConditionalOnProperty(name = "playwrite.pool.firefox.enabled", havingValue = "true", matchIfMissing = false)
    public GenericObjectPoolConfig firefox() {

        return new GenericObjectPoolConfig();

    }

    @Bean(name = "webkitPoolConfig")
    @ConfigurationProperties(prefix = "playwrite.pool.webkit")
    @ConditionalOnProperty(name = "playwrite.pool.webkit.enabled", havingValue = "true", matchIfMissing = false)
    public GenericObjectPoolConfig webkit() {

        return new GenericObjectPoolConfig();

    }


    @Bean(name = "chromeFactory")
    @ConditionalOnProperty(name = "playwrite.pool.chrome.enabled", havingValue = "true", matchIfMissing = false)
    public PooledObjectFactory<Browser> chromeFactory() {

        return new BrowserPooledObjectFactory(p -> p.chromium().launch());


    }

    @Bean(name = "firefoxFactory")
    @ConditionalOnProperty(name = "playwrite.pool.firefox.enabled", havingValue = "true", matchIfMissing = false)
    public PooledObjectFactory<Browser> firefoxFactory() {

        return new BrowserPooledObjectFactory(p -> p.firefox().launch());

    }

    @Bean(name = "webkitFactory")
    @ConditionalOnProperty(name = "playwrite.pool.webkit.enabled", havingValue = "true", matchIfMissing = false)
    public PooledObjectFactory<Browser> webkitFactory() {

        return new BrowserPooledObjectFactory(p -> p.webkit().launch());

    }


    @Bean(name = "chromePool")
    @ConditionalOnProperty(name = "playwrite.pool.chrome.enabled", havingValue = "true", matchIfMissing = false)
    public GenericObjectPool<Browser> chromePool(@Qualifier("chromePoolConfig") GenericObjectPoolConfig chromePoolConfig,
                                                 @Qualifier("chromeFactory") PooledObjectFactory<Browser> chromeFactory) {

        return new GenericObjectPool<>(chromeFactory, chromePoolConfig);
    }


    @Bean(name = "firefoxPool")
    @ConditionalOnProperty(name = "playwrite.pool.firefox.enabled", havingValue = "true", matchIfMissing = false)
    public GenericObjectPool<Browser> firefoxPool(@Qualifier("firefoxPoolConfig") GenericObjectPoolConfig firefoxPoolConfig,
                                                  @Qualifier("firefoxFactory") PooledObjectFactory<Browser> firefoxFactory) {

        return new GenericObjectPool<>(firefoxFactory, firefoxPoolConfig);
    }


    @Bean(name = "webkitPool")
    @ConditionalOnProperty(name = "playwrite.pool.webkit.enabled", havingValue = "true", matchIfMissing = false)

    public GenericObjectPool<Browser> webkitPool(@Qualifier("webkitPoolConfig") GenericObjectPoolConfig webkitPoolConfig,
                                                 @Qualifier("webkitFactory") PooledObjectFactory<Browser> webkitFactory) {

        return new GenericObjectPool<>(webkitFactory, webkitPoolConfig);
    }


}
