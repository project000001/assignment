package com.assignment.transferservice.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity Class holding User account data
 * 
 * @author hjain
 *
 */
@Entity
@Table(name = "ACCOUNT", schema = "TRANSFERSERVICE", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ACCOUNT_ID") })
public class Account implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@SequenceGenerator(name = "SEQ_ACCOUNT_PK_GENERATOR", sequenceName = "SEQ_ACCOUNT_PK", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "SEQ_ACCOUNT_PK_GENERATOR")
	@Column(name = "ACCOUNT_ID", unique = true, nullable = false, precision = 10, scale = 0)
	private long accountId;

	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	@Column(name = "BALANCE", nullable = false)
	private float balance;

	/**
	 * 
	 */
	public Account() {
		super();
	}

	/**
	 * @param name
	 * @param balance
	 */
	public Account(String name, float balance) {
		super();
		this.name = name;
		this.balance = balance;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("accountId").append("='").append(getAccountId()).append("' ");
		buffer.append("name").append("='").append(getName()).append("' ");
		buffer.append("balance").append("='").append(getBalance()).append("' ");
		buffer.append("]");

		return buffer.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Account))
			return false;
		Account castOther = (Account) other;

		return this.getAccountId() == castOther.getAccountId();
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (Long.valueOf(getAccountId()) == null ? 0 : Long.valueOf(getAccountId()).hashCode());
		result = 37 * result + (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result + (Float.valueOf(this.getBalance()).hashCode());
		return result;
	}

}
