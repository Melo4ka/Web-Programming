package ru.meldren.weblab3.bean;

import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.meldren.weblab3.entity.Coordinates;
import ru.meldren.weblab3.entity.Result;
import ru.meldren.weblab3.storage.ResultStorage;
import ru.meldren.weblab3.util.MBeanRegistryUtil;
import ru.meldren.weblab3.util.PlotUtil;

import javax.management.*;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Meldren on 31/10/2022
 */
@Named
@ApplicationScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultBean extends NotificationBroadcasterSupport implements ResultMXBean, Serializable {

    @Inject
    ResultStorage storage;
    final List<Result> results = new CopyOnWriteArrayList<>();
    Coordinates current = new Coordinates();
    int sequenceNumber = 1;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        results.addAll(storage.getAllResults());
        MBeanRegistryUtil.registerBean(this, "main");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }

    @Override
    public int getTotalResultsNumber() {
        return results.size();
    }

    @Override
    public int getUnsuccessfulResultsNumber() {
        return (int) results.stream()
                .filter(result -> !result.isSuccessful())
                .count();
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
        String name = AttributeChangeNotification.class.getName();
        String description = "The point is outside of area.";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }

    public void addResult() {
        boolean successful = PlotUtil.isOnPlot(current.getX(), current.getY(), current.getR());
        Result result = new Result(
                current.getX(),
                current.getY(),
                current.getR(),
                successful,
                System.currentTimeMillis()
        );

        results.add(result);
        storage.addResult(result);

        if (!successful) {
            Notification notification = new Notification(
                    "Outside of area",
                    getClass().getSimpleName(),
                    sequenceNumber++,
                    String.format("The point %s is outside of area.", result.getCoordinates())
            );
            sendNotification(notification);
        }
    }

    public String parseResultsToJson() {
        return new GsonBuilder().create().toJson(results);
    }
}
