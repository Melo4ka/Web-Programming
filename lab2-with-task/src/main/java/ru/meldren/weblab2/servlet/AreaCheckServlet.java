package ru.meldren.weblab2.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.meldren.weblab2.database.InfluxDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.meldren.weblab2.Constant.DATABASE_ATTRIBUTE;
import static ru.meldren.weblab2.Constant.ROWS_PER_PAGE;

@WebServlet(name = "AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        InfluxDatabase database = ((InfluxDatabase) getServletContext().getAttribute(DATABASE_ATTRIBUTE));
        long startTime = System.nanoTime();
        int page;
        try {
            JsonObject data = JsonParser.parseReader(req.getReader()).getAsJsonObject();
            page = data.get("page").getAsInt();
            if (data.size() > 1) {
                double x = data.get("x").getAsDouble(),
                        y = data.get("y").getAsDouble(),
                        r = data.get("r").getAsDouble();
                if (validate(x, y, r, data.get("clicked").getAsBoolean())) {
                    database.addElement(x, y, r, startTime);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        } catch (Exception ex) {
            resp.getWriter().println("Некорректные входные данные.");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long elementsCount = database.getElementsCount();
        int maxPage = (int) (elementsCount / ROWS_PER_PAGE) +
                elementsCount % ROWS_PER_PAGE != 0 ? 1 : 0;
        if (page <= 0 || page > maxPage) {
            page = maxPage;
        }
        resp.getWriter().println(gson.toJson(Map.of(
                "page", page,
                "output", database.getElementsByPage(page)
                        .stream()
                        .map(Objects::toString)
                        .collect(Collectors.joining())
        )));
    }

    private boolean validate(double x, double y, double r, boolean clicked) {
        return (clicked || ((x % 1 == 0 && x >= -3 && x <= 5) &&
                (y >= -3 && y <= 5))) &&
                List.of(1.0, 1.5, 2.0, 2.5, 3.0).contains(r);
    }
}
