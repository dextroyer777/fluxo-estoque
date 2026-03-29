package com.fluxo_estoque.domain.repository;

import com.fluxo_estoque.domain.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
	// O Spring Data JPA vai gerar toda a implementação de save, find, etc.
}