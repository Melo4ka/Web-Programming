package ru.meldren.weblab4.dao;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import ru.meldren.weblab4.entity.Result;
import ru.meldren.weblab4.util.PlotUtil;
import ru.meldren.weblab4.util.TransactionUtil;

import java.util.List;

@Singleton
@Startup
@LocalBean
public class ResultRepository implements IRepository<Result> {

    @Override
    public void save(Result dto) {
        TransactionUtil.init(manager -> manager.persist(dto));
    }

    @Override
    public List<Result> getAll() {
        return TransactionUtil.initWithCallback(manager -> manager
                .createQuery("SELECT result FROM Result result", Result.class)
                .getResultList().stream()
                .peek(result -> result.setSuccessful(PlotUtil.isOnPlot(
                        result.getX(), result.getY(), result.getR()
                )))
                .toList());
    }

    @Override
    public void clear() {
        TransactionUtil.init(manager -> manager
                .createQuery("DELETE FROM Result")
                .executeUpdate());
    }
}
