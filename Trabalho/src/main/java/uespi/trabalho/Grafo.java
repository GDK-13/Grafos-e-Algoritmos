package uespi.trabalho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Classe que guarda o Grafo.
 * Funciona como um "Super Grafo": armazena sempre como Direcionado e Ponderado.
 * Os algoritmos (BFS, Dijkstra) decidem se querem ignorar a direção ou os pesos.
 */
public class Grafo {

    // ------------------------------------
    // CLASSE INTERNA: Nó Vértice Inteligente
    // ------------------------------------
    public static class No {
        public int id;

        // Lista de arestas que SAEM deste nó (A -> B)
        public ArrayList<Aresta> arestasSaida = new ArrayList<>();
        // Lista de arestas que ENTRAM neste nó (B -> A)
        // (Isso é essencial para a função 'ignorarDirecao')
        public ArrayList<Aresta> arestasEntrada = new ArrayList<>();

        public No(int id) {
            this.id = id;
        }

        /**
         * Pega os vizinhos de um nó.
         * Se 'ignorarDirecao' for true, ele pega quem eu aponto E quem aponta pra mim.
         * Se for false, pega SÓ quem eu aponto.
         */
        public List<No> getVizinhos(boolean ignorarDirecao) {
            // Usamos um Set aqui só para evitar vizinhos duplicados
            Set<No> vizinhosUnicos = new HashSet<>();

            // 1. Pega todo mundo para quem eu aponto
            for (Aresta a : arestasSaida) {
                vizinhosUnicos.add(a.destino);
            }

            // 2. Se for pra ignorar a direção, pega também quem aponta pra mim
            if (ignorarDirecao) {
                for (Aresta a : arestasEntrada) {
                    vizinhosUnicos.add(a.origem);
                }
            }

            // Converte o Set de volta para uma Lista
            return new ArrayList<>(vizinhosUnicos);
        }

