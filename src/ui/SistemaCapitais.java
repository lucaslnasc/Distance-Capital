package ui;

import model.Capital;
import model.Grafo;
import service.CapitalService;
import service.DadosCapitais;
import algorithm.AlgoritmoDijkstra;

public class SistemaCapitais {
    private Grafo grafo;
    private AlgoritmoDijkstra algoritmo;
    private CapitalService capitalService;
    private Menu menu;
    
    public SistemaCapitais() {
        this.grafo = new Grafo();
        DadosCapitais.inicializarConexoes(grafo);
        this.algoritmo = new AlgoritmoDijkstra(grafo);
        this.capitalService = new CapitalService(grafo);
        this.menu = new Menu();
    }
    
    public void executar() {
        System.out.println("=== Sistema de Cálculo da Menor Distância entre Capitais Brasileiras ===");
        
        boolean continuar = true;
        while (continuar) {
            menu.exibirMenuPrincipal();
            int opcao = menu.obterOpcao();
            
            switch (opcao) {
                case 1:
                    calcularMenorCaminho();
                    break;
                case 2:
                    capitalService.exibirTodasConexoes();
                    break;
                case 3:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        
        System.out.println("Obrigado por utilizar o sistema!");
        menu.fechar();
    }
    
    private void calcularMenorCaminho() {
        System.out.println("\n=== Cálculo da Menor Distância ===");
        System.out.println("\nCapitais disponíveis:");
        capitalService.exibirCapitaisDisponiveis();
        
        Capital origem = obterCapitalUsuario("Informe a capital de origem: ");
        if (origem == null) return;
        
        Capital destino = obterCapitalUsuario("Informe a capital de destino: ");
        if (destino == null) return;
        
        if (origem.equals(destino)) {
            System.out.println("Erro: A origem e destino não podem ser a mesma capital.");
            return;
        }
        
        try {
            AlgoritmoDijkstra.Resultado resultado = algoritmo.calcularMenorCaminho(origem, destino);
            
            if (resultado.getCaminho().size() <= 1 || resultado.getDistanciaTotal() == Integer.MAX_VALUE) {
                System.out.println("\nNão existe um caminho possível entre " + origem.getNome() + " e " + destino.getNome());
            } else {
                System.out.println("\nResultado:");
                System.out.println(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao calcular o caminho: " + e.getMessage());
        }
    }
    
    private Capital obterCapitalUsuario(String mensagem) {
        while (true) {
            String nome = menu.obterEntrada(mensagem);
            
            if (nome.isEmpty()) {
                System.out.println("Nome não pode ser vazio.");
                continue;
            }
            
            Capital capital = capitalService.buscarCapital(nome);
            
            if (capital == null) {
                if (!menu.confirmar("Capital não encontrada. Verifique se digitou corretamente. Deseja tentar novamente?")) {
                    return null;
                }
            } else {
                return capital;
            }
        }
    }
}