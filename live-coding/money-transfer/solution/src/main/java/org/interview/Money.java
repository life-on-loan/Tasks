package org.interview;

import java.math.BigDecimal;

public record Money(BigDecimal amount) implements Comparable<Money> {

	public Money(BigDecimal amount) {
		// validation
		this.amount = amount;
	}

	@Override
	public int compareTo(Money o) {
		return amount.compareTo(o.amount);
	}

	public Money add(Money amount) {
		return new Money(this.amount.add(amount.amount));
	}

	public Money subtract(Money amount) {
		return new Money(this.amount.subtract(amount.amount));
	}
}
