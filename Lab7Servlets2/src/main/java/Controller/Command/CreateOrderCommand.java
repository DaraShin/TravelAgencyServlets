package Controller.Command;

import java.io.IOException;

import Model.DAO.ClientDao;
import Model.DAO.OrderDao;
import Model.DAO.TripDao;
import Model.Entity.Order;
import Model.Exception.DaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CreateOrderCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String role = (String)request.getSession().getAttribute("role");
		if(role == null) {
			request.getRequestDispatcher("./TripsAppServlet&action=show-login");
			return;
		}
		if(!role.equals("client")) {
			response.sendError(403);
			return;
		}
		
		OrderDao orderDao = new OrderDao();
		TripDao tripDao = new TripDao();
		ClientDao clientDao = new ClientDao();
		Order order = new Order();
		order.setPaid(request.getParameter("is-paid") != null);
		order.setClient(clientDao.getById((int)request.getSession().getAttribute("client-id")));
		order.setTrip(tripDao.getById(Integer.parseInt(request.getParameter("trip-id"))));
		try {
			orderDao.create(order);
		} catch (DaoException e) {
			response.sendError(500);
			return;
		}
		request.getRequestDispatcher("/TripsAppServlet?action=show-orders").forward(request, response);
	}
}