        // Método para imprimir o Nó e suas conexões de SAÍDA
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Vértice ").append(id).append(" aponta para: [");
            for (int i = 0; i < arestasSaida.size(); i++) {
                Aresta a = arestasSaida.get(i);
                sb.append(a.destino.id).append("(p=").append(a.peso).append(")");
                if (i < arestasSaida.size() - 1) sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    // ------------------------------------
    // CLASSE INTERNA: Aresta
    // ------------------------------------
    public static class Aresta implements Comparable<Aresta> {
        public No origem, destino;
        public int peso;

        public Aresta(No origem, No destino, int peso) {
            this.origem = origem;
            this.destino = destino;
            this.peso = peso;
        }

        // Permite ordenar as arestas pelo peso (usado no Collections.sort)
        @Override
        public int compareTo(Aresta outra) {
            return Integer.compare(this.peso, outra.peso);
        }

        @Override
        public String toString() {
            return "Aresta [" + origem.id + " --> " + destino.id + " | peso: " + peso + "]";
        }
    }

    // -----------------------------------------------
    // --- MÉTODOS DE GERENCIAMENTO DO GRAFO ---
    // -----------------------------------------------

    /**
     * Método para o usuário digitar o grafo manualmente.
     */
    public static void criarGrafoManual(Scanner sc, List<No> vertices) {
        vertices.clear();
        System.out.println("\n--- Criação de Grafo Unificado ---");
        System.out.println("Nota: Crie as arestas sempre pensando na direção (Origem -> Destino).");
        System.out.println("Se quiser uma aresta 'bidirecional' real, adicione A->B e B->A.");

        int quantidade = lerQuantidadeVertices(sc);
        for (int i = 0; i < quantidade; i++) {
            System.out.print("ID do vértice #" + i + ": ");
            vertices.add(new No(sc.nextInt()));
        }

        for (int i = 0; i < vertices.size(); i++) {
            No origem = vertices.get(i);
            System.out.println("\nConectando a partir do vértice " + origem.id);
            while (true) {
                System.out.print("Índice do Destino (-1 para parar): ");
                int indiceDestino = sc.nextInt();
                if (indiceDestino == -1) break;

                if (indiceDestino >= 0 && indiceDestino < vertices.size()) {
                    No destino = vertices.get(indiceDestino);
                    System.out.print("Peso para " + origem.id + " -> " + destino.id + ": ");
                    int peso = sc.nextInt();
                    
                    adicionarArestaUnificada(origem, destino, peso);
                } else {
                    System.out.println("Índice inválido.");
                }
            }
        }
        System.out.println("\nGrafo criado!");
    }

    /**
     * Carrega o grafo de exemplo da aula.
     */
    public static void carregarGrafoExemplo(List<No> vertices) {
        vertices.clear();
        // Cria 5 vértices
        No v0 = new No(0); No v1 = new No(1); No v2 = new No(2);
        No v3 = new No(3); No v4 = new No(4);
        Collections.addAll(vertices, v0, v1, v2, v3, v4);

        // Adiciona as arestas (direcionadas e com pesos)
        adicionarArestaUnificada(v0, v1, 10); // 0 -> 1
        adicionarArestaUnificada(v0, v2, 5);  // 0 -> 2
        adicionarArestaUnificada(v1, v2, 2);  // 1 -> 2
        adicionarArestaUnificada(v1, v3, 1);  // 1 -> 3
        adicionarArestaUnificada(v2, v1, 3);  // 2 -> 1 (volta)
        adicionarArestaUnificada(v2, v3, 9);  // 2 -> 3
        adicionarArestaUnificada(v2, v4, 2);  // 2 -> 4
        adicionarArestaUnificada(v4, v0, 7);  // 4 -> 0 (volta)
        adicionarArestaUnificada(v3, v4, 4);  // 3 -> 4

        System.out.println("\nGrafo de exemplo (5 nós) carregado.");
    }

    /**
     * Carrega um grafo maior e mais complexo para testes.
     */
    public static void carregarGrafoComplexo(List<No> vertices) {
        vertices.clear();
        // Cria 7 vértices
        No v0 = new No(0); No v1 = new No(1); No v2 = new No(2);
        No v3 = new No(3); No v4 = new No(4); No v5 = new No(5);
        No v6 = new No(6);
        Collections.addAll(vertices, v0, v1, v2, v3, v4, v5, v6);

        // Arestas
        adicionarArestaUnificada(v0, v1, 4);
        adicionarArestaUnificada(v0, v2, 3);
        
        adicionarArestaUnificada(v1, v2, 1); 
        adicionarArestaUnificada(v1, v3, 2);
        adicionarArestaUnificada(v3, v1, 1); 
        
        adicionarArestaUnificada(v2, v3, 4);
        adicionarArestaUnificada(v2, v4, 12); 
        
        adicionarArestaUnificada(v3, v4, 2);
        adicionarArestaUnificada(v3, v5, 5);
        
        adicionarArestaUnificada(v4, v6, 5);
        
        adicionarArestaUnificada(v5, v4, 1);
        adicionarArestaUnificada(v5, v6, 10);

        System.out.println("\nGrafo complexo (7 nós) carregado.");
    }


    /**
     * Método central que conecta dois nós.
     * Ele atualiza a lista de 'saída' da origem E a lista de 'entrada' do destino.
     */
    public static void adicionarArestaUnificada(No origem, No destino, int peso) {
        Aresta novaAresta = new Aresta(origem, destino, peso);
        origem.arestasSaida.add(novaAresta);
        destino.arestasEntrada.add(novaAresta);
    }

    /**
     * Exibe o grafo (mostra para onde cada nó aponta).
     */
    public static void exibirGrafo(List<No> vertices) {
        System.out.println("\n--- Estrutura do Grafo (Visão Direcionada Base) ---");
        if (vertices.isEmpty()) {
            System.out.println("O grafo está vazio.");
            return;
        }
        for (No no : vertices) {
            System.out.println(no);
        }
    }

    // --- MÉTODOS AUXILIARES ---

    /**
     * Pega o grafo e retorna uma lista simples de todas as arestas.
     * 'ignorarDirecao' é o mais importante:
     * - false: Pega todas as setas (A -> B é diferente de B -> A).
     * - true: Trata o grafo como "não-direcionado".
     */
    public static List<Aresta> getTodasArestas(List<No> vertices, boolean ignorarDirecao) {

        List<Aresta> listaFinalDeArestas = new ArrayList<>();

        // Passa por cada vértice (nó) do grafo
        for (No noAtual : vertices) {
            // Para cada nó, olhamos todas as setas que SAEM dele
            for (Aresta arestaDeSaida : noAtual.arestasSaida) {

                if (ignorarDirecao == false) {
                    // MODO DIRECIONADO: Apenas adiciona a seta
                    listaFinalDeArestas.add(arestaDeSaida);

                } else {
                    // MODO NÃO-DIRECIONADO: Precisa checar duplicata
                    // (Ex: Se já adicionamos 1->0, não queremos adicionar 0->1)
                    boolean duplicataEncontrada = false;

                    // Olha a lista de arestas que JÁ ADICIONAMOS
                    for (Aresta arestaJaAdicionada : listaFinalDeArestas) {

                        // Verifica se é a "volta" (ex: 0->1 e 1->0)
                        boolean ehArestaOposta = arestaDeSaida.origem == arestaJaAdicionada.destino &&
                                                 arestaDeSaida.destino == arestaJaAdicionada.origem;

                        // Verifica se os pesos são iguais
                        boolean pesosIguais = arestaDeSaida.peso == arestaJaAdicionada.peso;

                        if (ehArestaOposta && pesosIguais) {
                            // Se achamos a "volta" com o mesmo peso, é duplicata.
                            duplicataEncontrada = true;
                            break; // Para de procurar
                        }
                    }

                    // Se, depois de verificar tudo, não achamos duplicata...
                    if (duplicataEncontrada == false) {
                        // ...adiciona na lista!
                        listaFinalDeArestas.add(arestaDeSaida);
                    }
                }
            } 
        } 
        return listaFinalDeArestas;
    }

    private static int lerQuantidadeVertices(Scanner sc) {
        System.out.print("Quantos vértices? ");
        int n = sc.nextInt();
        return Math.max(0, n); // Evita número negativo
    }
}