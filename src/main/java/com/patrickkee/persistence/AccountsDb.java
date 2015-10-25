package com.patrickkee.persistence;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import com.patrickkee.model.impl.Account;

@XmlRootElement
public class AccountsDb {
	private static HashMap<String, Account> _accounts = new HashMap<String, Account>();

	/**
	 * Persists the account to in memory storage
	 * 
	 * @param account
	 */
	public synchronized static void persistAccount(Account account) {
		_accounts.putIfAbsent(account.getEmail(), account);
	}

	public static Account getAccountByEmail(String email) {
		Account acct = _accounts.get(email);
		
		if (null==acct) {
			return Account.newAccount().accountName("").lastName("").firstName("").email("");
		} else {
			return acct;
		}
	}
}
