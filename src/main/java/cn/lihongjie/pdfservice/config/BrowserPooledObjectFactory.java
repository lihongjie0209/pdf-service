package cn.lihongjie.pdfservice.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Slf4j
public class BrowserPooledObjectFactory implements PooledObjectFactory<Browser> {

    private Function<Playwright, Browser> browserFactory;

    private Map<Browser , Playwright> browserMap = new ConcurrentHashMap<>();


    public BrowserPooledObjectFactory(Function<Playwright, Browser> browserFactory) {
        this.browserFactory = browserFactory;
    }

    @Override
    public void activateObject(PooledObject<Browser> p) throws Exception {

        log.debug("activateObject: {}", p.getObject().toString());
    }

    @Override
    public void destroyObject(PooledObject<Browser> p) throws Exception {

        Browser object = p.getObject();
        object.close();

        log.info("destroyObject: {}", object.toString());

        Playwright playwright = browserMap.get(object);
        if (playwright != null) {
            playwright.close();
            browserMap.remove(object);
            log.info("destroyObject: close playwright: {}", playwright.toString());
        }
    }

    @Override
    public PooledObject<Browser> makeObject() throws Exception {

        Playwright playwright = Playwright.create();
        log.info("makeObject: create playwright {}", playwright.toString());
        Browser browser = browserFactory.apply(playwright);

        log.info("makeObject: create browser {}", browser.toString());

        browserMap.put(browser, playwright);
        return new DefaultPooledObject<>(browser);
    }

    @Override
    public void passivateObject(PooledObject<Browser> p) throws Exception {

        log.debug("passivateObject: {}", p.getObject().toString());



    }

    @Override
    public boolean validateObject(PooledObject<Browser> p) {

        Browser object = p.getObject();
        try {
            log.debug("validateObject: {}", object.toString());
            return object.isConnected();
        } catch (Exception e) {
            log.info("validateObject error: {}", e.getMessage());
            return false;
        }
    }
};
