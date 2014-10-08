package com.hashedin.artcollective.service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import com.hashedin.artcollective.BaseIntegrationTest;
import com.hashedin.artcollective.controller.ProductsAPI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class ProductsAPITest extends BaseIntegrationTest {

	@Autowired
	private ProductsAPI productsAPI;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(productsAPI).build();
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
    public void testForAPICalls() throws Exception {
		
		mockMvc.perform(get("/api/collections"))
        .andExpect(status().isOk());
		
		mockMvc.perform(get("/manage/priceRange/getall"))
        .andExpect(status().isOk());
		
		mockMvc.perform(get("/api/artworks/search?limit=10&offset=0"))
        .andExpect(status().isOk());
		
		mockMvc.perform(get("/api/artworks/search?limit=10&offset=0"
				+ "&subjects=1234&styles=65432&medium=fineart&orientation=landscape&colors=ffffff,fff000"))
        .andExpect(status().isOk());
		
		mockMvc.perform(get("/api/frames?frameLength=12&frameBreadth=17.5&mountThickness=1&frameThickness=1"))
        .andExpect(status().isOk());
		
		mockMvc.perform(get("/manage/priceRange/add?id=1&title=low&lowerRange=2500&upperRange=5000"))
        .andExpect(status().isOk());
		
        
    }
	
}
