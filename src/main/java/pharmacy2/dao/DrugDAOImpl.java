package pharmacy2.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pharmacy2.entity.Drug;

@Repository
@Transactional
public class DrugDAOImpl implements DrugDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Drug getDrug(int drugId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Drug theDrug = currentSession.get(Drug.class, drugId);
		return theDrug;
	}

	@Override
	public void deleteDrug(int drugId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = currentSession.createQuery("delete from Drug where drugId=:drugId");
		theQuery.setParameter("drugId", drugId);
		theQuery.executeUpdate();
	}

	@Override
	public List<Drug> getAllDrugs() {
		Session currentSession = sessionFactory.getCurrentSession();		
		Query<Drug> theQuery = currentSession.createQuery("from Drug order by drugName",Drug.class); 		
		List<Drug> drugs = theQuery.getResultList(); 
		return drugs;
	}

	@Override
	public void addDrug(Drug theDrug) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(theDrug);
		
	}

	@Override
	public void updateDrug(Drug theDrug) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.update(theDrug);
		
	}

}
