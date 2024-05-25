package org.example.demotest;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/calculateDistance")
public class DistanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            double lat1 = Double.parseDouble(request.getParameter("lat1"));
            double lon1 = Double.parseDouble(request.getParameter("lon1"));
            double lat2 = Double.parseDouble(request.getParameter("lat2"));
            double lon2 = Double.parseDouble(request.getParameter("lon2"));

            double R = 6371e3; // Radius of the Earth in meters
            double phi1 = Math.toRadians(lat1);
            double phi2 = Math.toRadians(lat2);
            double deltaPhi = Math.toRadians(lat2 - lat1);
            double deltaLambda = Math.toRadians(lon2 - lon1);

            double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                    Math.cos(phi1) * Math.cos(phi2) *
                            Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double distance = R * c;

            request.setAttribute("distance", distance);
            request.getRequestDispatcher("processing.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Please enter valid numbers for coordinates.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    public void destroy() {
        // Cleanup code if necessary
    }
}
