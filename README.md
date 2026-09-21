# Teste Prático - Iniflex

Projeto desenvolvido em **Java** como parte do teste técnico da **Iniflex**.

O objetivo é implementar uma aplicação simples para cadastro e manipulação de funcionários, utilizando recursos fundamentais da linguagem Java e boas práticas de organização de código.

## Objetivo

A aplicação parte de uma lista de funcionários contendo:

- nome;
- data de nascimento;
- salário;
- função.

A partir desses dados, o programa executa operações de remoção, atualização salarial, agrupamento, filtragem, ordenação e cálculos.

## Estrutura do projeto

A solução foi organizada a partir de três classes principais:

```text
src/
└── main/
    └── java/
        └── br/
            └── com/
                └── iniflex/
                    ├── Principal.java
                    └── model/
                        ├── Pessoa.java
                        └── Funcionario.java
```

### Pessoa

A classe `Pessoa` representa os dados básicos de uma pessoa.

Atributos:

```java
String nome;
LocalDate dataNascimento;
```

Foi utilizado `LocalDate` porque a data de nascimento não necessita de informações de horário ou fuso.

### Funcionario

A classe `Funcionario` estende `Pessoa` e adiciona os dados específicos do funcionário:

```java
BigDecimal salario;
String funcao;
```

O salário é representado por `BigDecimal`, evitando problemas de precisão comuns em cálculos monetários feitos com `float` ou `double`.

Exemplo:

```java
new BigDecimal("2009.44");
```

### Principal

A classe `Principal` contém o método `main` e é responsável por executar os requisitos solicitados no teste.

## Funcionalidades

### 1. Cadastro dos funcionários

Os funcionários são armazenados em uma:

```java
List<Funcionario>
```

utilizando uma implementação com:

```java
ArrayList<>
```

Os registros são inseridos na mesma ordem apresentada no enunciado.

### 2. Remoção do funcionário João

A remoção é realizada por meio de `removeIf`:

```java
funcionarios.removeIf(
    funcionario -> funcionario.getNome().equals("João")
);
```

### 3. Impressão dos funcionários

Todos os funcionários são exibidos com:

- nome;
- data de nascimento;
- salário;
- função.

As datas são formatadas no padrão brasileiro:

```text
dd/MM/yyyy
```

Para isso é utilizado `DateTimeFormatter`.

Os valores numéricos também são exibidos no padrão brasileiro, com ponto como separador de milhar e vírgula como separador decimal, utilizando `NumberFormat`.

Exemplo:

```text
19.119,88
```

### 4. Aumento salarial de 10%

Todos os funcionários recebem um reajuste de 10%.

O cálculo é realizado com `BigDecimal`:

```java
funcionario.getSalario()
    .multiply(new BigDecimal("1.10"));
```

### 5. Agrupamento por função

Os funcionários são agrupados utilizando:

```java
Map<String, List<Funcionario>>
```

A chave representa a função e o valor contém a lista de funcionários pertencentes àquela função.

O agrupamento é feito com Java Streams:

```java
Collectors.groupingBy(Funcionario::getFuncao)
```

### 6. Impressão dos funcionários agrupados por função

O `Map` gerado anteriormente é percorrido para exibir cada função junto aos respectivos funcionários.

### 7. Aniversariantes dos meses 10 e 12

A lista é filtrada com base no mês da data de nascimento:

```java
funcionario.getDataNascimento().getMonthValue()
```

São selecionados os funcionários que fazem aniversário em outubro ou dezembro.

### 8. Funcionário com maior idade

O funcionário mais velho é encontrado comparando as datas de nascimento:

```java
Comparator.comparing(Funcionario::getDataNascimento)
```

A idade é calculada utilizando:

```java
Period.between(
    funcionario.getDataNascimento(),
    LocalDate.now()
).getYears();
```

São exibidos o nome e a idade.

### 9. Ordenação alfabética

A lista é ordenada pelo nome:

```java
Comparator.comparing(Funcionario::getNome)
```

### 10. Total dos salários

O total dos salários é calculado utilizando `map` e `reduce`:

```java
funcionarios.stream()
    .map(Funcionario::getSalario)
    .reduce(BigDecimal.ZERO, BigDecimal::add);
```

### 11. Quantidade de salários mínimos

Para cada funcionário é calculado quantos salários mínimos o salário representa.

