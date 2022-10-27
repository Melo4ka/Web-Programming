package ru.meldren.weblab2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.meldren.weblab2.database.InfluxDatabase;
import ru.meldren.weblab2.util.FileUtil;

import java.io.IOException;
import java.util.Properties;

import static ru.meldren.weblab2.Constant.*;

/**
 * Created by Meldren on 30/09/2022
 */
@WebServlet(name = "ControllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    public void init() {
        getServletContext().setAttribute(DATABASE_ATTRIBUTE, createDatabase());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        getServletContext().getNamedDispatcher("AreaCheckServlet").forward(req, resp);
    }

    public InfluxDatabase createDatabase() {
        Properties properties = FileUtil.getProperties(CONFIG_FILE_NAME);
        return new InfluxDatabase(
                properties.getProperty(CONFIG_HOST_PROPERTY),
                properties.getProperty(CONFIG_TOKEN_PROPERTY),
                properties.getProperty(CONFIG_ORGANIZATION_PROPERTY),
                properties.getProperty(CONFIG_BUCKET_PROPERTY)
        );
    }
}
