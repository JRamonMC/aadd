package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//DEBERIA RECUPERAR EL MAPA 
@WebServlet("/ServletCerrar")
public class ServletCerrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletCerrar() {
        super();
    }

	
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			HashMap<String, Date> usuarios = (HashMap<String, Date>) getServletConfig().getServletContext()
					.getAttribute("usuarios");
			
			if(usuarios!=null){
				usuarios.remove(request.getSession().getAttribute("usuario_actual"));
			}
			request.getSession().invalidate();
			
			
			
			response.sendRedirect("index.html");
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
