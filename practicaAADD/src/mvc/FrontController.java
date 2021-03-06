package mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("*.event")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontController() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		procesa(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		procesa(request, response);
	}
	
	private void procesa(HttpServletRequest request, HttpServletResponse response)
	{
		// Obtiene la ruta f�sica de la aplicaci�n para que el
		// objeto PeticionHelper pueda acceder a sus ficheros de
		// propiedades
		String dirAplicacion = getServletConfig().getServletContext().getRealPath("/");
		
		// Utiliza una clase Helper para analizar la acci�n a realizar
		PeticionHelper peticionHelper = new PeticionHelper(request,dirAplicacion);
		
		// Obtiene la acci�n a ejecutar asociada a la petici�n
		Accion acc = peticionHelper.getAccion();
		
		// Recupera el objeto aplicaci�n para la acci�n
		ServletContext aplicacion = getServletConfig().getServletContext();
		
		// Ejecuta la acci�n y obtiene la vista
		String vista = acc.ejecutar(request, response, aplicacion);
		
		RequestDispatcher rd = request.getRequestDispatcher(vista);
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
