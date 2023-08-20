package Controller.Command;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("role");
		session.removeAttribute("client_id");
		
		Cookie lastVisitTimeCookie = new Cookie("last_visit_time", "");
		Cookie visitsNumber = new Cookie("visits_number", "1");
		response.addCookie(visitsNumber);
		
		session.invalidate();
		request.getRequestDispatcher("/TripsAppServlet?action=show-login").forward(request, response);
	}

}
