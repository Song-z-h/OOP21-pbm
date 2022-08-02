package test.model;



import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import main.model.account.Account;
import main.model.account.NotEnoughFundsException;
import main.model.account.SimpleAccount;

/**
 * JUnit basic evaluations to test with Gradle.
 */
public final class TestAccount {

	
	@Test void testBankOperations() {
		final Account acc1 = new SimpleAccount(0);
		final Account acc2 = new SimpleAccount(0);

		try {
			acc1.withdraw(100);
			fail("not enough fund to cash out");
		} catch (NotEnoughFundsException e) {
			assertNotNull(e);
		}

		acc2.deposit(2000);
		try {
			acc2.withdraw(200);
			if(acc2.getBalance() < 1800) {
				fail();
			}
		} catch (Exception e) {
			fail("It shouldn't arrive here.");
		}
		
	}
	
	
}
