package com.assignment.transferservice.dao;

import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import com.assignment.transferservice.entity.Transfer;

/**
 * * Data Access Layer class to perform operations on TRANSFER table.
 * 
 * @author hjain
 *
 */
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class TransferDao {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	private EntityManager em;

	/**
	 * @return EntityManager
	 */
	private EntityManager getEntityManager() {
		if (Objects.isNull(em))
			this.em = entityManagerFactory.createEntityManager();
		return this.em;
	}

	/**
	 * @param transaction
	 * @return Transfer Record
	 */
	public Transfer create(Transfer transaction) {
		getEntityManager().persist(transaction);
		return transaction;
	}

}
