package com.patrickkee.model.event.type;

import java.math.BigDecimal;

public enum Operation {
	/**
	 * Add a value to an existing value
	 */
	ADD
	{
		@Override
		public BigDecimal ex(BigDecimal x1, BigDecimal x2) {
			return x1.add(x2);
		}
	},
	/**
	 * Replace an existing value by a given value
	 */
	MULTIPLY  {
		@Override
		public BigDecimal ex(BigDecimal x1, BigDecimal x2) {
			return x1.multiply(x2);
		}
	},
	/**
	 * Replace an existing value with a new value
	 */
	REPLACE
	{
		@Override
		public BigDecimal ex(BigDecimal x1, BigDecimal x2) {
			return x2;
		}
	};
	
	/**
	 * Executes the operation on the given operands
	 * @param x1 - the initial value
	 * @param x2 - the value to be applied
	 * @return
	 */
	public abstract BigDecimal ex(BigDecimal x1, BigDecimal x2);
}
