---

# 🧩 **Algoritmos em Grafos (Java)**

Projeto acadêmico desenvolvido para implementar **os algoritmos clássicos de Teoria dos Grafos** em **Java**, com foco em **clareza e didática**, não em performance.
Todo o programa é controlado por **menu no console**, permitindo **criar grafos**, **carregar exemplos** e **executar algoritmos** interativamente.

---

## 🚀 **Funcionalidades Principais**

O projeto cobre os algoritmos exigidos na avaliação, incluindo **buscas de menor caminho** e **três métodos de Árvore Geradora Mínima (MST)**.

### 🧭 1. Menor Caminho

#### 🔹 **Busca em Largura (BFS)**

* Aplicável a grafos **sem peso**
* Funciona em **grafos direcionados e não-direcionados**

#### 🔹 **Algoritmo de Dijkstra**

* Aplicável a grafos **ponderados**
* Compatível com **versões direcionadas e não-direcionadas**

---

### 🌳 2. Árvore Geradora Mínima (MST)

* ⚙️ **Kruskal**
* ⚙️ **Prim**
* ⚙️ **Apaga-Reverso (Reverse-Delete)**

---

### 🧰 3. Outras Ferramentas

* 🗂️ Criação e exibição da **lista de adjacência**
* ⚖️ **Ordenação de arestas** por peso (crescente e decrescente)
* 🧑‍💻 **Criação manual de grafos** pelo console
* 🧪 **Carga de exemplos prontos** para testes rápidos

---

## 🏛️ **Filosofia do Projeto**

### 💡 1. O “Super Grafo”

A classe `Grafo.java` foi projetada como um **modelo unificado** — um **Super Grafo** que é **direcionado e ponderado internamente**.
Os **algoritmos** decidem **como interpretá-lo**.

| Tipo de Algoritmo | Método Usado              | Interpretação                          |
| ----------------- | ------------------------- | -------------------------------------- |
| Não-direcionado   | `getVizinhos(true)`       | Considera arestas em ambos os sentidos |
| Direcionado       | `getVizinhos(false)`      | Considera apenas as arestas de saída   |
| Sem peso          | Ignora o peso das arestas | Usado em BFS                           |

---

### ✏️ 2. Código Didático

O foco é **entendimento, não eficiência**.
Por isso, o código usa apenas estruturas simples e conhecidas:

* `ArrayList`
* `Queue` (com `LinkedList`)
* Loops `for` para varreduras manuais

📘 *Exemplo:*
O **Dijkstra** é implementado sem `PriorityQueue`, substituindo-a por um loop simples para escolher o menor custo — isso torna o raciocínio mais visível para quem está aprendendo.

---

## 🏃‍♂️ **Como Executar**

### 🔧 Requisitos

* Ter o **JDK (Java Development Kit)** instalado.

### 💻 Clonar e Compilar

```bash
# Clonar o repositório
git clone [URL_DO_SEU_REPOSITORIO]

# Entrar na pasta do projeto
cd nome-da-pasta

# Compilar os arquivos
javac uespi/trabalho/*.java

# Executar o programa principal
java uespi.trabalho.Trabalho
```

O **menu principal** aparecerá no console.

> 💡 Dica: Comece carregando um dos grafos de exemplo (opção 2 ou 3) para testar rapidamente.

---

## 📂 **Estrutura do Projeto**

| Arquivo                 | Função                                                     |
| ----------------------- | ---------------------------------------------------------- |
| `Trabalho.java`      | Ponto de entrada — contém o `main` e o menu principal      |
| `Grafo.java`         | Estrutura central — define vértices, arestas e utilitários |
| `BFS.java`           | Implementa a busca em largura e `isGrafoConectado`         |
| `Dijkstra.java`      | Implementa o algoritmo de Dijkstra                         |
| `Kruskal.java`       | Implementa o algoritmo de Kruskal                          |
| `Prim.java`          | Implementa o algoritmo de Prim                             |
| `ReverseDelete.java` | Implementa o algoritmo Apaga-Reverso                       |
| `Ordenacao.java`     | Contém a lógica de ordenação e exibição das arestas        |

---

## 👨‍💻 **Autor**

**GDK13 (Kevin)**
📚 Estudante de Ciência da Computação
---
