package cn.lihongjie.pdfservice.service;

import cn.lihongjie.pdfservice.dto.GenFileResponse;
import cn.lihongjie.pdfservice.dto.PdfRequest;
import cn.lihongjie.pdfservice.dto.ScreenShotRequest;
import cn.lihongjie.pdfservice.enums.Metrics;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
@Slf4j
public class PlayWriteService {


    @Autowired(required = false)
    @org.springframework.beans.factory.annotation.Qualifier("chromePool")
    public GenericObjectPool<Browser> chromePool;

    @Autowired(required = false)
    @org.springframework.beans.factory.annotation.Qualifier("firefoxPool")
    public GenericObjectPool<Browser> firefoxPool;

    @Autowired(required = false)
    @org.springframework.beans.factory.annotation.Qualifier("webkitPool")
    public GenericObjectPool<Browser> webkitPool;


    public <T> T withBrowser(String browserType, Function<Browser, T> function, int borrowMaxWaitMillis) {
        if (browserType == null || browserType.isEmpty()) {
            return null;
        }

        GenericObjectPool<Browser> pool = null;
        switch (browserType.toLowerCase()) {
            case "chrome":

                pool = chromePool;
                break;
            case "firefox":
                pool = firefoxPool;
                break;
            case "webkit":
                pool = webkitPool;
                break;
            default:

                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }


        Browser browser = null;
        try {


            browser = pool.borrowObject(borrowMaxWaitMillis);

        } catch (Exception e) {
            log.error("Error borrowing browser from pool: {}", e.getMessage(), e);
            throw new RuntimeException("Error borrowing browser from pool", e);
        }

        try {
            return function.apply(browser);
        } finally {
            if (browser != null) {
                pool.returnObject(browser);
            }
        }

    }


    @SneakyThrows
    public GenFileResponse getScreenshot(ScreenShotRequest request) {


        long startTime = System.currentTimeMillis();
        Map<String, Long> metrics = new java.util.HashMap<>();

        InputStream is = withBrowser(request.getBrowserType() == null ? "chrome" : request.getBrowserType(), (browser) -> {

            updateMetric(metrics, Metrics.GET_BROWSER, startTime);


            Page page;
            long previousTime = System.currentTimeMillis();
            Optional<Page> matchPage = browser.contexts().stream().flatMap(x -> x.pages().stream()).filter(y -> y.viewportSize() != null && y.viewportSize().width == request.getScreenWidth() && y.viewportSize().height == request.getScreenHeight()).findFirst();


            if (matchPage.isPresent()) {

                log.info("found match page width {} height {}", matchPage.get().viewportSize().width, matchPage.get().viewportSize().height);

                page = matchPage.get();

            } else {


                Browser.NewContextOptions options = new Browser.NewContextOptions();
                if (request.getScreenWidth() != null && request.getScreenHeight() != null) {
                    options.setViewportSize(request.getScreenWidth(), request.getScreenHeight());
                }


                BrowserContext browserContext = browser.newContext(options);

                updateMetric(metrics, Metrics.NEW_CONTEXT, previousTime);

                previousTime = System.currentTimeMillis();


                if (request.getTimeout() != null) {

                    browserContext.setDefaultTimeout(request.getTimeout());
                }

                page = browserContext.newPage();

            }
            updateMetric(metrics, Metrics.NEW_PAGE, previousTime);


            previousTime = System.currentTimeMillis();

            if (request.getTimeout() != null) {
                page.setDefaultTimeout(request.getTimeout());
            }


            if (request.getUrl() != null && !request.getUrl().isEmpty()) {
                page.navigate(request.getUrl());
            } else if (request.getHtml() != null && !request.getHtml().isEmpty()) {
                page.setContent(request.getHtml());
            } else {
                throw new IllegalArgumentException("url or html is required");

            }

            updateMetric(metrics, Metrics.LOAD_PAGE, previousTime);

            previousTime = System.currentTimeMillis();


            byte[] bytes = null;
            if (StringUtils.isNotBlank(request.getSelector())) {

                bytes = page.locator(request.getSelector()).screenshot(new Locator.ScreenshotOptions());
            } else {
                bytes = page.screenshot(new Page.ScreenshotOptions());

            }

            updateMetric(metrics, Metrics.SCREENSHOT, previousTime);

            previousTime = System.currentTimeMillis();


            updateMetric(metrics, Metrics.CLEAN_UP, previousTime);

            return new ByteArrayInputStream(bytes);

        }, ObjectUtils.defaultIfNull(request.getTimeout(), 10000));


        GenFileResponse genFileResponse = new GenFileResponse();
        genFileResponse.setBase64File(Base64.getEncoder().encodeToString(is.readAllBytes()));

        updateMetric(metrics, Metrics.TOTAL_TIME, startTime);
        genFileResponse.setMetrics(metrics);


        return genFileResponse;

    }


