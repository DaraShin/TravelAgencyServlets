package Controller.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.DAO.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowOrdersCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("role") == null) {
			request.getRequestDispatcher("/TripsAppServlet?show-login").forward(request, response);
			return;
		}

		OrderDao orderDao = new OrderDao();
		List<Model.Entity.Order> ordersList = new ArrayList<>();
		if (session.getAttribute("role").equals("admin")) {
			ordersList = orderDao.getAll();
		} else if (session.getAttribute("role").equals("client")) {
			ordersList = orderDao.getOrdersOfClient((int) session.getAttribute("client-id"));
		} else {
			response.sendError(403);
			return;
		}
		request.setAttribute("orders-list", ordersList);
		request.getRequestDispatcher("/order-list.jsp").forward(request, response);
	}

}
