package spring.jpa.hibernate.dao;


import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import spring.jpa.hibernate.entities.Cliente;

@Component
public class ClienteDAO {
	
	private EntityManagerFactory emf; 
	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory emf){
		this.emf = emf; 
	}
	private EntityManager em;
	
	
	public void addCliente(Cliente cliente){
		em = emf.createEntityManager(); 
		try {
			em.getTransaction().begin();
			em.persist(cliente);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		em.close();
		//emf.close();
	}
	
	public void removeCliente(Long id){
		em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Cliente deleteCliente = em.find(Cliente.class, id);
			if(deleteCliente!=null){
				em.remove(deleteCliente);
				em.flush();
				em.getTransaction().commit();
			}else System.out.println("ID: " + id + "no existe");
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		em.close();
	}
	
	public Cliente findByID(Long id){
		em = emf.createEntityManager(); 
		em.getTransaction().begin();
		Cliente findCliente = em.find(Cliente.class, id);
		if(findCliente==null) System.out.println("ID: " + id + "no existe");
		em.close();
		return findCliente;
	}
	
	public void listAll(){
		em = emf.createEntityManager();
		Query query = em.createQuery("FROM Cliente");
		List<Cliente> listClientes = (List<Cliente>) query.getResultList();
		for (Iterator<Cliente> iterator = listClientes.iterator(); iterator.hasNext();){
			Cliente cliente = (Cliente) iterator.next(); 
			System.out.println("Nombre: " + cliente.getNombre());
			System.out.println("Apellido: " + cliente.getApellido());
			System.out.println("DNI: " + cliente.getDni());
			System.out.println("\n");
		}
	}

}
