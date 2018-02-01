package tallerjunit;

import static org.junit.Assert.*;

import java.sql.Time;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.AtLeast;
import org.mockito.internal.verification.Times;
import org.junit.Assert;

import com.everis.bootcamp.tallerjunit.Articulo;
import com.everis.bootcamp.tallerjunit.BaseDeDatosService;
import com.everis.bootcamp.tallerjunit.CarritoCompraService;

public class CarritoCompraServiceTest {

	CarritoCompraService service;
	BaseDeDatosService mock= Mockito.mock(BaseDeDatosService.class);		

	
	@Test
	public void test() {
		System.out.println("Este es el test 1");
		
	}
	
	@Test
	public void setUp() {
		System.out.println("Inicializamos en sevicio");
		service= new CarritoCompraService();
		mock= Mockito.mock(BaseDeDatosService.class);
		service.setBbddService(mock);

	}
	
	@After
	public void test3() {
		System.out.println("Este es el after");
		System.out.println("--------------------------------------------------");

	}
	
	@Test
	public void testAddArticulo() {
		System.out.println("Probando addArticulo");
		Assert.assertTrue((service.getArticulos().isEmpty()));
		service.addArticulo(new Articulo("Articulo 1", 10d));
		assertFalse(service.getArticulos().isEmpty());

	}
	
	@Test 
	public void testCestaLimpia() {
		System.out.println("Limpiando cesta");
		Assert.assertTrue((service.getArticulos().isEmpty()));
		service.addArticulo(new Articulo("Articulo 1", 10d));
		assertFalse(service.getArticulos().isEmpty());
		service.limpiarCesta();
		Assert.assertTrue((service.getArticulos().isEmpty()));

	}
	
	@Test
	public void testTotalPrice() {
		System.out.println("Precio Total");
		Assert.assertTrue((service.getArticulos().isEmpty()));
		service.addArticulo(new Articulo("Articulo 1", 10d));
		service.addArticulo(new Articulo("Articulo 2", 13d));
		assertEquals(service.totalPrice(), new Double (23));

		
	}
	
	@Test
	public void testCalcularDescuento() {
		System.out.println("Calculando descuento");
		assertEquals(service.calculadorDescuento(100d,10d),new Double (90));

		
	}
	
	
	
	@Test
	public void testTodoEnUno() {
		System.out.println("Limpiando cesta");
		Assert.assertTrue(service.getArticulos().isEmpty());
		System.out.println("Añadiendo Articulo");
		Assert.assertTrue((service.getArticulos().isEmpty()));
		service.addArticulo(new Articulo("Articulo 3", 10d));
		service.addArticulo(new Articulo("Articulo 4", 13d));
		assertFalse(service.getArticulos().isEmpty());
		System.out.println("Precio Total");
		assertEquals(service.totalPrice(), new Double (23));
		Assert.assertFalse((service.getArticulos().isEmpty()));
		service.limpiarCesta();
		

	}
	
	@Before
	public void before(){
		
		
		service=new CarritoCompraService();
		service.setBbddService(mock);
		System.out.println(mock);		
		
	}
	
	@Test
	public void buscarArtBd() {
		
		Mockito.when(mock.findArticuloById(1)).thenReturn(new Articulo("Camiseta", 10d));
		Assert.assertEquals(service.aplicarDescuento(1, 10d), new Double(9));
		
		Mockito.verify(mock, new Times(1)).findArticuloById(1);
	
	}
	
	@Test(expected=Exception.class)
	public void buscarError() {
		
		System.out.println("Probando errores");
		Mockito.when(mock.findArticuloById(0)).thenThrow(new Exception()); //Cambiando new Exception por Exception.class lanza la excepcion
		Double x =service.aplicarDescuento(0, 0d);
		System.out.println(x);
	
	}
	
	@Test
	public void testInsertar() {
		Articulo art= new Articulo("Articulo 2", 13.7);
		art.setId(9L);
		Mockito.when(mock.insertArticulo(art)).thenReturn(9);
		Integer i= service.insertar(art);

		
		Assert.assertEquals(service.getArticulos().get(0).getId().longValue(), i.longValue());
		Assert.assertTrue(service.getArticulos().contains(art));
	}
	

}