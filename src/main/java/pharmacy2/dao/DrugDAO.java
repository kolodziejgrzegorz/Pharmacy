package pharmacy2.dao;

import java.util.List;

import pharmacy2.entity.Drug;

public interface DrugDAO {

	public List<Drug> getAllDrugs();
	
	public Drug getDrug(int drugId);
	
	public void addDrug(Drug theDrug);
	
	public void updateDrug(Drug theDrug);
	
	public void deleteDrug(int drugId);
}
