Algoritmos em Grafos (Java)

Este é um projeto acadêmico focado em implementar os algoritmos clássicos de Teoria dos Grafos em Java. O programa é todo controlado por um menu de console, onde você pode criar seus próprios grafos, carregar exemplos e rodar os algoritmos.

O foco principal aqui não é a performance, mas sim a clareza do código. Optei por usar estruturas de dados mais simples e diretas (como ArrayList) em vez de bibliotecas otimizadas. A ideia é que o código seja didático e fácil de acompanhar.

🚀 O que o programa faz?

O projeto cobre todos os algoritmos pedidos na avaliação, incluindo buscas de menor caminho e três métodos distintos para encontrar a Árvore Geradora Mínima (MST).

1. Encontrar o Menor Caminho

    Busca em Largura (BFS)

        Para grafos sem peso (Não-Direcionado e Direcionado)

    Algoritmo de Dijkstra

        Para grafos com peso (Não-Direcionado e Direcionado)

2. Árvore Geradora Mínima (MST)

    Algoritmo de Kruskal

    Algoritmo de Prim

    Algoritmo Apaga-Reverso (Reverse-Delete)

3. Outras Ferramentas

    Criação e Exibição da Lista de Adjacência

    Ordenação de Arestas por peso (crescente e decrescente)

    Criação de grafos manualmente pelo console

    Carga de dois grafos de exemplo para testes rápidos

🏛️ Como o projeto foi pensado

A estrutura do projeto se baseia em duas ideias centrais:

1. O "Super Grafo"

Em vez de ter 4 classes diferentes, a classe Grafo.java usa um único modelo de "Super Grafo". Internamente, o grafo é sempre direcionado e com peso.

A mágica acontece nos algoritmos. São eles que decidem como interpretar o grafo:

    Se o algoritmo é não-direcionado, ele pede os vizinhos usando getVizinhos(true), que olha tanto as setas de saída (A -> B) quanto as de entrada (B -> A).

    Se o algoritmo é direcionado, ele usa getVizinhos(false) e pega só as setas de saída.

    Se o algoritmo é sem peso (como o BFS), ele simplesmente ignora o peso das arestas.

2. Código Didático (ou "Amador")

O objetivo principal é a clareza. Por isso, o código evita estruturas de dados complexas ou otimizadas.

    O que foi usado: ArrayList, Queue (com LinkedList) e loops for para fazer buscas.

Por exemplo, na implementação do Dijkstra, em vez de usar uma PriorityQueue (fila de prioridade) para achar o nó mais próximo, o código faz uma busca manual com um for loop dentro da lista. É uma solução mais lenta, mas é perfeita para entender a lógica do algoritmo passo a passo.

🏃‍♂️ Como Rodar o Projeto

    Você vai precisar do JDK (Java Development Kit) instalado.

    Clone o repositório:
    Bash

git clone [URL_DO_SEU_REPOSITORIO]

Entre na pasta do projeto e compile os arquivos .java

Bash

# Estando na pasta raiz (a que contém 'uespi')
javac uespi/trabalho/*.java

Execute a classe principal Trabalho:

Bash

    java uespi.trabalho.Trabalho

    O menu vai aparecer no console. Aconselho carregar um dos grafos de exemplo (Opção 2 ou 3) para começar a testar.

📂 Como os arquivos estão organizados

O projeto é bem modularizado. Cada algoritmo principal tem seu próprio arquivo:

    Trabalho.java: É o ponto de entrada, onde o main e o menu principal vivem.

    Grafo.java: É o coração do projeto. Define o No (Vértice), a Aresta e todos os métodos para criar e mostrar o grafo.

    BFS.java: Contém a Busca em Largura (sem peso) e também a função isGrafoConectado que o ReverseDelete usa.

    Dijkstra.java: Contém o Algoritmo de Dijkstra (com peso).

    Kruskal.java: Contém o Algoritmo de Kruskal (MST).

    Prim.java: Contém o Algoritmo de Prim (MST).

    ReverseDelete.java: Contém o Algoritmo Apaga-Reverso (MST).

    Ordenacao.java: Contém a lógica para ordenar e exibir as arestas.

👨‍💻 Autor

    GDK13 (Kevin)
