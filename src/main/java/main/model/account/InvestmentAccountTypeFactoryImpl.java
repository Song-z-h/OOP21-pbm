package main.model.account;

import java.util.function.BiPredicate;

import com.google.common.base.Function;

public class InvestmentAccountTypeFactoryImpl implements InvestmentAccountTypeFactory {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public InvestmentAccount createForFree() {
		return createGeneratedAccount("", fee -> 0.0, s -> "", (state, amount) -> amount > 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public InvestmentAccount createWithOperationFees(final Function<Double, Double> fees) {
		return createGeneratedAccount("", fees, s -> "", (state, amount) -> fees.apply(amount) <= amount);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public InvestmentAccount createWithOperationLimitForFree(final int limit) {
		return createGeneratedAccount(0, fee -> 0.0, s -> s + 1, (state, amount) -> state < limit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InvestmentAccount createWithAmountLimitForFree(final double amountLimitPerOperation) {
		return createGeneratedAccount("", fee -> 0.0, s -> "", (state, amount) -> amount < amountLimitPerOperation);
	}

	private <E> InvestmentAccount createGeneratedAccount(final E initialState,
			final Function<Double, Double> operationFee, final Function<E, E> stateChanger,
			final BiPredicate<E, Double> predicate) {
		return new InvestmentAccount() {

			private Account account = new SimpleAccount(0);
			private double investedBalance;
			private E state = initialState;

			@Override
			public double getBalance() {
				return account.getBalance();
			}

			private boolean changeState(final Function<E, E> f) {
				state = f.apply(state);
				return true;
			}

			@Override
			public void withdraw(final double amount) {
				if (canWithdraw(amount)) {
					this.account.withdraw(amount);
					chargeOperationFees(amount);
				} else {
					throw new NotEnoughFundsException();
				}
			}

			private boolean canWithdraw(final double amount) {
				return (operationFee.apply(amount) + amount) <= this.getBalance() && predicate.test(state, amount)
						&& changeState(stateChanger);

			}

			@Override
			public void deposit(final double amount) {
				if (canDeposit(amount)) {
					this.account.deposit(amount);
					chargeOperationFees(amount);
				} else {
					throw new IllegalStateException();
				}
			}

			private void chargeOperationFees(final double amount) {
				this.account.withdraw(operationFee.apply(amount));
			}

			private boolean canDeposit(final double amount) {
				return predicate.test(state, amount);
			}

			private void increaseInvestedMoney(final double moneyBought) {
				this.investedBalance += moneyBought;

			}

			private void decreaseInvestedMoney(final double moneySold) {
				this.investedBalance -= moneySold;

			}

			@Override
			public double getInvestedBalance() {
				return this.investedBalance;
			}

			@Override
			public double getReturnInPercentage(final double netWorthInvested) {
				return this.getInvestedBalance() != 0 ? getReturn(netWorthInvested) / getInvestedBalance() : 0;
			}

			@Override
			public double getReturn(final double netWorthInvested) {
				return netWorthInvested - this.getInvestedBalance();
			}

			@Override
			public void invest(final double amounts) {
				withdraw(amounts);
				increaseInvestedMoney(amounts);
			}

			@Override
			public void cashout(final double amounts) {
				decreaseInvestedMoney(amounts);
				deposit(amounts);
			}

		};
	}

}
