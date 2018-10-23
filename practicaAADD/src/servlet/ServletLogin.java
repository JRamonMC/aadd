package servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controlador.Controlador;

@WebServlet(urlPatterns = { "/ServletLogin", "/index.html" }, initParams = {
		@WebInitParam(name = "admin", value = "admin") })
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String claveAdmin;

	public ServletLogin() {
		super();
	}

	protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
			throws ServletException, IOException {
		respuesta.setContentType("text/html");
		PrintWriter out = respuesta.getWriter();
		// Obtenemos la ruta fiÌ�sica el fichero del formulario
		String pathFichero = getServletConfig().getServletContext().getRealPath("login.html");
		BufferedReader br = new BufferedReader(new FileReader(pathFichero));
		String linea;
		while ((linea = br.readLine()) != null)
			out.println(linea);
		br.close();
	}

	protected void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
			throws ServletException, IOException {
		
		String usuario = peticion.getParameter("usuario");
		String clave = peticion.getParameter("clave");

		HttpSession sesion = peticion.getSession();
		// Una vez recuperada
		Integer numIntentos;
		numIntentos = (Integer) sesion.getAttribute("numIntentos");
		// Si ya estaba, se intento logear anteriormente

		if (numIntentos != null) {
			numIntentos++;
		} else {
			numIntentos = 1;
		}
		sesion.setAttribute("numIntentos", numIntentos);
		if (numIntentos > 3) {
			respuesta.sendError(500, "Numero de intentos superado");
			return;
		}

		// if (Controlador.getUnicaInstancia().login(usuario, clave)){
		String claveUsuario = getServletConfig().getInitParameter(usuario);
		boolean error = true;

		// Voy a recuperar del contexto la tabla de usuarios
		@SuppressWarnings("unchecked")
		HashMap<String, Date> usuarios = (HashMap<String, Date>) getServletConfig().getServletContext()
				.getAttribute("usuarios");

		if (usuarios == null) {
			usuarios = new HashMap<String, Date>();
			getServletConfig().getServletContext().setAttribute("usuarios", usuario);
		}

		if (usuarios.get(usuario) != null) {
			respuesta.sendError(500, "USUARIO YA LOGEADO EN OTRA SESION");
			return;
		}

		if (Controlador.getUnicaInstancia().loginUsuario(usuario, clave)) {
			// if (clave != null && clave.equals(claveUsuario)) {
			sesion.setAttribute("numIntentos", numIntentos);
			sesion.setAttribute("usuario_actual", usuario);
			error = false;
		}

		if (!error) {

			usuarios.put(usuario, new Date());
			RequestDispatcher rd = peticion.getRequestDispatcher("ServletMain");
			peticion.setAttribute("usuario_actual", usuario);
			rd.forward(peticion, respuesta);
		} else {
			respuesta.sendRedirect("Error.jsp");
		}
	}

}
