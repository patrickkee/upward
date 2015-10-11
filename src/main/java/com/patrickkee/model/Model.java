package com.patrickkee.model;

import java.math.BigDecimal;
import java.util.Date;

public interface Model {
	public int getModelId();
	public BigDecimal getValue(Date date);
}
