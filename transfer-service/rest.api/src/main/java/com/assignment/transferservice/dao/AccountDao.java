package com.assignment.transferservice.dao;

import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import com.assignment.transferservice.entity.Account;
import com.assignment.transferservice.exception.RepositoryException;

/**
 * Data Access Layer class to perform operation on ACCOUNT table.
 * 
 * @author hjain
 *
 */
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class AccountDao {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	private EntityManager em;

	/**
	 * @return
	 */
	private EntityManager getEntityManager() {
		if (Objects.isNull(em))
			this.em = entityManagerFactory.createEntityManager();
		return this.em;
	}

	/**
	 * @param account
	 * @return Created Account
	 */
	public Account create(Account account) {
		getEntityManager().persist(account);
		return account;
	}

	/**
	 * @param accountId
	 * @return Account
	 */
	public Account find(long accountId) {
		Account account;
		try {
			account = getEntityManager().find(Account.class, accountId);
		} catch (Exception e) {
			account = null;
		}
		return account;
	}

	/**
	 * Method to transfer an amount from one account record to other record
	 * 
	 * @param drAccount
	 *            : Account to be debited
	 * @param crAccount
	 *            : Account to be credited
	 * @param amount
	 *            : Amount to be debited
	 * @throws RepositoryException
	 */
	public void updateAccounts(Account drAccount, Account crAccount, float amount) throws RepositoryException {
		// Refresh and Attain lock on debit and credit account record
		getEntityManager().refresh(drAccount, LockModeType.PESSIMISTIC_WRITE);
		getEntityManager().refresh(crAccount, LockModeType.PESSIMISTIC_WRITE);
		// throw exception if either of record already deleted by some simultaneous
		// transaction
		if (drAccount == null || crAccount == null)
			throw new RepositoryException("Referred account no found");
		// throw exception if balance in debit account is now low due to simultaneous
		// transaction
		if (drAccount.getBalance() < amount)
			throw new RepositoryException("Account balance low");
		// transfer amount
		drAccount.setBalance(drAccount.getBalance() - amount);
		crAccount.setBalance(crAccount.getBalance() + amount);
		getEntityManager().persist(drAccount);
		getEntityManager().persist(drAccount);
	}

}
