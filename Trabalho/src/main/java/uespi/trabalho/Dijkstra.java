package uespi.trabalho;

import uespi.trabalho.Grafo.Aresta;
import uespi.trabalho.Grafo.No;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Implementa o algoritmo de Dijkstra (caminho mais curto com peso).
 */
public class Dijkstra {

    /**
     * Encontra o caminho com a menor SOMA de pesos.
     * @param ign
     * @param sc
     * @param vertices
     */
    public static void menorCaminho_Dijkstra(char ign, Scanner sc, List<No> vertices) {
        System.out.println("\n--- Menor Caminho (Dijkstra) ---");

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

        // --- 2. Preparar Estruturas ---
        
        // distancias.get(i) guarda a distância do nó vertices.get(i)
        ArrayList<Integer> distancias = new ArrayList<>();
        // predecessores.get(i) guarda o "pai" do nó vertices.get(i)
        ArrayList<No> predecessores = new ArrayList<>();
        // "Fila" de nós que ainda não visitamos
        ArrayList<No> nosParaVisitar = new ArrayList<>();

        // --- 3. Iniciar o Algoritmo ---
        for (int i = 0; i < vertices.size(); i++) {
            distancias.add(Integer.MAX_VALUE); // Distância "Infinita"
            predecessores.add(null);           // Sem "pai"
            nosParaVisitar.add(vertices.get(i)); // Adiciona na fila
        }

        // A distância da Origem para ela mesma é 0
        int indiceInicial = vertices.indexOf(noInicial);
        distancias.set(indiceInicial, 0);

        boolean caminhoEncontrado = false;

        // --- 4. Loop Principal ---
        while (!nosParaVisitar.isEmpty()) {

            // 4.1. PROCURA MANUALMENTE O NÓ MAIS PRÓXIMo
            No noAtual = null;
            int menorDistancia = Integer.MAX_VALUE;

            for (No noDaFila : nosParaVisitar) {
                int indiceNo = vertices.indexOf(noDaFila);
                int distNo = distancias.get(indiceNo);

                if (distNo < menorDistancia) {
                    menorDistancia = distNo;
                    noAtual = noDaFila;
                }
            }

            // Se o nó mais próximo é "Infinito", o resto é inalcançável
            if (noAtual == null || menorDistancia == Integer.MAX_VALUE) {
                break; 
            }

            // 4.2. Remove o nó da fila (marca como finalizado
            nosParaVisitar.remove(noAtual);
            
            if (noAtual == noFinal) {
                caminhoEncontrado = true;
                break;
            }
            
            // 4.3. Relaxamento
            int indiceNoAtual = vertices.indexOf(noAtual);
            int distanciaNoAtual = distancias.get(indiceNoAtual);
            
            // Pega as arestas (respeitando ou não a direção)
            for (Aresta aresta : getArestasAdjacentes(noAtual, ign == 's')) {
                
                if (aresta.peso < 0) {
                    System.out.println("ERRO: Peso negativo!"); return;
                }
                
                No vizinho = (aresta.origem == noAtual) ? aresta.destino : aresta.origem;

                // Se o vizinho já foi finalizado, ignora
                if (!nosParaVisitar.contains(vizinho)) {
                    continue;
                }
                
                int novaDistancia = distanciaNoAtual + aresta.peso;
                int indiceVizinho = vertices.indexOf(vizinho);
                int distanciaAtualVizinho = distancias.get(indiceVizinho);

                // Se achou um caminho mais curto
                if (novaDistancia < distanciaAtualVizinho) {
                    distancias.set(indiceVizinho, novaDistancia);
                    predecessores.set(indiceVizinho, noAtual);
                }
            }
        }

        // --- 5. Mostrar o Resultado ---
        int indiceFinal = vertices.indexOf(noFinal);
        if (caminhoEncontrado && distancias.get(indiceFinal) != Integer.MAX_VALUE) {
            System.out.println("\nCaminho mais curto encontrado (por peso):");
            
            ArrayList<No> caminho = reconstruirCaminho(predecessores, vertices, noFinal);
            imprimirCaminhoSimples(caminho, distancias.get(indiceFinal));
        } else {
            System.out.println("\nNão foi encontrado um caminho entre " + idOrigem + " e " + idDestino + ".");
        }
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
     * Pega a lista de 'pais' e monta o caminho de volta.
     */
    private static ArrayList<No> reconstruirCaminho(ArrayList<No> predecessores, List<No> vertices, No noFinal) {
        ArrayList<No> caminho = new ArrayList<>();
        No noAtual = noFinal;
        
        // Vai pulando de filho para pai até chegar na origem
        while (noAtual != null) {
            caminho.add(noAtual);
            
            int indiceAtual = vertices.indexOf(noAtual);
            noAtual = predecessores.get(indiceAtual); 
        }
        
        Collections.reverse(caminho); // Inverte para (Origem -> Destino)
        return caminho;
    }

    /**
     * Imprime o caminho (lista de nós) e o custo.
     */
    private static void imprimirCaminhoSimples(ArrayList<No> caminho, Integer custo) {
        for (int i = 0; i < caminho.size(); i++) {
            No no = caminho.get(i);
            System.out.print(no.id);
            if (i < caminho.size() - 1) {
                System.out.print(" -> ");
            }
        }
        
        if (custo != null) {
            System.out.println("\nCusto total: " + custo);
        } else {
            System.out.println("\nTotal de " + (caminho.size() - 1) + " arestas.");
        }
    }
}