Foi considerado o valor definido no enunciado:

```text
R$ 1.212,00
```

O cálculo utiliza:

```java
BigDecimal.divide()
```

com:

```java
RoundingMode.HALF_UP
```

## Recursos do Java utilizados

O projeto utiliza principalmente:

- Programação Orientada a Objetos;
- herança;
- encapsulamento;
- `List` e `ArrayList`;
- `Map`;
- Java Streams;
- `Collectors.groupingBy`;
- `Comparator`;
- `BigDecimal`;
- `LocalDate`;
- `Period`;
- `DateTimeFormatter`;
- `NumberFormat`.

## Fluxo de execução

```text
Cadastro dos funcionários
        ↓
Remoção de João
        ↓
Impressão dos dados
        ↓
Aumento salarial de 10%
        ↓
Agrupamento por função
        ↓
Impressão dos grupos
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

## Requisitos

Para executar o projeto é necessário possuir:

- Java JDK 17 ou superior;
- Git;
- uma IDE Java, opcionalmente.

Para verificar a instalação do Java:

```bash
java -version
javac -version
```

## Como executar

Clone o repositório:

```bash
git clone https://github.com/RafaelCirn3/teste-pratico-iniflex.git
```

Entre no diretório do projeto:

```bash
cd teste-pratico-iniflex
```

Abra o projeto em uma IDE com suporte a Java, como:

- IntelliJ IDEA;
- Eclipse;
- NetBeans;
- Visual Studio Code.

Em seguida, localize a classe:

```text
Principal.java
```

e execute o método:

```java
public static void main(String[] args)
```

A saída da aplicação será exibida no terminal ou console da IDE.

## Diferenciais do projeto

Além de cumprir os requisitos funcionais propostos, o projeto foi pensado para demonstrar organização, segurança do domínio e facilidade de manutenção.

### Separação de responsabilidades

As regras de negócio foram concentradas em uma camada de serviço, evitando que a classe `Principal` acumule cálculos, filtros e transformações. Dessa forma:

- `Principal` coordena o fluxo da aplicação e apresenta os resultados;
- `FuncionarioService` concentra reajustes, agrupamentos, filtros, ordenações e cálculos;
- `Pessoa` e `Funcionario` representam e protegem os dados do domínio.

### Validações de domínio

As entidades validam seus próprios dados para impedir estados inválidos, incluindo:

- nomes e funções nulos ou vazios;
- remoção de espaços desnecessários;
- datas de nascimento futuras;
- salários nulos ou negativos;
- validação do salário tanto no cadastro quanto na atualização.

### Valores monetários seguros

Todos os salários e cálculos financeiros utilizam `BigDecimal`, com arredondamento explícito quando necessário. Isso evita as imprecisões que podem ocorrer com `float` e `double`.

### Código testável

As operações foram divididas em métodos pequenos e independentes, permitindo testar cada regra isoladamente. A suíte com JUnit 5 cobre os requisitos funcionais e as validações das entidades.

### Automação e reprodutibilidade

O projeto utiliza Maven para gerenciamento da compilação, execução e testes. Também foi configurado um workflow do GitHub Actions para validar automaticamente o projeto a cada atualização relevante.

### Simplicidade intencional

Não foram adicionados frameworks, banco de dados ou infraestrutura que não fossem necessários para o problema. A proposta é demonstrar domínio de Java Core, orientação a objetos, Collections, Streams, datas e cálculos monetários por meio de uma solução simples, legível e extensível.

## Decisões de implementação

A solução foi mantida propositalmente simples e focada em **Java Core**.

Não foram utilizados frameworks externos, banco de dados ou bibliotecas adicionais, pois o objetivo do teste é demonstrar domínio de:

- orientação a objetos;
- collections;
- manipulação de datas;
- operações monetárias;
- Streams;
- agrupamento;
- filtragem;
- ordenação;
- redução de dados.

Também foi priorizado o uso de tipos adequados ao domínio, como `BigDecimal` para valores monetários e `LocalDate` para datas de nascimento.

## Observação sobre o enunciado

O enunciado original pula diretamente do requisito **3.6** para o **3.8**. Por esse motivo, não há implementação correspondente a um requisito 3.7.

## Autor

**Rafael Cirne Medeiros**

GitHub: [RafaelCirn3](https://github.com/RafaelCirn3)
