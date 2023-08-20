package Controller.Command;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class Command {
	public abstract void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
