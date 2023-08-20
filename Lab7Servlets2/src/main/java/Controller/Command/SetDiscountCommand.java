package Controller.Command;

import java.io.IOException;

import Model.DAO.DiscountDao;
import Model.Exception.DaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SetDiscountCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String role = (String) request.getSession().getAttribute("role");
		if (role == null) {
			request.getRequestDispatcher("./TripsAppServlet&action=show-login");
			return;
		}
		if (!role.equals("admin")) {
			response.sendError(403);
			return;
		}

		DiscountDao discountDao = new DiscountDao();
		int discount = Integer.parseInt(request.getParameter("discount"));
		try {
			discountDao.setDiscount(discount);
		} catch (DaoException e) {
			response.sendError(500);
			return;
		}
		request.getRequestDispatcher("./TripsAppServlet?action=show-discount").forward(request, response);

	}

}
