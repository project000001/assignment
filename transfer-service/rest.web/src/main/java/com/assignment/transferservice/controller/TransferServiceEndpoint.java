package com.assignment.transferservice.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assignment.transferservice.entity.Account;
import com.assignment.transferservice.entity.Transfer;
import com.assignment.transferservice.exception.RepositoryException;
import com.assignment.transferservice.repository.ITransferServiceRepository;

/**
 * Controller Class for the required rest operations
 * 
 * @author hjain
 *
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TransferServiceEndpoint {

	private static final Logger LOG = LoggerFactory.getLogger(TransferServiceEndpoint.class);
	@Inject
	ITransferServiceRepository transferServiceRepository;

	/**
	 * Method to create user account
	 * 
	 * @param inName
	 *            : account holder name
	 * @param inBalance
	 *            : initial balance
	 * @return
	 */
	@GET
	@Path("/createAccount")
	@Produces("application/json")
	public Response createAccount(@QueryParam("name") String inName, @QueryParam("balance") String inBalance) {
		String errorMessage = "Error";
		try {
			float balance = Float.parseFloat(inBalance);
			if (balance < 100)
				throw new RepositoryException("amount less than minimal allowed balance");
			Account account = new Account(inName, balance);
			account = transferServiceRepository.createAccount(account);
			return Response.ok().entity(account).build();
		} catch (Exception e) {
			errorMessage = e.getMessage();
			LOG.debug(errorMessage);
		}
		return Response.status(412).entity(errorMessage).build();
	}

	/**
	 * Method to transfer amount from Debit account to Credit account
	 * 
	 * @param inDrAccountId
	 *            : debit account id
	 * @param inCrAccountId
	 *            : credit account id
	 * @param inAmount
	 *            : amount to be transfered
	 * @return
	 */
	@GET
	@Path("/transferAmount")
	@Produces("application/json")
	public Response transferAmount(@QueryParam("drAccountId") String inDrAccountId,
			@QueryParam("crAccountId") String inCrAccountId, @QueryParam("amount") String inAmount) {
		String errorMessage = "Error";
		try {
			float amount = Float.parseFloat(inAmount);
			long drAccountId = Long.parseLong(inDrAccountId);
			long crAccountId = Long.parseLong(inCrAccountId);
			Transfer transfer = transferServiceRepository.executeTransfer(drAccountId, crAccountId, amount);
			return Response.ok().entity(transfer).build();
		} catch (Exception e) {
			errorMessage = e.getMessage();
			LOG.debug(errorMessage);
		}
		return Response.status(412).entity(errorMessage).build();
	}

	/**
	 * Method to view account details
	 * 
	 * @param inAccountId
	 *            : account id
	 * @return Account
	 */
	@GET
	@Path("/viewAccount")
	@Produces("application/json")
	public Response viewAccount(@QueryParam("accountId") String inAccountId) {
		LOG.debug("inAccountId=" + inAccountId);
		String errorMessage = "Error";
		try {
			long accountId = Long.parseLong(inAccountId);
			Account account = transferServiceRepository.viewAccount(accountId);
			return Response.ok().entity(account).build();
		} catch (Exception e) {
			errorMessage = e.getMessage();
			LOG.debug(errorMessage);
		}
		return Response.status(412).entity(errorMessage).build();
	}

}
