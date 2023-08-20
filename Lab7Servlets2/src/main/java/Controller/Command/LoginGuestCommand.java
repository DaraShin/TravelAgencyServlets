package Controller.Command;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginGuestCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("role", "guest");
		session.setAttribute("login", "guest");
		request.getRequestDispatcher("./TripsAppServlet?action=show-all-trips").forward(request, response);
	}

}
