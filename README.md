# Teste Prático — Iniflex

Projeto desenvolvido em **Java 17** como solução para o teste prático de programação da Iniflex.

A aplicação cadastra uma lista de funcionários e executa operações de remoção, reajuste salarial, agrupamento, filtragem, ordenação e cálculos. A solução utiliza somente Java Core nas regras de negócio, com Maven para construção do projeto e JUnit 5 para testes.

## Requisitos atendidos

| Item | Implementação |
|---|---|
| 1 | Classe `Pessoa` com nome e data de nascimento |
| 2 | Classe `Funcionario` herdando de `Pessoa`, com salário e função |
| 3.1 | Cadastro dos funcionários na ordem apresentada no enunciado |
| 3.2 | Remoção do funcionário João |
| 3.3 | Impressão de todos os dados com formatação brasileira |
| 3.4 | Reajuste salarial de 10% |
| 3.5 | Agrupamento dos funcionários por função em um `Map` |
| 3.6 | Impressão dos funcionários agrupados por função |
| 3.8 | Filtro dos aniversariantes dos meses 10 e 12 |
| 3.9 | Identificação do funcionário com maior idade |
| 3.10 | Ordenação alfabética dos funcionários |
| 3.11 | Cálculo do total dos salários |
| 3.12 | Cálculo da quantidade de salários mínimos por funcionário |

> O enunciado original passa diretamente do item 3.6 para o 3.8 e não apresenta um requisito 3.7.

## Estrutura do projeto

```text
teste-pratico-iniflex/
├── .github/
│   └── workflows/
│       └── ci.yml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── br/com/iniflex/
│   │           ├── Principal.java
│   │           ├── model/
│   │           │   ├── Pessoa.java
│   │           │   └── Funcionario.java
│   │           └── service/
│   │               └── FuncionarioService.java
│   └── test/
│       └── java/
│           └── br/com/iniflex/
│               ├── PrincipalTest.java
│               └── model/
│                   └── ModelValidationTest.java
├── .gitignore
├── pom.xml
└── README.md
```

## Arquitetura

A aplicação foi organizada em três responsabilidades principais.

### Model

As classes `Pessoa` e `Funcionario` representam as entidades do domínio.

`Pessoa` possui:

- `String nome`;
- `LocalDate dataNascimento`.

`Funcionario` estende `Pessoa` e adiciona:

- `BigDecimal salario`;
- `String funcao`.

### Service

A classe `FuncionarioService` concentra as regras de negócio:

- remoção por nome;
- reajuste salarial;
- agrupamento por função;
- filtro de aniversariantes;
- identificação do funcionário mais velho;
- cálculo da idade;
- ordenação alfabética;
- soma dos salários;
- cálculo em salários mínimos.

### Aplicação

A classe `Principal`:

- cria os dados definidos pelo enunciado;
- coordena a sequência de execução;
- delega as regras ao serviço;
- formata e apresenta os resultados no console.

## Decisões técnicas

### BigDecimal para valores monetários

Salários e cálculos financeiros utilizam `BigDecimal`, evitando as imprecisões de representação encontradas em `float` e `double`.

Os valores são construídos a partir de texto:

```java
new BigDecimal("2009.44");
```

O reajuste salarial utiliza arredondamento explícito para duas casas decimais com `RoundingMode.HALF_UP`.

### LocalDate para datas

As datas de nascimento utilizam `LocalDate`, pois não precisam armazenar horário ou fuso. A idade é calculada com `Period.between`.

### Collections e Streams

A solução utiliza:

- `ArrayList` para armazenar os funcionários;
- `Map<String, List<Funcionario>>` para agrupamento por função;
- `filter` para aniversariantes;
- `sorted` e `Comparator` para ordenação;
- `map` e `reduce` para soma dos salários;
- `Collectors.groupingBy` para formação dos grupos.

### Formatação brasileira

As datas são apresentadas no padrão:

```text
dd/MM/yyyy
```

Os valores numéricos utilizam a localidade `pt-BR`, com ponto para milhares e vírgula para decimais:

```text
19.119,88
```

## Validações de domínio

As entidades evitam a criação de estados inválidos:

- nome não pode ser nulo ou vazio;
- função não pode ser nula ou vazia;
- espaços excedentes de nome e função são removidos;
- data de nascimento não pode ser nula ou futura;
- salário não pode ser nulo ou negativo;
- atualizações de salário passam pelas mesmas validações do cadastro.

O salário igual a zero é aceito, pois não representa um valor inválido para o tipo monetário.

## Testes automatizados

O projeto utiliza **JUnit 5** e possui testes para:

- cadastro e ordem original dos funcionários;
- remoção de João;
- reajuste salarial e arredondamento;
- agrupamento por função;
- aniversariantes de outubro e dezembro;
- funcionário mais velho;
- cálculo de idade;
- ordenação alfabética;
- total dos salários;
- quantidade de salários mínimos;
- atributos nulos;
- textos vazios;
- normalização de espaços;
- data de nascimento futura;
- salário negativo;
- validação durante a atualização do salário.

Para executar a suíte:

```bash
mvn clean test
```

## Integração contínua

O workflow `.github/workflows/ci.yml` utiliza GitHub Actions para executar:

```bash
mvn --batch-mode --update-snapshots verify
```

A validação automática utiliza Java 17 e é acionada em atualizações das branches `develop` e `main`, além de pull requests direcionados à `main`.

## Requisitos para execução

- Java JDK 17 ou superior;
- Apache Maven 3.9 ou compatível;
- Git.

Verifique as instalações:

```bash
java -version
mvn -version
git --version
```

## Como executar

Clone o repositório:

```bash
git clone https://github.com/RafaelCirn3/teste-pratico-iniflex.git
```

Acesse o diretório:

```bash
cd teste-pratico-iniflex
```

Compile e execute os testes:

```bash
mvn clean test
```

Execute a aplicação:

```bash
mvn exec:java
```

A saída será apresentada no terminal.

## Fluxo da aplicação

```text
Cadastro dos funcionários
        ↓
Remoção de João
        ↓
Impressão dos dados
        ↓
Reajuste salarial de 10%
        ↓
Agrupamento e impressão por função
        ↓
Filtro de aniversariantes
        ↓
Identificação do funcionário mais velho
        ↓
Ordenação alfabética
        ↓
Soma dos salários
        ↓
Cálculo em salários mínimos
```

## Diferenciais do projeto

Além do atendimento aos requisitos, foram adotadas decisões para melhorar a qualidade da solução:

- separação das regras de negócio em uma camada de serviço;
- entidades responsáveis por proteger seus próprios dados;
- métodos pequenos e testáveis;
- testes funcionais e de validação;
- construção reproduzível com Maven;
- integração contínua com GitHub Actions;
- histórico organizado com Conventional Commits;
- uso apenas das tecnologias necessárias ao problema.

A ausência de frameworks, banco de dados e containers é intencional. O objetivo é demonstrar domínio dos fundamentos solicitados por meio de uma solução simples, legível e de fácil execução.

## Autor

**Rafael Cirne Medeiros**

GitHub: [RafaelCirn3](https://github.com/RafaelCirn3)
