package ru.meldren.weblab2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Meldren on 02/10/2022
 */
@Data
@NoArgsConstructor
public class ResultsListBean implements Serializable {
    private final List<Result> results = new CopyOnWriteArrayList<>();

    @Override
    public String toString() {
        return results.stream()
                .map(Result::toString)
                .collect(Collectors.joining());
    }
}
