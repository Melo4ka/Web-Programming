package ru.meldren.weblab3.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.faces.bean.ManagedBean;
import jakarta.inject.Inject;
import ru.meldren.weblab3.entity.Result;
import ru.meldren.weblab3.storage.ResultStorage;
import ru.meldren.weblab3.util.MBeanRegistryUtil;

import java.util.List;

/**
 * Created by Meldren on 03/05/2023
 */
@ApplicationScoped
public class ResultHitsPercentBean implements ResultHitsPercentMXBean {

    @Inject
    private ResultStorage storage;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.registerBean(this, "percent");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }

    @Override
    public double getHitsPercent() {
        List<Result> results = storage.getAllResults();
        if (results.isEmpty()) {
            return 0;
        }
        int totalHitsNumber = results.size();
        long unsuccessfulHitsNumber = results.stream()
                .filter(result -> !result.isSuccessful())
                .count();
        return (double) unsuccessfulHitsNumber / totalHitsNumber * 100;
    }
}
