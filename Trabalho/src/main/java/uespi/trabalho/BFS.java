package uespi.trabalho;

import uespi.trabalho.Grafo.No;
import uespi.trabalho.Grafo.Aresta; 
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Implementa o algoritmo de Busca em Largura (BFS).
 */
public class BFS {

    /**
     * Encontra o menor caminho (contando arestas, sem peso).
     */
    public static void menorCaminho_BFS(char ign, Scanner sc, List<No> vertices) {
        System.out.println("\n--- Menor Caminho (BFS) ---");

        // --- 1. Perguntar Origem/Destino ---
        System.out.print("Digite o ID do vértice de ORIGEM: ");
        int idOrigem = sc.nextInt();
        System.out.print("Digite o ID do vértice de DESTINO: ");
        int idDestino = sc.nextInt();

        No noInicial = encontrarNoPorId(idOrigem, vertices);
        No noFinal = encontrarNoPorId(idDestino, vertices);

        if (noInicial == null || noFinal == null) {
            System.out.println("Erro: Vértice de origem ou destino não encontrado.");
            return;
        }

        // --- 2. Preparar Estruturas do BFS ---
        
        // Fila que guarda o caminho completo (uma Lista de Nós)
        Queue<ArrayList<No>> filaDeCaminhos = new LinkedList<>();
        // Lista de nós que já visitou (pra não entrar em loop)
        ArrayList<No> visitados = new ArrayList<>();

        // --- 3. Iniciar o Algoritmo ---
        
        // O primeiro caminho é uma lista só com o nó inicial
        ArrayList<No> caminhoInicial = new ArrayList<>();
        caminhoInicial.add(noInicial);
        
        filaDeCaminhos.add(caminhoInicial);
        visitados.add(noInicial); 

        boolean caminhoEncontrado = false;

        // --- 4. Loop Principal ---
        while (!filaDeCaminhos.isEmpty()) {

            // Pega o caminho mais antigo da fila
            ArrayList<No> caminhoAtual = filaDeCaminhos.poll();
            // Pega o último nó desse caminho
            No noAtual = caminhoAtual.get(caminhoAtual.size() - 1);

            // Se for o destino, achou
            if (noAtual == noFinal) {
                imprimirCaminhoSimples(caminhoAtual);
                caminhoEncontrado = true;
                break;
            }

            // Pega os vizinhos respeitando ou ignorando a direção
            for (No vizinho : noAtual.getVizinhos(ign == 's')) {

                // Se ainda não visitamos esse vizinho
                if (!visitados.contains(vizinho)) {
                    // 1. Marca como visitado
                    visitados.add(vizinho);
                    // 2. Copia o caminho antigo
                    ArrayList<No> novoCaminho = new ArrayList<>(caminhoAtual);
                    // 3. Adiciona o vizinho no final
                    novoCaminho.add(vizinho);
                    // 4. Bota o caminho novo na fila
                    filaDeCaminhos.add(novoCaminho);
                }
            }
        }

        if (!caminhoEncontrado) {
            System.out.println("\nNão foi encontrado um caminho entre " + idOrigem + " e " + idDestino + ".");
        }
    }

    
    // --- Métodos Auxiliares ---

    /**
     * Procura um nó na lista de vértices pelo seu ID.
     */
    private static No encontrarNoPorId(int idProcurado, List<No> vertices) {
        for (No no : vertices) {
            if (no.id == idProcurado) {
                return no;
            }
        }
        return null; // Não encontrou
    }

    /**
     * Imprime o caminho (lista de nós)
     */
    private static void imprimirCaminhoSimples(ArrayList<No> caminho) {
        for (int i = 0; i < caminho.size(); i++) {
            No no = caminho.get(i);
            System.out.print(no.id);
            if (i < caminho.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println("\nTotal de " + (caminho.size() - 1) + " arestas.");
    }
    
    
    /**
     * Função especial para o ReverseDelete
     * Verifica se o grafo está "inteiro" (conectado)
     * usando APENAS a lista de arestas que ele recebe.
     */
    public static boolean isGrafoConectado(List<No> todosOsVertices, List<Aresta> arestasParaUsar) {
        
        if (todosOsVertices.isEmpty()) {
            return true;
        }

        // Lista de nós visitados
        ArrayList<No> visitados = new ArrayList<>();
        // Fila normal do BFS
        Queue<No> fila = new LinkedList<>();

        // Começa o BFS a partir do primeiro nó
        No noInicial = todosOsVertices.get(0);
        fila.add(noInicial);
        visitados.add(noInicial);

        while (!fila.isEmpty()) {
            No noAtual = fila.poll();

            // Procura vizinhos DESTE nó na lista de arestas
            for (Aresta aresta : arestasParaUsar) {
                No vizinho = null;

                // A aresta é (A -> B) ou (B -> A)?
                if (aresta.origem == noAtual) {
                    vizinho = aresta.destino;
                } else if (aresta.destino == noAtual) {
                    vizinho = aresta.origem;
                }

                // Se achou um vizinho e ele ainda não foi visitado
                if (vizinho != null && !visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }

        // Se visitou todo mundo, o grafo está conectado
        return visitados.size() == todosOsVertices.size();
    }
}