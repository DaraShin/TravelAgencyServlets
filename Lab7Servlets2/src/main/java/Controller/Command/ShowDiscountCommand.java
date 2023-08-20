package Controller.Command;

import java.io.IOException;
import java.util.List;

import Model.DAO.ClientDao;
import Model.DAO.DiscountDao;
import Model.DAO.TripDao;
import Model.Entity.Client;
import Model.Entity.Discount;
import Model.Entity.Trip;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowDiscountCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if(role == null) {
			request.getRequestDispatcher("./TripsAppServlet?show-login").forward(request, response);
			return;
		}
		if(!role.equals("admin")) {
			response.sendError(403);
			return;
		}
		
		/*DiscountDao discountDao = new DiscountDao();
		if(role.equals("client")) {
			ClientDao clientDao = new ClientDao();
			Client client = clientDao.getById((int)request.getSession().getAttribute("client-id"));
			if(client.getHasDiscount()) {
				request.setAttribute("discount", discountDao.getDiscount().getDiscountValue());
			}else {
				request.setAttribute("discount", 0);
			}
		}*/

		DiscountDao discountDao = new DiscountDao();
		request.setAttribute("discount", discountDao.getDiscount().getDiscountValue());

		request.getRequestDispatcher("/discount.jsp").forward(request, response);

	}

}
