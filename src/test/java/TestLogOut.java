import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.BankApp.User;

public class TestLogOut {

	@Test
	public void test() {
		User user = new User();
		user.setAge(23);
		user.setFirstName("Vikki");
		user.setLastName("Walker");
		user.setEmail("vwalker@gmail");
		user.setUserName("vswalker");
		user.setPassword("abc@123");
		user.setSsn(123221234);
		System.out.println(user.toString());
		
		User result = User.logout();
		
				
		assertTrue( result == null);
		
		
		
	}
	
	

}
