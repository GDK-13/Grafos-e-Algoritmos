package uespi.trabalho;

import uespi.trabalho.Grafo.Aresta;
import uespi.trabalho.Grafo.No;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implementa o algoritmo Apaga-Reverso (MST).
 */
public class ReverseDelete {

    /**
     * Encontra a MST:
     * 1. Ordena arestas da MAIS CARA para a MAIS BARATA.
     * 2. Remove uma aresta.
     * 3. Se o grafo NÃO quebrou (continua conectado), a remoção é permanente.
     * 4. Se quebrou, coloca a aresta de volta.
     */
    public static void mst_ReverseDelete(List<No> vertices) {
        System.out.println("\n--- Árvore Geradora Mínima (Reverse-Delete) ---");

        if (vertices.isEmpty()) {
            System.out.println("O grafo está vazio.");
            return;
        }

        // --- Passo 1: Pegar todas as arestas (não-direcionado) ---
        List<Aresta> todasArestas = Grafo.getTodasArestas(vertices, true);

        // --- Passo 2: Ordenar do MAIOR peso para o MENOR ---
        todasArestas.sort(Collections.reverseOrder());

        // --- Passo 3: Começa com TODAS as arestas ---
        List<Aresta> mstArestas = new ArrayList<>(todasArestas);

        int custoTotal = 0;

        // --- Passo 4: Iterar pelas arestas (da mais cara p/ mais barata) ---
        // loop normal com iterador para remover com segurança)
        for (int i = todasArestas.size() - 1; i >= 0; i--) {
            Aresta arestaParaTestar = todasArestas.get(i);
            
            // 4.1. Remove a aresta temporariamente
            mstArestas.remove(arestaParaTestar);

            // 4.2. Verifica se o grafo continua conectado
            boolean aindaConectado = BFS.isGrafoConectado(vertices, mstArestas);

            // 4.3. Decide se a remoção é permanente
            if (aindaConectado == false) {
                mstArestas.add(arestaParaTestar);
            }
            // Se 'aindaConectado' for true, a remoção é permanente.
        }

        // --- 5. Mostrar o Resultado ---
        System.out.println("A Árvore Geradora Mínima (MST) tem as seguintes arestas:");
        for (Aresta aresta : mstArestas) {
            imprimirArestaMST(aresta);
            custoTotal += aresta.peso; 
        }
        System.out.println("Custo Total da MST: " + custoTotal);
        
        if (mstArestas.size() < vertices.size() - 1 && !vertices.isEmpty()) {
             System.out.println("Atenção: O grafo não é totalmente conexo.");
        }
    }

    /**
     * Helper para imprimir a aresta de MST (sem seta).
     */
    private static void imprimirArestaMST(Aresta aresta) {
         System.out.println("Aresta [ " + aresta.origem.id + " -- (peso=" + aresta.peso + ") -- " + aresta.destino.id + " ]");
    }
}