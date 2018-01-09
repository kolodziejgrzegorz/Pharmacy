package pharmacy2.service;

import java.util.List;

import pharmacy2.entity.Drug;

public interface DrugService {

	public List<Drug> getAllDrugs();
	
	public Drug getDrug(int drugId);
	
	public void addDrug(Drug theDrug);
	
	public void updateDrug(Drug theDrug);
	
	public void deleteDrug(int drugId);
	
	public boolean exists(Drug theDrug);
}
