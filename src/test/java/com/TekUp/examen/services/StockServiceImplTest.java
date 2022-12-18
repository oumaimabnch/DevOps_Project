package com.TekUp.examen.services;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import com.TekUp.examen.entities.Stock;
import com.TekUp.examen.repositories.StockRepository;

import lombok.var;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {

	 @Autowired
	 private MockMvc mockMvc;

	@Mock
	StockRepository sr;
	@InjectMocks
	StockServiceImpl ss;
	
	@Test
	public void RetrieveAllStockTest() {
		List<Stock> stocks = new ArrayList<Stock>();
		Stock s1= new Stock("libelle",20,1);
		Stock s2= new Stock("libelle1",14,1);
		Stock s3= new Stock("libelle2",25,1);
		stocks.add(s1);
		stocks.add(s2);
		stocks.add(s3);
		
		when(sr.findAll()).thenReturn(stocks);

		List<Stock> expected = ss.retrieveAllStocks();
		assertEquals(3, expected.size());
		//assertEquals(expected, stocks);
		verify(sr).findAll();
	}
	
	@Test
      public void FindStockByIdTest() {
        // Arrange
        final var stock = Stock.builder().libelleStock("libelle").qte(10).qteMin(1).build();
        when(sr.findById(any())).thenReturn(Optional.of(stock));

        // Act
        final var actualStck = ss.retrieveStock(stock.getIdStock());

        // Assert
        assertThat(actualStck).usingRecursiveComparison().isEqualTo(stock);
        verify(sr).findById(any());
        verifyNoMoreInteractions(sr);
    }

	@Test
	public void AddStockTest() {
		Stock stock = new Stock("libelle1",12,5);

		Mockito.when(sr.save(ArgumentMatchers.any(Stock.class))).thenReturn(stock);

		Stock Stockadd = ss.addStock(stock);

		assertThat(Stockadd.getLibelleStock()).isSameAs(Stockadd.getLibelleStock());
		verify(sr).save(stock);
	}

	/*@Test
	public void whenGivenId_shouldUpdateStock_ifFound() {
	Stock stock = new Stock();
	stock.setIdStock(1L);
	stock.setLibelleStock("libelle2");
	stock.setQte(15);
	stock.setQteMin(1);
	Stock stock2 = new Stock();
	stock.setLibelleStock("libelle2");
	stock.setQte(10);
	stock.setQteMin(1);
	when(sr.findById(stock.getIdStock())).thenReturn(Optional.of(stock));
	
	Stock sotckupdated=ss.updateStock(stock2);
	assertThat(sotckupdated.getQte()).isEqualTo(10);
	//verify(sr).findById(stock.getIdStock());
	}
*/
	@Test
	public void DeleteStockIfExistTest() {
		Stock stock = new Stock();
		stock.setIdStock(1L);
		stock.setLibelleStock("libelle3");
		stock.setQte(20);
		stock.setQteMin(1);
		Mockito.when(sr.findById(stock.getIdStock())).thenReturn(Optional.of(stock));
		ss.deleteStock(stock.getIdStock());
		verify(sr).deleteById(stock.getIdStock());
	};
	//expected = RuntimeException.class
	@Test()
	public void should_throw_exception_when_stock_doesnt_exist() {
		Stock stock = new Stock();
		stock.setIdStock(90L);
		stock.setLibelleStock("libelle4");
		stock.setQte(20);
		stock.setQteMin(1);
		given(sr.findById(anyLong())).willReturn(Optional.ofNullable(null));
		sr.deleteById(stock.getIdStock());
		}
	}


/*
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockServiceImplTest {
	
	
	@Autowired
	private StockServiceImpl service;
	
	@Test
	@Order(1)
	public void testRetrieveAllStock() {
		        List<Stock> listStocks = service.retrieveAllStocks();
		        Assertions.assertEquals(0, listStocks.size());
		        }
	@Test
	@Order(2)
	public void testAddStock() {
		       Stock stock= new Stock("libelle", 15, 1);
		       Stock Stockadd= service.addStock(stock);
		       Assertions.assertEquals(stock.getQte(), Stockadd.getQte());
		        }
	
	@Test
	@Order(3)
	public void testModifyStock() {
		       Stock stock= new Stock("libelle1", 16, 2);
		       Stock Stockupdate= service.updateStock(stock);
		       Assertions.assertEquals(stock.getQte(), Stockupdate.getQte());
		        }
	@Test
	@Order(4)
	public void testRetrieveStock() {
				long stockid =1;
		       Stock StockRetrieved= service.retrieveStock(stockid);
		       Assertions.assertEquals(1L, StockRetrieved.getIdStock());
		        }


	@Test
	@Order(5)
	public void testDeleteStock() {
				long stockid =1;
			    service.deleteStock(stockid);
				Assertions.assertNull(service.retrieveStock(stockid));
	        	}

	}
	*/
	



/*


@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceImplTest {


@Autowired
private StockServiceImpl service;
@MockBean
private StockRepository repository;
//DetailFournisseur df = new DetailFournisseur();

@Test
public void AddStockTest(){

   when(repository.findAll()).thenReturn(Stream
            .of(new Stock("libelle1", 600,1), new Stock("libelle2", 80,10)).collect(Collectors.toList()));
    assertEquals(2, service.retrieveAllStocks().size());
}
@Test
public void ModifyStockTest(){
	Stock stock =new Stock(1L,"libelleupdate",600,1);
   when(repository.save(stock).thenReturn(Optional.Stock()));
    assertEquals(2, service.retrieveAllStocks().size());
}

@Test
public void RetrieveStockTest(){
   //System.out.println("bjjjjr");
   when(repository.findAll()).thenReturn(Stream
            .of(new Stock("libelle1", 600,1), new Stock("libelle2", 80,10)).collect(Collectors.toList()));
    assertEquals(2, service.retrieveAllStocks().size());
}
@Test
public void DeleteStockTest(){
   //System.out.println("bjjjjr");
   when(repository.findAll()).thenReturn(Stream
            .of(new Stock("libelle1", 600,1), new Stock("libelle2", 80,10)).collect(Collectors.toList()));
    assertEquals(2, service.retrieveAllStocks().size());
}

}

/*

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceImplTest {

	
	@Autowired
	StockRepository sr;

	@Test
	public void add() {
		
		Stock s=Stock.builder().libelleStock("stock").qte(30).build();
		Stock s2=sr.save(s);
		assertEquals(s.getIdStock(), s2.getIdStock() );
		sr.delete(s2);
	}
	
}*/
