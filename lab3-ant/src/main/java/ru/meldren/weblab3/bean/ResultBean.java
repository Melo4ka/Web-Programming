package ru.meldren.weblab3.bean;

import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.meldren.weblab3.entity.Coordinates;
import ru.meldren.weblab3.entity.Result;
import ru.meldren.weblab3.storage.ResultStorage;
import ru.meldren.weblab3.util.PlotUtil;

import java.io.Serializable;
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
public class ResultBean implements Serializable {

    @Inject
    ResultStorage storage;
    final List<Result> results = new CopyOnWriteArrayList<>();
    Coordinates current = new Coordinates();

    @PostConstruct
    public void init() {
        results.addAll(storage.getAllResults());
    }

    public void addResult() {
        Result result = new Result(
                current.getX(),
                current.getY(),
                current.getR(),
                PlotUtil.isOnPlot(current.getX(), current.getY(), current.getR()),
                System.currentTimeMillis()
        );

        results.add(result);
        storage.addResult(result);
    }

    public String parseResultsToJson() {
        return new GsonBuilder().create().toJson(results);
    }
}
