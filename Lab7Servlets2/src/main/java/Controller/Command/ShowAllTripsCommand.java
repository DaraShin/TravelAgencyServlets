package Controller.Command;

import java.io.IOException;
import java.util.List;

import Model.DAO.TripDao;
import Model.Entity.Trip;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowAllTripsCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("role") == null) {
			request.getRequestDispatcher("/TripsAppServlet?show-login").forward(request, response);
			return;
		}
		
		TripDao daoTrip = new TripDao();
		List<Trip> allTrips = daoTrip.getAll();
		request.setAttribute("trips-list", allTrips);

		request.getRequestDispatcher("/trip-list.jsp").forward(request, response);
	}

}
