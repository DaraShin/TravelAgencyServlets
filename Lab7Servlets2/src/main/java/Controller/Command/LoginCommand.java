package Controller.Command;

import java.io.IOException;

import Model.DAO.UserDao;
import Model.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand extends Command {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		if(login == null || login.isBlank() || password == null || password.isBlank()) {
			forwardOnInvalidParams(request, response);
			return;
		}
		
		UserDao userDao = new UserDao();
		User user = userDao.getByLogin(request.getParameter("login"));
		if (user == null) {
			forwardOnInvalidParams(request, response);
			return;
		} else {
			if(user.getPassword().equals(request.getParameter("password"))) {
				HttpSession session = request.getSession(true);
				session.setAttribute("role", user.getRole());
				if(user.getRole().equals("client")) {
					session.setAttribute("client-id", user.getClient().getClientId());
				}
				session.setAttribute("login", user.getLogin());
				request.getRequestDispatcher("./TripsAppServlet?action=show-all-trips").forward(request, response);
			}else {
				forwardOnInvalidParams(request, response);
				return;
			}
		}
	}
	
	private void forwardOnInvalidParams(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("invalid-params", true);
		request.getRequestDispatcher("./TripsAppServlet?action=show-login").forward(request, response);
	}

}
