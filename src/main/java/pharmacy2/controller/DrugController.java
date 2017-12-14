package pharmacy2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pharmacy2.entity.Drug;
import pharmacy2.service.DrugServiceImpl;

@RestController
public class DrugController {

	@Autowired
	private DrugServiceImpl drugService;
		
	 @GetMapping(value="/drugs/{drugId}",produces={MediaType.APPLICATION_JSON_VALUE})
	 public Drug getDrug(@PathVariable("drugId") int drugId) {
		 return drugService.getDrug(drugId);
	 }
	 
	 @RequestMapping(value="/drugs/add",method=RequestMethod.POST,
			 		consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	 public Drug addDrug(@RequestBody Drug theDrug) {
		 drugService.addDrug(theDrug);
		 return theDrug;
	 }
	 
	 @RequestMapping(value="/drugs/update",method=RequestMethod.POST,
			 		consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	 public  Drug updateDrug(@RequestBody Drug theDrug) {
		 drugService.updateDrug(theDrug);
		 return theDrug;
	 }
	 
	 @GetMapping(value= {"/drugs"},produces={MediaType.APPLICATION_JSON_VALUE})
	 public List <Drug> getAllDrugs(){
		 return drugService.getAllDrugs();
	 }
	 
	 @GetMapping(value="/drugs/delete/{drugId}",produces={MediaType.APPLICATION_JSON_VALUE})
	 public String deleteDrug(@PathVariable("drugId") int drugId) {
		 drugService.deleteDrug(drugId);
		 //return drugService.getAllDrugs();
		 return "Successful delete drug " + drugId ;
	 }
	 
	 
}
