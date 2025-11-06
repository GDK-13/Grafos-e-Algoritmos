package uespi.trabalho;

import uespi.trabalho.Grafo.Aresta;
import uespi.trabalho.Grafo.No;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implementa o algoritmo de Kruskal (Árvore Geradora Mínima).
 */
public class Kruskal {

    /**
     * Encontra a MST:
     * 1. Ordena arestas da mais barata para a mais cara.
     * 2. Adiciona se não formar ciclo.
     * @param vertices
     */
    public static void mst_Kruskal(List<No> vertices) {
        System.out.println("\n--- Árvore Geradora Mínima (Kruskal) ---");
        
        if (vertices.isEmpty()) {
            System.out.println("O grafo está vazio.");
            return;
        }

        // --- Passo 1: Pegar todas as arestas (ignorando a direção) ---
        List<Aresta> todasArestas = Grafo.getTodasArestas(vertices, true);

        // --- Passo 2: Ordenar as arestas da mais barata para a mais cara ---
        Collections.sort(todasArestas);

        // --- Passo 3: Preparar a Árvore ---
        List<Aresta> mst = new ArrayList<>();
        int custoTotal = 0;

        // --- Verificação de Ciclo ---
        // 'grupos[i]' guarda o "grupo" do vértice 'i'.
        // Se dois nós já estão no mesmo grupo, uma aresta entre eles forma um ciclo.
        int numVertices = vertices.size();
        int[] grupos = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            grupos[i] = i; // Cada nó começa em seu próprio grupo
        }

        // --- Passo 4: Iterar pelas arestas ordenadas ---
        for (Aresta arestaAtual : todasArestas) {
            
            // Pega os ÍNDICES dos nós na aresta
            int indiceU = vertices.indexOf(arestaAtual.origem);
            int indiceV = vertices.indexOf(arestaAtual.destino);

            // Pega o NÚMERO DO GRUPO de cada nó
            int grupoU = grupos[indiceU];
            int grupoV = grupos[indiceV];

            // --- Passo 5: Verificar se forma ciclo ---
            // Se os nós estão em grupos DIFERENTES é seguro adicionar
            if (grupoU != grupoV) {
                
                mst.add(arestaAtual);
                custoTotal += arestaAtual.peso;

                // --- "União" (Union) ---
                // Junta os dois grupos.
                // Move todos os nós do 'grupoV' para o 'grupoU'.
                for (int i = 0; i < numVertices; i++) {
                    if (grupos[i] == grupoV) {
                        grupos[i] = grupoU;
                    }
                }
            }
            
            // Otimização: Se a árvore está completa, para cedo.
            if (mst.size() == numVertices - 1) {
                break;
            }

        } // Fim do loop de arestas

        // --- Exibir Resultado ---
        System.out.println("A Árvore Geradora Mínima (MST) tem as seguintes arestas:");
        for (Aresta aresta : mst) {
            imprimirArestaMST(aresta);
        }
        System.out.println("Custo Total da MST: " + custoTotal);
        
        if (mst.size() < numVertices - 1) {
            System.out.println("Atenção: O grafo não é totalmente conexo.");
        }
    }
    
    /**
     * Helper para imprimir a aresta de MST.
     */
    private static void imprimirArestaMST(Aresta aresta) {
         System.out.println("Aresta [ " + aresta.origem.id + " -- (peso=" + aresta.peso + ") -- " + aresta.destino.id + " ]");
    }
}