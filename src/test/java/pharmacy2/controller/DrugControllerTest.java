package pharmacy2.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import pharmacy2.entity.Drug;
import pharmacy2.exception.CustomRestExceptionHandler;
import pharmacy2.exception.DataExistsException;
import pharmacy2.exception.DataNotFoundException;
import pharmacy2.service.DrugService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DrugControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private DrugService drugService;
	
	@InjectMocks
	private DrugController drugController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(drugController)
					.setControllerAdvice(new CustomRestExceptionHandler())	
					.build();
	}
	
	@Test
	public void getAllSucces() throws Exception {
		List<Drug> drugs = new ArrayList<Drug>();
		Drug theDrug = new Drug("wit A", 1.2, 20, "50 tabs");
		theDrug.setDrugId(1);
		Drug theDrug2 = new Drug("wit D", 1.3, 30, "60 tabs");
		theDrug2.setDrugId(2);
		drugs.add(theDrug);
		drugs.add(theDrug2);
		
		when(drugService.getAllDrugs()).thenReturn(drugs);
		mockMvc.perform(get("/drugs"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$",hasSize(2)))
			.andExpect(jsonPath("$[0].drugId",is(1)))
			.andExpect(jsonPath("$[0].drugName",is("wit A")))
			.andExpect(jsonPath("$[0].drugCost",is(1.2)))
			.andExpect(jsonPath("$[0].drugAmount",is(20)))
			.andExpect(jsonPath("$[0].drugSize",is("50 tabs")))
			.andExpect(jsonPath("$[1].drugId",is(2)))
			.andExpect(jsonPath("$[1].drugName",is("wit D")))
			.andExpect(jsonPath("$[1].drugCost",is(1.3)))
			.andExpect(jsonPath("$[1].drugAmount",is(30)))
			.andExpect(jsonPath("$[1].drugSize",is("60 tabs")));
		verify(drugService,times(1)).getAllDrugs();
		verifyNoMoreInteractions(drugService);
	}
	
	@Test
	public void getAllNotFoundException() throws Exception{
		when(drugService.getAllDrugs()).thenThrow(new DataNotFoundException(""));
		mockMvc.perform(get("/drugs"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		verify(drugService,times(1)).getAllDrugs();
		verifyNoMoreInteractions(drugService);
	}
	
	@Test
	public void getAllMethodNotAllowedException() throws Exception{
	
		mockMvc.perform(post("/drugs"))
				.andExpect(status().isMethodNotAllowed())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	@Test
	public void getByIdSucces()  throws Exception {
		Drug theDrug = new Drug("wit A", 1.2, 20, "50 tabs");
		theDrug.setDrugId(1);
		
		when(drugService.getDrug(1)).thenReturn(theDrug);
		mockMvc.perform(get("/drugs/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.drugId",is(1)))
			.andExpect(jsonPath("$.drugName",is("wit A")))
			.andExpect(jsonPath("$.drugCost",is(1.2)))
			.andExpect(jsonPath("$.drugAmount",is(20)))
			.andExpect(jsonPath("$.drugSize",is("50 tabs")));
		verify(drugService,times(1)).getDrug(1);
		verifyNoMoreInteractions(drugService);
	}
	
	@Test
	public void getByIdMethodArgumentTypeMismatch() throws Exception{

		String message = "Failed to convert value of type 'java.lang.String' to required type 'int';" + 
				" nested exception is java.lang.NumberFormatException: For input string: \"a\"";
		List<String> errors = Arrays.asList("drugId should be of type int");
		
		mockMvc.perform(get("/drugs/a"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.status",is("BAD_REQUEST")))
				.andExpect(jsonPath("$.message",is(message)))
				.andExpect(jsonPath("$.errors",is(errors)));

	}

	@Test
	public void getByIdNotFoundException()  throws Exception {
		
		List<String> errors = Arrays.asList("404");
		
		when(drugService.getDrug(1)).thenThrow(new DataNotFoundException("Drug with Id = 1 not found"));
		mockMvc.perform(get("/drugs/1"))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.status",is("NOT_FOUND")))
			.andExpect(jsonPath("$.message",is("Drug with Id = 1 not found")))
			.andExpect(jsonPath("$.errors",is(errors)));
		verify(drugService,times(1)).getDrug(1);
		verifyNoMoreInteractions(drugService);
	}
	
	@Test
	public void createDrugSucces() throws Exception{
		Drug theDrug = new Drug("wit A", 1.2, 20, "50 tabs");
		theDrug.setDrugId(1);
		
		when(drugService.exists(theDrug)).thenReturn(false);
		doNothing().when(drugService).addDrug(theDrug);
		mockMvc.perform(post("/drugs/add")
					.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
					.content(asJsonString(theDrug)))
			.andExpect(status().isCreated());
		verify(drugService,times(1)).exists(theDrug);
		verify(drugService,times(1)).addDrug(theDrug);
		verifyNoMoreInteractions(drugService);
	}
	
	@Test
	public void createDrugAlreadyExists() throws Exception{
		
		Drug theDrug = new Drug("wit A", 1.2, 20, "50 tabs");
		theDrug.setDrugId(1);
		List<String> errors = Arrays.asList("409");
		
		when(drugService.exists(theDrug)).thenThrow(new DataExistsException("Drug with name wit A exists"));
		mockMvc.perform(post("/drugs/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(theDrug)))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.status",is("CONFLICT")))
			.andExpect(jsonPath("$.message",is("Drug with name wit A exists")))
			.andExpect(jsonPath("$.errors",is(errors)))
			.andExpect(status().isConflict());
		verify(drugService,times(1)).exists(theDrug);
		verifyNoMoreInteractions(drugService);
	}
	
	@Test
	public void createDrugHttpMessageNotReadable() throws Exception{
		
		Drug theDrug = new Drug("wit A", 1.2, 20, "50 tabs");
		theDrug.setDrugId(1);
		
		when(drugService.exists(theDrug)).thenReturn(false);
		doNothing().when(drugService).addDrug(theDrug);
		
		mockMvc.perform(post("/drugs/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(theDrug.toString()))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		verifyNoMoreInteractions(drugService);
	}
	/*
	 * Bad JSON property name exception test
	 * 
	 * 
	@Test
	public void createDrugInvalidAttributeInRequest() throws Exception{
		
		String theDrug = "{\r\n" + 
				"	\"drugIdaaaa\": 23,\r\n" + 
				"	\"drugName\": \"9999\",\r\n" + 
				"	\"drugCost\": 0.0,\r\n" + 
				"	\"drugAmount\": 22,\r\n" + 
				"	\"drugSize\": null\r\n" + 
				"}" ;
		
		//when(drugService.exists(theDrug)).thenReturn(false);
		//doNothing().when(drugService).addDrug(theDrug);
		
		mockMvc.perform(post("/drugs/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(theDrug))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		
		verifyNoMoreInteractions(drugService);
	}
	*/
	
	@Test
	public void updateDrugSucces() throws Exception {
		Drug theDrug = new Drug("wit A", 1.2, 20, "50 tabs");
		theDrug.setDrugId(1);
		
		doNothing().when(drugService).updateDrug(theDrug);
		mockMvc.perform(put("/drugs/update")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(asJsonString(theDrug)))
				.andExpect(status().isOk());
		verify(drugService,times(1)).updateDrug(theDrug);
	}
	
	@Test
	public void deleteDrugSucces() throws Exception{
		Drug theDrug = new Drug("wit A", 1.2, 20, "50 tabs");
		theDrug.setDrugId(1);
		
		doNothing().when(drugService).deleteDrug(theDrug.getDrugId());
		mockMvc.perform(delete("/drugs/delete/{drugId}", theDrug.getDrugId()))
				.andExpect(status().isOk());
		verify(drugService,times(1)).deleteDrug(theDrug.getDrugId());
		verifyNoMoreInteractions(drugService);		
	}
    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
}