    @SneakyThrows
    public GenFileResponse getPdf(PdfRequest request) {


        long startTime = System.currentTimeMillis();
        Map<String, Long> metrics = new java.util.HashMap<>();

        InputStream is = withBrowser(request.getBrowserType() == null ? "chrome" : request.getBrowserType(), (browser) -> {

            updateMetric(metrics, Metrics.GET_BROWSER, startTime);

            Page page;
            long previousTime = System.currentTimeMillis();
            Optional<Page> matchPage = browser.contexts().stream().flatMap(x -> x.pages().stream()).filter(y -> y.viewportSize() != null && y.viewportSize().width == request.getScreenWidth() && y.viewportSize().height == request.getScreenHeight()).findFirst();


            if (matchPage.isPresent()) {

                log.info("found match page width {} height {}", matchPage.get().viewportSize().width, matchPage.get().viewportSize().height);

                page = matchPage.get();

            } else {


                Browser.NewContextOptions options = new Browser.NewContextOptions();
                if (request.getScreenWidth() != null && request.getScreenHeight() != null) {
                    options.setViewportSize(request.getScreenWidth(), request.getScreenHeight());
                }


                BrowserContext browserContext = browser.newContext(options);

                updateMetric(metrics, Metrics.NEW_CONTEXT, previousTime);

                previousTime = System.currentTimeMillis();


                if (request.getTimeout() != null) {

                    browserContext.setDefaultTimeout(request.getTimeout());
                }

                page = browserContext.newPage();

            }
            updateMetric(metrics, Metrics.NEW_PAGE, previousTime);

            previousTime = System.currentTimeMillis();

            if (request.getTimeout() != null) {
                page.setDefaultTimeout(request.getTimeout());
            }


            if (request.getUrl() != null && !request.getUrl().isEmpty()) {
                page.navigate(request.getUrl());
            } else if (request.getHtml() != null && !request.getHtml().isEmpty()) {
                page.setContent(request.getHtml());
            } else {
                throw new IllegalArgumentException("url or html is required");

            }

            updateMetric(metrics, Metrics.LOAD_PAGE, previousTime);

            previousTime = System.currentTimeMillis();


            Page.PdfOptions pdfOptions = new Page.PdfOptions();

            request.updatePdfOptions(pdfOptions);


            byte[] bytes = page.pdf(pdfOptions);


            updateMetric(metrics, Metrics.GET_PDF, previousTime);

            previousTime = System.currentTimeMillis();


            updateMetric(metrics, Metrics.CLEAN_UP, previousTime);

            return new ByteArrayInputStream(bytes);

        }, ObjectUtils.defaultIfNull(request.getTimeout(), 10000));


        GenFileResponse genFileResponse = new GenFileResponse();
        genFileResponse.setBase64File(Base64.getEncoder().encodeToString(is.readAllBytes()));

        updateMetric(metrics, Metrics.TOTAL_TIME, startTime);
        genFileResponse.setMetrics(metrics);


        return genFileResponse;

    }

    private static void updateMetric(Map<String, Long> metrics, Metrics getBrowser, long prevTime) {
        metrics.put(getBrowser.name(), System.currentTimeMillis() - prevTime);
    }


}
