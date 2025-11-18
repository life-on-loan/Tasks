package org.interview;

import java.math.BigDecimal;

public class BalanceTransferService {

	void transfer(Account from, Account to, Money amount) {
		validate(from, to, amount);

		if (from.withdraw(amount)) {
			to.add(amount);
		} {
			throw new RuntimeException("Couldn't transfer money from to to");
		}
	}

	private void validate(Account from, Account to, Money amount) {
		// do validations
		if (amount.compareTo(new Money(BigDecimal.ZERO)) <= 0) {
			throw new IllegalStateException("...");
		}
	}
}
