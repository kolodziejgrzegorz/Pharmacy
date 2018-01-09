package pharmacy2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pharmacy2.entity.Drug;
import pharmacy2.exception.DataExistsException;
import pharmacy2.exception.DataNotFoundException;
import pharmacy2.service.DrugService;

@RestController
public class DrugController {

	@Autowired
	private DrugService drugService;

	@RequestMapping(value = { "/drugs" }, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Drug>> getAllDrugs() {
		List<Drug> drugs = drugService.getAllDrugs();
		
		if(drugs==null || drugs.isEmpty()) {
			throw new  DataNotFoundException("Drugs list not found");
		}
		return new ResponseEntity<List<Drug>>(drugs, HttpStatus.OK);
	}

	@GetMapping(value = "/drugs/{drugId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Drug> getDrug(@PathVariable("drugId") int drugId) {
		Drug drug = drugService.getDrug(drugId); 
		if( drug == null ) {
			throw new  DataNotFoundException("Drug with Id = " + drugId + " not found ");
		}
		return new ResponseEntity<Drug>(drug,HttpStatus.OK);
	}

	@RequestMapping(value = "/drugs/add", method = RequestMethod.POST, consumes = {	MediaType.APPLICATION_JSON_VALUE },
					produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addDrug(@RequestBody Drug theDrug) {
		if(drugService.exists(theDrug)) {
			throw new DataExistsException("Drug with name " + theDrug.getDrugName() + " already exists");
		}
		
		StringBuilder builder = new StringBuilder();
		if(theDrug.getDrugId() == 0) builder.append(" drugId");
		if(theDrug.getDrugName() == null) builder.append(" drugName");
		if(theDrug.getDrugCost() <0) builder.append(" drugCost");
		if(theDrug.getDrugAmount() <0) builder.append(" drugAmount");
		if(theDrug.getDrugSize() == null) builder.append(" drugSize");
		
		if(builder.length()>0) throw new DataNotFoundException("Missing property" + builder.toString());
		if(theDrug.getDrugName() == null) throw new DataNotFoundException("Missing property drugName");
		
		drugService.addDrug(theDrug);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/drugs/update", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE },
					produces = { MediaType.APPLICATION_JSON_VALUE } )
	public ResponseEntity<Void> updateDrug(@RequestBody Drug theDrug) {
		
		StringBuilder builder = new StringBuilder();
		if(theDrug.getDrugId() == 0) builder.append(" drugId");
		if(theDrug.getDrugName() == null) builder.append(" drugName");
		if(theDrug.getDrugCost() <0) builder.append(" drugCost");
		if(theDrug.getDrugAmount() <0) builder.append(" drugAmount");
		if(theDrug.getDrugSize() == null) builder.append(" drugSize");
		
		if(builder.length()>0) throw new DataNotFoundException("Missing property" + builder.toString());		
		if(!drugService.exists(theDrug)) throw new DataExistsException("Not found drug with id = " + theDrug.getDrugId()); 
		
		drugService.updateDrug(theDrug);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/drugs/delete/{drugId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDrug(@PathVariable("drugId") int drugId) {
		drugService.deleteDrug(drugId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
