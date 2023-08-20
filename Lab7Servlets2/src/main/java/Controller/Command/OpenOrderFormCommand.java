package Controller.Command;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OpenOrderFormCommand extends Command {

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
		
		request.getRequestDispatcher("/create-order.jsp").forward(request, response);
	}

}
