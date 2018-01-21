package com.assignment.transferservice.repository.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.LockTimeoutException;

import com.assignment.transferservice.dao.AccountDao;
import com.assignment.transferservice.dao.TransferDao;
import com.assignment.transferservice.entity.Account;
import com.assignment.transferservice.entity.Transfer;
import com.assignment.transferservice.exception.RepositoryException;
import com.assignment.transferservice.repository.ITransferServiceRepository;

/**
 * 
 * Implementation Service class to perform various operations on Accounts
 * 
 * @author hjain
 *
 */
@ApplicationScoped
public class TransferServiceRepository implements ITransferServiceRepository {

	@Inject
	AccountDao accountDao;

	@Inject
	TransferDao transactionDao;

	@Override
	public Account createAccount(Account account) throws RepositoryException {
		return accountDao.create(account);
	}

	@Override
	public Transfer executeTransfer(long drAccountId, long crAccountId, float amount) throws RepositoryException {
		// Retrive Debit and credit account
		Account drAccount = accountDao.find(drAccountId);
		Account crAccount = accountDao.find(crAccountId);
		// throw exception if corresponding accounts not found
		if (drAccount == null || crAccount == null)
			throw new RepositoryException("Referred account no found");
		// throw exception if debit account have less balance
		if (drAccount.getBalance() < amount)
			throw new RepositoryException("Account balance low");
		int executionCount = 0;
		boolean failed = false;
		// Retry 3 times in case database updation fails as another transaction in
		// process on one the debit or credit account
		while (executionCount == 0 || failed && executionCount < 3) {
			executionCount++;
			try {
				accountDao.updateAccounts(drAccount, crAccount, amount);
			} catch (LockTimeoutException e) {
				failed = true;
			} catch (RepositoryException e) {
				throw e;
			} catch (Exception e) {
				throw new RepositoryException("Transaction failed due to technical issue. Please Try again");
			}
		}
		Transfer transaction = new Transfer(drAccount, crAccount, amount);
		return transactionDao.create(transaction);
	}

	@Override
	public Account viewAccount(long accountId) throws RepositoryException {
		Account account = accountDao.find(accountId);
		if (account == null) {
			throw new RepositoryException("Referred account no found");
		}
		return account;
	}
}
