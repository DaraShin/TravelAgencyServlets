package Controller.Command;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowStartPageCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("role" +  request.getSession().getAttribute("role"));
		System.out.println("session id: " + request.getSession().getId());
		
		String role = (String)request.getSession().getAttribute("role");
		if(role == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/TripsAppServlet?action=show-all-trips").forward(request, response);
		}
	}
}
