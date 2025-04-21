# Sistema de Cálculo da Menor Distância entre Capitais Brasileiras

Este projeto é um sistema que calcula a menor distância entre capitais brasileiras utilizando o algoritmo de Dijkstra. Ele também permite visualizar todas as conexões disponíveis entre as capitais.

## Funcionalidades

- Calcular a menor distância entre duas capitais.
- Exibir todas as conexões disponíveis entre as capitais.
- Listar todas as capitais disponíveis no sistema.

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

src/ ├── Main.java # Classe principal que inicia o sistema ├── algorithm/ │ └── AlgoritmoDijkstra.java # Implementação do algoritmo de Dijkstra ├── model/ │ ├── Capital.java # Representação de uma capital │ └── Grafo.java # Representação do grafo de conexões entre capitais ├── service/ │ ├── CapitalService.java # Serviço para manipulação de capitais │ └── DadosCapitais.java # Dados e conexões entre capitais └── ui/ ├── Menu.java # Interface de menu para interação com o usuário └── SistemaCapitais.java # Controlador principal do sistema

## Como Executar

1. Certifique-se de ter o [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) instalado.
2. Compile o projeto:
   ```bash
   javac -d bin src/**/*.java
   ```

### Exemplo de Uso

1. Escolha a opção "1" no menu principal para calcular a menor distância entre duas capitais.
2. Insira o nome da capital de origem e da capital de destino.
3. O sistema exibirá o caminho mais curto e a distância total.

### Tecnologias Utilizadas

Java: Linguagem de programação principal.
Algoritmo de Dijkstra: Para cálculo do menor caminho em grafos.

### Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

### Licença

Este projeto está licenciado sob a MIT License.

### Autores:

Jhon Leno

Lucas de Lima

Sergio Paulo