package com.fluxo_estoque.infrastructure.messaging;

import com.fluxo_estoque.domain.event.OrderEvent;
import com.fluxo_estoque.domain.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {

	private final StockService stockService;

	@KafkaListener(topics = "pedidos-criados", groupId = "estoque-group")
	public void receive(OrderEvent event) {
		log.info("#### [CONSUMER] Evento recebido: {}", event.orderId());

		// Delega a responsabilidade para o serviço
		stockService.processOrderStock(event);
	}
}