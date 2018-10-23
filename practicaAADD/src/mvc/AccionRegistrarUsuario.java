package mvc;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.Controlador;
import modelo.Usuario;

public class AccionRegistrarUsuario implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response, ServletContext aplicacion) 
	{
		String usuario = request.getParameter("usuario");
		String clave = request.getParameter("pass");
		String email = request.getParameter("correo");
		String nombre = request.getParameter("nombre");
		String nif = request.getParameter("nif");
		
		Usuario usu = Controlador.getUnicaInstancia().registrarUsuario(usuario, clave, email, nombre, nif);
	
		if (usu == null)
			return "Error.jsp";
		else
			return "index.html";
	}

}
