package uespi.trabalho;

import java.util.ArrayList;
import java.util.Scanner;
import uespi.trabalho.Grafo.No; 

/**
 * Classe Principal (Controlador)
 * Responsável pelo menu de loop e por manter o grafo em memória.
 */
public class Trabalho {

    // Guarda o grafo em memória para os algoritmos usarem
    static ArrayList<No> grafoAtual = new ArrayList<>();

    /**
     * Método Main: Ponto de entrada do programa
     */
    public static void main(String[] args) {
        // Loop principal do menu
        try (Scanner sc = new Scanner(System.in)) {
            // Loop principal do menu
            while (true) {
                exibirMenu();
                int op = sc.nextInt();
                
                if (op == 0) {
                    System.out.println("Saindo...");
                    break; // Encerra o loop
                }
                
                executarOpcao(sc, op);
            }
        }
    }

    /**
     * Exibe o menu de opções para o usuário
     */
    private static void exibirMenu() {
        System.out.println("\n--- Menu Principal de Algoritmos em Grafos ---");
        System.out.println("---               Criação                 ---");
        System.out.println(" 1. Criar Grafo Manualmente");
        System.out.println(" 2. Carregar Grafo de Exemplo (5 nós)");
        System.out.println(" 3. Carregar Grafo Complexo (7 nós)");
        System.out.println("---           Exibição (Req 01, 02)       ---");
        System.out.println(" 4. Exibir Lista de Adjacência");
        System.out.println("---       Menor Caminho (Req 03-06)       ---");
        System.out.println(" 5. BFS - Não Direcionado (Sem Peso)");
        System.out.println(" 6. BFS - Direcionado (Sem Peso)");
        System.out.println(" 7. Dijkstra -Não Direcionado (Com Peso)");
        System.out.println(" 8. Dijkstra -Direcionado (Com Peso)");
        System.out.println("---             MST (Req 07-09)           ---");
        System.out.println(" 9. Árvore Geradora Mínima (Kruskal)");
        System.out.println(" 10. Árvore Geradora Mínima (Prim)");
        System.out.println(" 11. Árvore Geradora Mínima (Reverse-Delete)");
        System.out.println("---           Utilidades (Req 10)         ---");
        System.out.println(" 12. Ordenar Arestas por Peso");
        System.out.println(" 0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Chama o método apropriado com base na opção do menu
     */
    private static void executarOpcao(Scanner sc, int op) {
        
        // Trava de segurança: não permite rodar algoritmos se o grafo estiver vazio
        if (op > 3 && grafoAtual.isEmpty()) {
            System.out.println("\n[ERRO] Crie um grafo primeiro (Opção 1, 2 ou 3).");
            return;
        }

        switch (op) {
            // Criação
            case 1 -> Grafo.criarGrafoManual(sc, grafoAtual);
            case 2 -> Grafo.carregarGrafoExemplo(grafoAtual);
            case 3 -> Grafo.carregarGrafoComplexo(grafoAtual);
            // Exibição
            case 4 -> Grafo.exibirGrafo(grafoAtual);
            // Menor Caminho (BFS)
            case 5 -> BFS.menorCaminho_BFS('s', sc, grafoAtual); // 's' = ignorar direção
            case 6 -> BFS.menorCaminho_BFS('n', sc, grafoAtual); // 'n' = respeitar direção
            // Menor Caminho (Dijkstra)
            case 7 -> Dijkstra.menorCaminho_Dijkstra('s', sc, grafoAtual);
            case 8 -> Dijkstra.menorCaminho_Dijkstra('n', sc, grafoAtual);
            // MST
            case 9 -> Kruskal.mst_Kruskal(grafoAtual);
            case 10 -> Prim.mst_Prim(sc, grafoAtual);
            case 11 -> ReverseDelete.mst_ReverseDelete(grafoAtual);
            // Utilidades
            case 12 -> Ordenacao.ordenarArestas(sc, grafoAtual);
        
            default -> System.out.println("\n[ERRO] Opção inválida.");
        }
    }
}