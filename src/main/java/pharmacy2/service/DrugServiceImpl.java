package pharmacy2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pharmacy2.dao.DrugDAO;
import pharmacy2.entity.Drug;

@Service
public class DrugServiceImpl implements DrugService {

	@Autowired
	DrugDAO drugDao;
	
	@Override
	public List<Drug> getAllDrugs() {
		return drugDao.getAllDrugs();
	}

	@Override
	public Drug getDrug(int drugId) {
		return drugDao.getDrug(drugId);
	}

	@Override
	public void addDrug(Drug theDrug) {
		drugDao.addDrug(theDrug);
	}

	@Override
	public void updateDrug(Drug theDrug) {
		drugDao.updateDrug(theDrug);
	}

	@Override
	public void deleteDrug(int drugId) {
		drugDao.deleteDrug(drugId);
	}

	@Override
	public boolean exists(Drug theDrug) {
		return drugDao.exists(theDrug);
	}

}
