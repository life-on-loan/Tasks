package org.interview;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class Account {
	private final AccountId id;
	private final AtomicReference<Money> balance = new AtomicReference<>(new Money(BigDecimal.ZERO));

	// could be a builder
	public Account(AccountId id) {
		this.id = id;
	}

	public Money getBalance() {
		return balance.get();
	}

	public boolean withdraw(Money amount) {
		Money currentBalance;
		do {
			currentBalance = this.getBalance();
			if (currentBalance.compareTo(amount) < 0) {
				return false;
			}
		} while (!balance.compareAndSet(currentBalance, currentBalance.subtract(amount)));
		return true;
	}

	public void add(Money amount) {
		this.balance.updateAndGet(b -> b.add(amount));
	}

	public record AccountId(Long id) {}
}
