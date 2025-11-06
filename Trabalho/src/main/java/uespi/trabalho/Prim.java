package uespi.trabalho;

import uespi.trabalho.Grafo.Aresta;
import uespi.trabalho.Grafo.No;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implementa o algoritmo de Prim (Árvore Geradora Mínima).
 */
public class Prim {

    /**
     * Encontra a MST crescendo a partir de um nó inicial.
     */
    public static void mst_Prim(Scanner sc, List<No> vertices) {
        System.out.println("\n--- Árvore Geradora Mínima (Prim) ---");

        if (vertices.isEmpty()) {
            System.out.println("O grafo está vazio.");
            return;
        }

        int numVertices = vertices.size();

        // --- 1. Preparar Estruturas ---

        // 'custoParaEntrar[i]' guarda o peso da aresta MAIS BARATA
        // para conectar o nó 'i' (que está fora) à árvore.
        ArrayList<Integer> custoParaEntrar = new ArrayList<>();
        
        // 'arestaDeEntrada[i]' guarda o "pai" (quem da árvore) 
        // oferece essa aresta barata.
        ArrayList<No> arestaDeEntrada = new ArrayList<>();
        
        // 'estaNaMST[i]' marca se o nó 'i' já foi incluído na árvore.
        ArrayList<Boolean> estaNaMST = new ArrayList<>();

        // --- 2. Iniciar as Estruturas ---
        for (int i = 0; i < numVertices; i++) {
            custoParaEntrar.add(Integer.MAX_VALUE); // Custo "Infinito"
            arestaDeEntrada.add(null);              // Nenhuma aresta
            estaNaMST.add(false);                   // Ninguém está na árvore
        }

        System.out.print("Digite o ID do vértice INICIAL para Prim: ");
        int idInicial = sc.nextInt();
        No noInicial = encontrarNoPorId(idInicial, vertices);
        
        if(noInicial == null) {
            System.out.println("ID não encontrado! Começando do vértice " + vertices.get(0).id);
            noInicial = vertices.get(0); 
        }
        
        int indiceInicial = vertices.indexOf(noInicial);
        custoParaEntrar.set(indiceInicial, 0); // Custo do primeiro nó é 0

        ArrayList<Aresta> mst = new ArrayList<>();
        int custoTotal = 0;

        // --- 3. Loop Principal (repete V vezes) ---
        for (int i = 0; i < numVertices; i++) {

            // 3.1. PROCURA MANUALMENTE O NÓ MAIS PRÓXIMO
            // (O nó que não está na MST e tem o menor custoParaEntrar)
            int menorCusto = Integer.MAX_VALUE;
            int indiceU = -1; // Índice do nó a ser adicionado

            for (int v = 0; v < numVertices; v++) {
                if (estaNaMST.get(v) == false && custoParaEntrar.get(v) < menorCusto) {
                    menorCusto = custoParaEntrar.get(v);
                    indiceU = v; // Achamos o próximo nó
                }
            }

            // 3.2. Verifica se o grafo é desconexo
            if (indiceU == -1) {
                System.out.println("Atenção: O grafo não é totalmente conexo.");
                break; 
            }

            // 3.3. Adiciona o nó 'U' à MST
            estaNaMST.set(indiceU, true);
            No noU = vertices.get(indiceU);

            // Adiciona a aresta na lista (exceto para o primeiro nó)
            if (indiceU != indiceInicial) {
                No noPai = arestaDeEntrada.get(indiceU);
                Aresta arestaAdicionada = new Aresta(noPai, noU, menorCusto);
                mst.add(arestaAdicionada);
                custoTotal += menorCusto;
            }

            // 3.4. Relaxamento
            // (Prim só funciona em não-direcionado, ignoraDirecao = true)
            for (Aresta arestaSaindoDeU : getArestasAdjacentes(noU, true)) {
                
                No noV = (arestaSaindoDeU.origem == noU) ? arestaSaindoDeU.destino : arestaSaindoDeU.origem;
                int indiceV = vertices.indexOf(noV);
                int pesoAresta = arestaSaindoDeU.peso;

                // Se o vizinho 'V' AINDA NÃO está na MST
                // E esta aresta é MAIS BARATA do que a que ele já tinha
                if (estaNaMST.get(indiceV) == false && pesoAresta < custoParaEntrar.get(indiceV)) {
                    custoParaEntrar.set(indiceV, pesoAresta);
                    arestaDeEntrada.set(indiceV, noU); // O "pai" de V agora é U
                }
            }
        } // Fim do loop principal

        // --- 4. Mostrar o Resultado ---
        System.out.println("A Árvore Geradora Mínima (MST) tem as seguintes arestas:");
        for (Aresta aresta : mst) {
            imprimirArestaMST(aresta);
        }
        System.out.println("Custo Total da MST: " + custoTotal);
    }


    // --- Métodos Auxiliares ---

    /**
     * Pega todas as arestas (com pesos) conectadas a um nó.
     */
    private static List<Aresta> getArestasAdjacentes(No no, boolean ignorarDirecao) {
        List<Aresta> arestas = new ArrayList<>();
        arestas.addAll(no.arestasSaida);
        if (ignorarDirecao) {
            arestas.addAll(no.arestasEntrada);
        }
        return arestas;
    }

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
     * Helper para imprimir a aresta de MST.
     */
    private static void imprimirArestaMST(Aresta aresta) {
         System.out.println("Aresta [ " + aresta.origem.id + " -- (peso=" + aresta.peso + ") -- " + aresta.destino.id + " ]");
    }
}