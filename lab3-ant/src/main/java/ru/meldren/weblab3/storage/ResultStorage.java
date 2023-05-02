package ru.meldren.weblab3.storage;

import jakarta.enterprise.context.ApplicationScoped;
import ru.meldren.weblab3.entity.Result;
import ru.meldren.weblab3.util.TransactionUtil;

import java.util.List;

@ApplicationScoped
public class ResultStorage {

    public List<Result> getAllResults() {
        return TransactionUtil.executeWithCallback(manager -> manager
                .createQuery("SELECT result FROM Result result", Result.class)
                .getResultList());
    }

    public void addResult(Result result) {
        TransactionUtil.execute(manager -> manager.persist(result));
    }
}
