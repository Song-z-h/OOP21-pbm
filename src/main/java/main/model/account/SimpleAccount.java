package main.model.account;

public final class SimpleAccount extends BaseAccount {
	
	
	public SimpleAccount(final double amount) {
		super();
		this.deposit(amount);
	}


	@Override
	boolean checkWithdrawValidity(final double amount) {
		return this.getBalance() >= amount;
	}

	@Override
	boolean checkDepositValidity(final double amount) {
		return true;
	}
	

}
