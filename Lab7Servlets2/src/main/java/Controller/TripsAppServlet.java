package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Controller.Command.Command;
import Controller.Command.CreateOrderCommand;
import Controller.Command.GetHotToursOfTypeCommand;
import Controller.Command.LoginCommand;
import Controller.Command.LoginGuestCommand;
import Controller.Command.LogoutCommand;
import Controller.Command.OpenOrderFormCommand;
import Controller.Command.PayOrderCommand;
import Controller.Command.SetDiscountCommand;
import Controller.Command.SetHotTourCommand;
import Controller.Command.ShowAllTripsCommand;
import Controller.Command.ShowDiscountCommand;
import Controller.Command.ShowStartPageCommand;
import Controller.Command.ShowOrdersCommand;
import Model.DAO.TripDao;
import Model.Entity.Trip;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class TripsAppServlet
 */

public class TripsAppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TripsAppServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("------get " + request.getParameter("action"));
		System.out.println("get " + request.getParameter("action"));
		String uri = request.getRequestURI();
		String action = request.getParameter("action");
		Command command = null;
		if (action == null) {
			saveCookies(request, response);
			request.setAttribute("invalid-params", false);
			command = new ShowStartPageCommand();
		} else {
			switch (action) {
			case "show-all-trips":
				command = new ShowAllTripsCommand();
				break;
			case "show-user-orders":
				command = new ShowOrdersCommand();
				break;
			case "new-order":
				command = new OpenOrderFormCommand();
				break;
			case "show-login":
				command = new ShowStartPageCommand();
				break;
			case "login-guest":
				command = new LoginGuestCommand();
				break;
			case "logout":
				command = new LogoutCommand();
				break;
			case "show-all-orders":
				command = new ShowOrdersCommand();
				break;
			case "show-orders":
				command = new ShowOrdersCommand();
				break;
			case "show-discount":
				command = new ShowDiscountCommand();
				break;
			}
		}
		if (command != null) {
			command.run(request, response);
		} else {
			response.sendError(404);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("post " + request.getParameter("action"));
		if (request.getParameter("action") != null) {
			Command command = null;
			switch (request.getParameter("action")) {
			case "get-hot-tours-of-type":
				command = new GetHotToursOfTypeCommand();
				break;
			case "show-all-trips":
				command = new ShowAllTripsCommand();
				break;
			case "login":
				command = new LoginCommand();
				break;
			case "set-hot-tour":
				command = new SetHotTourCommand();
				break;
			case "create-order":
				command = new CreateOrderCommand();
				break;
			case "show-orders":
				command = new ShowOrdersCommand();
				break;
			case "show-login":
				command = new ShowStartPageCommand();
				break;
			case "pay-order":
				command = new PayOrderCommand();
				break;
			case "set-discount":
				command = new SetDiscountCommand();
				break;
			case "show-discount":
				command = new ShowDiscountCommand();
				break;
			}
			if (command != null) {
				command.run(request, response);
			} else {
				response.sendError(404);
				return;
			}

		}

	}

	private void saveCookies(HttpServletRequest request, HttpServletResponse response) {
		if(request.getSession().getAttribute("role")==null) {
			return;
		}
		Date currentDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.MM.yyyy-HH:mm");
		Cookie lastVisitTimeCookie = new Cookie("last_visit_time", simpleDateFormat.format(currentDate));
		Cookie visitsNumber = new Cookie("visits_number", "1");

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("visits_number")) {
				Integer visitsNum = Integer.parseInt(cookie.getValue());
				visitsNumber.setValue(Integer.toString(visitsNum + 1));
			}
		}
		System.out.println("visit number: " + visitsNumber.getValue());
		System.out.println("last visit date: " + lastVisitTimeCookie.getValue());
		response.addCookie(lastVisitTimeCookie);
		response.addCookie(visitsNumber);

	}

}
