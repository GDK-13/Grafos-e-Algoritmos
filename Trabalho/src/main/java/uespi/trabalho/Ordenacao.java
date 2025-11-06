package uespi.trabalho;

import uespi.trabalho.Grafo.Aresta;
import uespi.trabalho.Grafo.No;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Implementa o Requisito 10: Ordenação de Arestas.
 */
public class Ordenacao {

    /**
     * Pede ao usuário se quer ver as arestas direcionadas ou não,
     * e depois ordena por peso (crescente e decrescente).
     * @param sc
     * @param vertices
     */
    public static void ordenarArestas(Scanner sc, List<No> vertices) {
        System.out.println("\n--- Ordenação de Arestas por Peso ---");
        
        System.out.print("Deseja ignorar a direção (tratar como não-direcionado)? (s/n): ");
        char ign = sc.next().toLowerCase().charAt(0);

        // Pega todas as arestas baseado na escolha do usuário
        List<Aresta> arestas = Grafo.getTodasArestas(vertices, ign == 's');

        if (arestas.isEmpty()) {
            System.out.println("O grafo não possui arestas.");
            return;
        }

        // --- Ordem Crescente ---
        System.out.println("\n--- ORDEM CRESCENTE (por peso) ---");
        Collections.sort(arestas); // Ordenação padrão (usa o compareTo)
        for (Aresta a : arestas) {
            System.out.println(a);
        }

        // --- Ordem Decrescente ---
        System.out.println("\n--- ORDEM DECRESCENTE (por peso) ---");
        arestas.sort(Collections.reverseOrder()); // Ordem inversa
        for (Aresta a : arestas) {
            System.out.println(a);
        }
    }
}