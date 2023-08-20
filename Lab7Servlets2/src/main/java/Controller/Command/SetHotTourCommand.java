package Controller.Command;

import java.io.IOException;
import java.util.Enumeration;

import Model.DAO.TripDao;
import Model.Entity.Trip;
import Model.Exception.DaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SetHotTourCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String role = (String)request.getSession().getAttribute("role");
		if(role == null) {
			request.getRequestDispatcher("./TripsAppServlet&action=show-login");
			return;
		}
		if(!role.equals("admin")) {
			response.sendError(403);
			return;
		}
		
		if (request.getParameter("trip-id") == null) {
			response.sendError(500);
			return;
		}
		Integer tripId = Integer.parseInt(request.getParameter("trip-id"));
		
		TripDao tripDao = new TripDao();
		Trip trip = tripDao.getById(tripId);
		if (trip == null) {
			response.sendError(500);
			return;
		}
		trip.setHotTour(request.getParameter("isHotTour") != null);

		try {
			tripDao.update(trip);
		} catch (DaoException e) {
			response.sendError(500);
			return;
		}
		request.getRequestDispatcher("./TripsAppServlet?action=show-all-trips").forward(request, response);
	}

}
