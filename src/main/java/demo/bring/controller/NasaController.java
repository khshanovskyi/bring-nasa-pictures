package demo.bring.controller;

import demo.bring.AppContextFactory;
import demo.bring.service.PictureService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * Simple Web controller.
 */
@WebServlet("/nasa/picture/largest")
public class NasaController extends HttpServlet {
    private PictureService pictureService;

    @Override
    public void init() {
        if (Objects.isNull(pictureService)) {
            pictureService = AppContextFactory.getContext().getBean(PictureService.class);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var sol = req.getParameter("sol");
            if (Objects.nonNull(sol) && StringUtils.isNotEmpty(sol)) {
                resp.sendRedirect(pictureService.getLargestPictureUrl(sol));
            } else {
                resp.getWriter().println("<h1>Your request doesn't contains <b>sol</b> parameter.</h1>");
                resp.getWriter().println();

                var allFromDB = pictureService.getAllFromDB();
                if (!allFromDB.isEmpty()) {
                    resp.getWriter().println("<h1>So, you can choose among other big pictures urls that exists in DB:</h1>");
                    allFromDB.forEach(photo -> {
                        try {
                            resp.getWriter().println("<b><a href=\"" + photo.getUrl() + "\">" + "sol " + photo.getSol() + "</a></b>\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else {
                    resp.getWriter().println("<h1>Please, add to request a sol param, like '/nasa/picture/largest<b>?sol=15</b>'</h1>");
                }

            }
        } catch (Exception exception) {
            resp.getWriter().println("<h1>Oops... Something went wrong!</h1>");
            resp.getWriter().println(exception.getMessage());
        }

    }
}
