package ru.meldren.weblab2.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.meldren.weblab2.entity.Constant;
import ru.meldren.weblab2.entity.Point;
import ru.meldren.weblab2.entity.Result;
import ru.meldren.weblab2.entity.ResultsListBean;

import java.io.IOException;
import java.io.Reader;
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
        Point point = parsePoint(req.getReader());
        if (point == null || !validate(point)) {
            resp.getWriter().println("Your input is not valid.");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        List<Result> results = ((ResultsListBean) req.getSession()
                .getAttribute(Constant.ATTRIBUTE_NAME)).getResults();
        results.add(new Result(
                results.size() + 1,
                point, isOnPlot(point),
                LocalDateTime.now(),
                (int) ((System.nanoTime() - time) / 1000)
        ));
        resp.getWriter().println(results.stream()
                .map(Result::toString)
                .collect(Collectors.joining()));
    }

    private Point parsePoint(Reader reader) {
        try {
            return new Gson().fromJson(reader, Point.class);
        } catch (Exception ex) {
            return null;
        }
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
