package ru.meldren.weblab2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.meldren.weblab2.entity.Constant;
import ru.meldren.weblab2.entity.ResultsListBean;

import java.io.IOException;

/**
 * Created by Meldren on 30/09/2022
 */
@WebServlet(name = "ControllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createBeanIfNull(req.getSession());
        getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createBeanIfNull(req.getSession());
        resp.setContentType("text/html;charset=utf-8");
        getServletContext().getNamedDispatcher("AreaCheckServlet").forward(req, resp);
    }

    private void createBeanIfNull(HttpSession session) {
        if (session.getAttribute(Constant.ATTRIBUTE_NAME) != null) {
            return;
        }
        session.setAttribute(Constant.ATTRIBUTE_NAME, new ResultsListBean());
    }
}
