package com.fluxo_estoque.domain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fluxo_estoque.domain.entity.StockHistory;
import com.fluxo_estoque.domain.event.OrderEvent;
import com.fluxo_estoque.domain.repository.StockHistoryRepository;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

	@Mock
	private StockHistoryRepository repository;

	@InjectMocks
	private StockService stockService;

	@Test
	@DisplayName("Deve processar e salvar o histórico de estoque com sucesso")
	void shouldProcessAndSaveStockHistorySuccessfully() {
		// Arrange (Preparação)
		OrderEvent event = new OrderEvent("ORD-123", "filipe-gomes", new java.math.BigDecimal("100.50"),
				List.of("Item A"));

		// Act (Ação)
		stockService.processOrderStock(event);

		// Assert (Verificação)
		// Verifica se o método save do repository foi chamado exatamente 1 vez
		verify(repository, times(1)).save(any(StockHistory.class));
	}
}