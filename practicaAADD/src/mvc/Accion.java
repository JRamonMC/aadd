package mvc;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Accion 
{	
	public String ejecutar (HttpServletRequest request, HttpServletResponse response, ServletContext aplicacion);
}
