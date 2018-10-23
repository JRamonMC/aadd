package umu.aadd;

import controlador.Controlador;
import modelo.dao.DAOException;


public class PruebasPersistencia {

	public static void main(String[] args) throws DAOException {

		Controlador controlador = Controlador.getUnicaInstancia();
		
		
		// Pruebas registro y login de un usuario
		System.out.println(controlador.loginUsuario("a", "a"));
		
		controlador.registrarUsuario("XA", "abde", "abde", "abde", "abde");
		
		System.out.println(controlador.loginUsuario("abde", "abde"));
	}
	
}