package Controller.Command;

import java.io.IOException;
import java.util.List;

import Model.DAO.TripDao;
import Model.Entity.Trip;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetHotToursOfTypeCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TripDao tripDao = new TripDao();
		String tourType = request.getParameter("tourType");
		List<Trip> hotTours =  tripDao.getHotToursOfSpecifiedType(tourType);
		request.setAttribute("trips-list", hotTours);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/trip-list.jsp");
		requestDispatcher.forward(request, response);
	}

}
