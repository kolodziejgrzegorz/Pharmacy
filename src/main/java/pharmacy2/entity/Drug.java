package pharmacy2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="drug")
public class Drug {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int drugId;
	
	@Column(name="name")
	private String drugName;
	
	@Column(name="cost")
	private double drugCost=-1.00;
	
	@Column(name="amount")
	private int drugAmount=-1;
	
	@Column(name="size")
	private String drugSize;
	
	private Drug() {}
	
	public int getDrugId() {
		return drugId;
	}
	public void setDrugId(int drugId) {
		this.drugId = drugId;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public double getDrugCost() {
		return drugCost;
	}
	public void setDrugCost(double drugCost) {
		this.drugCost = drugCost;
	}

	public int getDrugAmount() {
		return drugAmount;
	}

	public void setDrugAmount(int drugAmount) {
		this.drugAmount = drugAmount;
	}

	public String getDrugSize() {
		return drugSize;
	}

	public void setDrugSize(String drugSize) {
		this.drugSize = drugSize;
	}

	public Drug(String drugName, double drugCost, int drugAmount, String drugSize) {
		this.drugName = drugName;
		this.drugCost = drugCost;
		this.drugAmount = drugAmount;
		this.drugSize = drugSize;
	}

	@Override
	public String toString() {
		return "Drug [drugId=" + drugId + ", drugName=" + drugName + ", drugCost=" + drugCost + ", drugAmount=" + drugAmount
				+ ", drugSize=" + drugSize + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + drugId;
		result = prime * result + ((drugName == null) ? 0 : drugName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drug other = (Drug) obj;
		if (drugId != other.drugId)
			return false;
		if (drugName == null) {
			if (other.drugName != null)
				return false;
		} else if (!drugName.equals(other.drugName))
			return false;
		return true;
	}

	
}
