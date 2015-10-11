package com.patrickkee.persistence;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import com.patrickkee.model.Account;

@XmlRootElement
public class AccountsDb {
	private static HashMap<Integer, Account> _accounts = new HashMap<Integer, Account>();

	/**
	 * Persists the account to in memory storage
	 * 
	 * @param account
	 */
	public synchronized static void persistAccount(Account account) {
		_accounts.putIfAbsent(account.getAccountId(), account);
	}

	public static Account getAccountById(int accountId) {
		Account acct = _accounts.get(accountId);
		
		if (null==acct) {
			return Account.newAccount().accountName("").lastName("").firstName("").email("");
		} else {
			return acct;
		}
	}
}
