package com.patrickkee.model.event.type;

import java.math.BigDecimal;

public enum Operation {
	/**
	 * Add a value to an existing value
	 */
	ADD {
		@Override
		public BigDecimal ex(BigDecimal x1, BigDecimal x2) {
			return x1.add(x2);
		}

		@Override
		public OperationType getType() {
			return OperationType.ADDEND;
		}
	},
	SUBTRACT {
		@Override
		public BigDecimal ex(BigDecimal x1, BigDecimal x2) {
			return x1.subtract(x2);
		}

		@Override
		public OperationType getType() {
			return OperationType.ADDEND;
		}
	},
	/**
	 * Replace an existing value by a given value
	 */
	ADD_PERCENT {
		@Override
		public BigDecimal ex(BigDecimal x1, BigDecimal x2) {
			x2 = x2.add(BigDecimal.valueOf(1)); // Percents expressed as 0.005
												// (for 5%) should be converted
												// to 1.005 before application
			return x1.multiply(x2);
		}

		@Override
		public OperationType getType() {
			return OperationType.MULTIPLICAND;
		}
	},
	/**
	 * Replace an existing value by a given value
	 */
	SUBTRACT_PERCENT {
		@Override
		public BigDecimal ex(BigDecimal x1, BigDecimal x2) {
			x2 = BigDecimal.valueOf(1).subtract(x2); // Percents expressed as 0.005
													 // (for 5%) should be converted
													 // to 0.995 before application
			return x1.multiply(x2);
		}

		@Override
		public OperationType getType() {
			return OperationType.MULTIPLICAND;
		}
	},
	/**
	 * Replace an existing value with a new value
	 */
	REPLACE {
		@Override
		public BigDecimal ex(BigDecimal x1, BigDecimal x2) {
			return x2;
		}

		@Override
		public OperationType getType() {
			return OperationType.ADDEND;
		}
	};

	/**
	 * Executes the operation on the given operands
	 * 
	 * @param x1
	 *            - the initial value
	 * @param x2
	 *            - the value to be applied
	 * @return
	 */
	public abstract BigDecimal ex(BigDecimal x1, BigDecimal x2);
	public abstract OperationType getType(); 
}
