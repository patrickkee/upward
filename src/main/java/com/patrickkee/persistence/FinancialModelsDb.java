package com.patrickkee.persistence;

import java.util.HashMap;

import com.google.common.base.Optional;
import com.patrickkee.model.account.Account;

public class FinancialModelsDb {
	private static HashMap<String, Account> _accounts = new HashMap<String, Account>();

	/**
	 * Persists the account to in memory storage
	 * 
	 * @param account
	 */
	public synchronized static void persistAccount(Account account) {
		_accounts.putIfAbsent(account.getEmail(), account);
	}

	public static Optional<Account> getAccount(String email) {
		Account acct = _accounts.get(email);

		if (null == acct) {
			return Optional.absent();
		} else {
			return Optional.of(acct);
		}
	}
}
