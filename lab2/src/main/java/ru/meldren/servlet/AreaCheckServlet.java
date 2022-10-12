package ru.meldren.servlet;

import com.google.gson.Gson;
import ru.meldren.entity.Constant;
import ru.meldren.entity.Point;
import ru.meldren.entity.Result;
import ru.meldren.entity.ResultsListBean;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Meldren on 02/10/2022
 */
@WebServlet(name = "AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long time = System.nanoTime();
        Point point = new Gson().fromJson(req.getReader(), Point.class);
        if (!validate(point)) {
            resp.getWriter().println("Your input is not valid.");
            return;
        }
        List<Result> results = ((ResultsListBean) req.getSession()
                .getAttribute(Constant.ATTRIBUTE_NAME)).getResults();
        Result result = new Result(
                results.size() + 1,
                point, isOnPlot(point),
                LocalDateTime.now(),
                (int) ((System.nanoTime() - time) / 1000)
        );
        results.add(result);
        resp.getWriter().println(results.stream()
                .map(Result::toString)
                .collect(Collectors.joining()));
    }

    private boolean validate(Point point) {
        double x = point.getX(), y = point.getY();
        return (point.isClicked() || ((x % 1 == 0 && x >= -3 && x <= 5) &&
                (y >= -3 && y <= 5))) &&
                List.of(1.0, 1.5, 2.0, 2.5, 3.0).contains(point.getR());
    }

    private boolean isOnPlot(Point point) {
        double x = point.getX(), y = point.getY(), r = point.getR();
        return (x >= 0 && x <= r / 2 && y >= 0 && y <= r && 2 * x + y <= r) || //triangle
                (x >= 0 && x <= r / 2 && y <= 0 && y >= -r) || //rectangle
                (x <= 0 && y <= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)); //circle
    }
}
