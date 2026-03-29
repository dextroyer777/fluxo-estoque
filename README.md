# Fluxo Estoque - Microserviço de Processamento de Inventário

Este microserviço é responsável por processar os pedidos criados no ecossistema de e-commerce. Ele consome eventos do Kafka, executa a lógica de reserva de estoque e persiste o histórico de processamento.

## 🚀 Tecnologias e Arquitetura
* **Java 21**: Aproveitando a performance das **Virtual Threads** (Project Loom).
* **Spring Boot 3.x**: Framework para construção de microserviços Java.
* **Apache Kafka**: Consumo de mensagens de forma assíncrona e desacoplada.
* **PostgreSQL**: Persistência do histórico de processamento (`processamento_estoque`).
* **Hibernate/JPA**: Mapeamento objeto-relacional e gerenciamento de transações.
* **Lombok**: Redução de código boilerplate.

## 🛡️ Resiliência e Tratamento de Erros
Um dos grandes diferenciais deste serviço é a robustez no consumo de mensagens:
* **ErrorHandlingDeserializer**: Configurado para evitar o cenário de "Poison Pill" (mensagens malformadas que travam o consumer). Caso ocorra um erro de deserialização, o erro é logado sem interromper o fluxo da aplicação.
* **JsonDeserializer Customizado**: Configurado para aceitar pacotes confiáveis e definir um tipo padrão (`OrderEvent`) caso os headers de tipo não estejam presentes.
* **Transacionalidade**: O uso de `@Transactional` na camada de serviço garante que o registro no banco de dados só seja confirmado se todo o processo ocorrer sem exceções.

## 🏗️ Estrutura do Projeto
A estrutura segue as boas práticas de desenvolvimento:
1. **Infrastructure (Messaging)**: O `OrderConsumer` atua como o listener do tópico `pedidos-criados`.
2. **Domain (Service)**: O `StockService` encapsula a regra de negócio e orquestra a persistência.
3. **Domain (Entity/Repository)**: Camada de acesso a dados utilizando Spring Data JPA.
4. **Test**: Implementação de testes unitários com **JUnit 5** e **Mockito** para validação da lógica de negócio.

## ⚙️ Como Executar

### Pré-requisitos
* **openSUSE Leap 15.6** (Ambiente de desenvolvimento utilizado).
* **Docker & Docker Compose**.
* **Maven**.

### Configuração
O microserviço espera que o Kafka e o Postgres estejam rodando (via Docker Compose do ecossistema):
```bash
docker-compose up -d
