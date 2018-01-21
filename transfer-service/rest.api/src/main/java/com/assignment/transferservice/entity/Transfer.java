package com.assignment.transferservice.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class holding transfer details
 * @author hjain
 *
 */
@Entity
@Table(name = "TRANSFER", schema = "TRANSFERSERVICE")
public class Transfer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@SequenceGenerator(name = "SEQ_TRANSFER_PK_GENERATOR", sequenceName = "SEQ_TRANSFER_PK", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "SEQ_TRANSFER_PK_GENERATOR")
	@Column(name = "PK", unique = true, nullable = false, precision = 10, scale = 0)
	private long pk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DR_ACCOUNT_FK")
	private Account drAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CR_ACCOUNT_FK")
	private Account crAccount;

	@Column(name = "AMOUNT", nullable = false)
	private float amount;

	@Column(name = "TRANSACTION_DATE", nullable = true)
	private Date transferDate;

	public Transfer() {
		super();
	}

	/**
	 * @param drAccount
	 * @param crAccount
	 * @param amount
	 */
	public Transfer(Account drAccount, Account crAccount, float amount) {
		super();
		this.drAccount = drAccount;
		this.crAccount = crAccount;
		this.amount = amount;
		this.transferDate = new Date();
	}

	public long getPk() {
		return pk;
	}

	public void setPk(long pk) {
		this.pk = pk;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransfernDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public Account getDrAccount() {
		return drAccount;
	}

	public void setDrAccount(Account drAccount) {
		this.drAccount = drAccount;
	}

	public Account getCrAccount() {
		return crAccount;
	}

	public void setCrAccount(Account crAccount) {
		this.crAccount = crAccount;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("drAccount").append("='").append(getDrAccount()).append("' ");
		buffer.append("crAccount").append("='").append(getCrAccount()).append("' ");
		buffer.append("amount").append("='").append(getAmount()).append("' ");
		buffer.append("transactionDate").append("='").append(getTransferDate()).append("' ");
		buffer.append("]");

		return buffer.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Transfer))
			return false;
		Transfer castOther = (Transfer) other;

		return this.getDrAccount() == castOther.getDrAccount()
				|| this.getDrAccount().equals(castOther.getDrAccount())
						&& this.getCrAccount() == castOther.getCrAccount()
				|| this.getCrAccount().equals(castOther.getCrAccount()) && this.getAmount() == castOther.getAmount()
						&& (this.getTransferDate() == castOther.getTransferDate()
								|| (this.getTransferDate().equals(castOther.getTransferDate())));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getDrAccount().hashCode());
		result = 37 * result + (this.getCrAccount().hashCode());
		result = 37 * result + (Float.valueOf(getAmount()).hashCode());
		result = 37 * result + (this.getTransferDate().hashCode());
		return result;
	}
}
