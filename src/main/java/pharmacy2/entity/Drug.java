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
	private double drugCost;
	
	@Column(name="amount")
	private int drugAmount=0;
	
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

	
}
