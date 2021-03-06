package nz.co.senanque.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import nz.co.senanque.pizzaorder.generated.Customer;
import nz.co.senanque.pizzaorder.generated.Order;
import nz.co.senanque.pizzaorder.generated.Pizza;
import nz.co.senanque.rules.RulesPlugin;
import nz.co.senanque.validationengine.ValidationEngine;
import nz.co.senanque.validationengine.ValidationException;
import nz.co.senanque.validationengine.ValidationSession;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;

public class SpringConfigurationTest {
	
	private static ValidationEngine validationEngine;

	@BeforeClass
	public static void setup() {
        @SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        validationEngine = (ValidationEngine) context.getBean("validationEngine");		
	}
	@AfterClass
	public static void takeDown() {
		//
	}
	@Test
	public void testPizza() {
        assertEquals(validationEngine.getIdentifier(),"my-identifier");
        ValidationSession validationSession = validationEngine.createSession();
        Pizza pizza = new Pizza();
        validationSession.bind(pizza);
        boolean exceptionFound = false;
        try 
        {
            pizza.setTestDouble(50D);
        } 
        catch (ValidationException e) 
        {
            exceptionFound = true;
        }
        assertTrue(exceptionFound);
        pizza.setTestDouble(500D);
        pizza.setTopping("Turkish");
        exceptionFound = false;
        try 
        {
            pizza.setSize("Large");
        } 
        catch (ValidationException e) 
        {
            exceptionFound = true;
        }
        assertTrue(exceptionFound);
        pizza.setSize("TrulyVast");
        @SuppressWarnings("unused")
		RulesPlugin rulesPlugin = validationEngine.getPlugin(RulesPlugin.class);
        validationSession.close();
    }

	@Test
	public void testCustomer() {
        assertEquals(validationEngine.getIdentifier(),"my-identifier");
        ValidationSession validationSession = validationEngine.createSession();
        Customer customer = new Customer();
        validationSession.bind(customer);
        customer.setName("fred");
        assertEquals("yes, that's okay",customer.getPassword());
        assertEquals(12D,customer.getWeight(),0);
        validationSession.close();
    }
	@Test
	public void testDuplicateCustomer() {
        assertEquals(validationEngine.getIdentifier(),"my-identifier");
        ValidationSession validationSession = validationEngine.createSession();
        Customer customer = new Customer();
        validationSession.bind(customer);
        customer.setName("fred");
        assertEquals("yes, that's okay",customer.getPassword());
        assertEquals(12D,customer.getWeight(),0);
        String t = validationSession.getStats();
        validationSession.bind(customer);
        String tt = validationSession.getStats();
        assertEquals(t,tt);
        validationSession.close();
    }
	@Test
	public void testOrderItemBind() {
        assertEquals(validationEngine.getIdentifier(),"my-identifier");
        ValidationSession validationSession = validationEngine.createSession();
        Order order = new Order();
        validationSession.bind(order);
        
        Pizza pizza = new Pizza();
        validationSession.bind(pizza);
        pizza.setSize("Large");

        String t = validationSession.getStats();
        order.getOrderItems().add(pizza);
        String tt = validationSession.getStats();
        assertEquals(t,tt);
        assertEquals(20D,order.getAmount(),0);
        validationSession.close();
    }
	@Test
	public void testResourceBundles() {
		MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(validationEngine.getMessageSource());
		Locale locale = new Locale("en");
		String m = messageSourceAccessor.getMessage("nz.co.senanque.validationengine.class.not.recognised",locale);
		assertEquals("Bind failed. Class not recognised: {0}",m);
		m = messageSourceAccessor.getMessage("nz.co.senanque.rules.divide.by.zero",locale);
		assertEquals("Divide by zero error",m);
		m = messageSourceAccessor.getMessage("nz.co.senanque.pizzaorder.generated.R22",locale);
		assertEquals("try out external functions",m);
		m = messageSourceAccessor.getMessage("shopping.cart.status",locale);
		assertEquals("{0} items in cart",m);
	}
}
