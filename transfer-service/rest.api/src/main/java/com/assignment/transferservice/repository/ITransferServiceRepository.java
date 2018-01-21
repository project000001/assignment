package com.assignment.transferservice.repository;

import com.assignment.transferservice.entity.Account;
import com.assignment.transferservice.entity.Transfer;
import com.assignment.transferservice.exception.RepositoryException;

/**
 * Service class to perform various operations on Accounts 
 * @author hjain
 *
 */
public interface ITransferServiceRepository {

	/**
	 * Method to create User account
	 * @param account
	 * @return Account
	 * @throws RepositoryException
	 */
	Account createAccount(Account account) throws RepositoryException;

	/**
	 * Method to execute transfer among accounts
	 * @param drAccountId
	 * @param crAccountId
	 * @param amount
	 * @return Transfer
	 * @throws RepositoryException
	 */
	Transfer executeTransfer(long drAccountId, long crAccountId, float amount) throws RepositoryException;

	/**
	 * Method to retrieve account data
	 * @param accountId
	 * @return Account
	 * @throws RepositoryException
	 */
	Account viewAccount(long accountId) throws RepositoryException;

}
