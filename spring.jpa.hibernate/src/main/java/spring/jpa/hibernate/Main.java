package spring.jpa.hibernate;


import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.jpa.hibernate.config.Config;
import spring.jpa.hibernate.dao.ClienteDAO;
import spring.jpa.hibernate.entities.Cliente;

public class Main {


	
	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new  AnnotationConfigApplicationContext(Config.class);
		
		ClienteDAO daoCliente = context.getBean(ClienteDAO.class);
		Scanner tecla = new Scanner(System.in);
		
		int option=0; 
		do {
			System.out.println("<<<Menu Clientes>>>");
			System.out.println("1.- Agregar");
			System.out.println("2.- Eliminar por ID");
			System.out.println("3.- Mostrar por ID");
			System.out.println("4.- Mostrar todo");
			System.out.println("5.- Salir");
			System.out.print("Opcion: ");
			option = Integer.parseInt(tecla.next()); 
			switch (option) {
			case 1:
				tecla.nextLine();
				System.out.println("Nombre: ");
				String nombre = tecla.nextLine(); 
				System.out.println("Apellido: ");
				String apellido = tecla.nextLine();
				System.out.println("DNI: ");
				int dni = tecla.nextInt();
				if(nombre==null && apellido==null && dni < 0) System.out.println("Ingrese los campos correctamente");
				else{
					Cliente nuevoCliente = new Cliente(nombre, apellido, dni);
					daoCliente.addCliente(nuevoCliente);
				}	
				break;
			case 2: 
				tecla.nextLine();
				System.out.println("ID a Eliminar: ");
				Long id = tecla.nextLong();
				daoCliente.removeCliente(id);
				break; 
			case 3:
				System.out.println("Ingrese ID: ");
				id = tecla.nextLong();
				Cliente cliente = daoCliente.findByID(id);
				if(cliente != null){	
				System.out.println("Nombre: " + cliente.getNombre());
				System.out.println("Apellido: " + cliente.getApellido());
				System.out.println("DNI: " + cliente.getDni());
				}
				break; 
			case 4: 
				System.out.println("Listando todos los clientes: ");
				daoCliente.listAll();
				break;
			case 5: 
				System.exit(0);
			default:
				System.out.println(option + " no registrada");
				break;
			}
			
		} while (option !=5);
		tecla.close();
		context.close();
		
	}

}
