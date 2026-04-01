# Fluxo Estoque - Microserviço de Processamento de Inventário

Este microserviço é responsável por processar os pedidos criados no ecossistema de e-commerce. Ele consome eventos do Kafka, executa a lógica de reserva de estoque e persiste o histórico de processamento.

## 🚀 Tecnologias e Arquitetura
* **Java 21**: Aproveitando a performance das **Virtual Threads** (Project Loom).
* **Spring Boot 3.x**: Framework para construção de microserviços Java.
* **Apache Kafka**: Consumo de mensagens de forma assíncrona e desacoplada.
* **PostgreSQL**: Persistência do histórico de processamento (`processamento_estoque`).
* **Hibernate/JPA**: Mapeamento objeto-relacional e gerenciamento de transações.
* **Lombok**: Redução de código boilerplate.

### 🔄 Fluxo de Processamento (Sequence Diagram)
O diagrama abaixo detalha a jornada da mensagem desde o consumo no Kafka até a persistência transacional no PostgreSQL.

```mermaid
sequenceDiagram
    participant K as Apache Kafka (pedidos-criados)
    participant C as OrderConsumer (Infrastructure)
    participant S as StockService (Domain)
    participant DB as PostgreSQL (processamento_estoque)

    K->>C: Envia Evento (OrderEvent)
    activate C
    Note over C: ErrorHandlingDeserializer verifica integridade
    C->>S: processarReserva(OrderEvent)
    activate S
    Note over S: @Transactional Scope
    S->>S: Executa Lógica de Reserva
    S->>DB: save(ProcessamentoEstoque)
    DB-->>S: Confirmação de Persistência
    S-->>C: Sucesso no Processamento
    deactivate S
    deactivate C