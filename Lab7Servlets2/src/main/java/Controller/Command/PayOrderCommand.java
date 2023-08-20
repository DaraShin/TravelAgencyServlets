package Controller.Command;

import java.io.IOException;

import Model.DAO.OrderDao;
import Model.Entity.Order;
import Model.Exception.DaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PayOrderCommand extends Command {

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
		Integer orderId = Integer.parseInt(request.getParameter("order-id"));
		Order order = orderDao.getById(orderId);
		order.setPaid(true);
		try {
			orderDao.update(order);
		} catch (DaoException e) {
			response.sendError(500);
			return;
		}
		new ShowOrdersCommand().run(request, response);
	}

}
