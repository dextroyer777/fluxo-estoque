package com.fluxo_estoque.domain.service;

import com.fluxo_estoque.domain.entity.StockHistory;
import com.fluxo_estoque.domain.event.OrderEvent;
import com.fluxo_estoque.domain.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

	private final StockHistoryRepository repository;

	@Transactional
	public void processOrderStock(OrderEvent event) {
		log.info("#### [SERVICE] Iniciando lógica de estoque para: {}", event.orderId());

		// Aqui você poderia adicionar validações reais:
		// 1. Verificar se o produto existe no banco
		// 2. Diminuir a quantidade do saldo de estoque
		// 3. Lançar uma exceção se não houver saldo (o Kafka tentaria novamente)

		StockHistory history = new StockHistory();
		history.setOrderId(event.orderId());
		history.setStatus("CONCLUIDO");
		history.setProcessedAt(LocalDateTime.now());

		repository.save(history);
		log.info("#### [SERVICE] Pedido persistido com sucesso.");
	}
}