package ui;

import java.util.Scanner;

public class Menu {
  private Scanner scanner;

  public Menu() {
    scanner = new Scanner(System.in);
  }

  public void exibirMenuPrincipal() {
    System.out.println("\nMenu Principal:");
    System.out.println("1. Calcular menor caminho entre capitais");
    System.out.println("2. Visualizar todas as conexões disponíveis");
    System.out.println("3. Sair");
    System.out.print("Escolha uma opção: ");
  }

  public int obterOpcao() {
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public void fechar() {
    scanner.close();
  }

  public String obterEntrada(String mensagem) {
    System.out.print(mensagem);
    return scanner.nextLine().trim();
  }

  public boolean confirmar(String mensagem) {
    while (true) {
      System.out.print(mensagem + " (s/n): ");
      String resposta = scanner.nextLine().toLowerCase();

      if (resposta.equals("s") || resposta.equals("sim") ||
          resposta.equals("y") || resposta.equals("yes")) {
        return true;
      } else if (resposta.equals("n") || resposta.equals("não") ||
          resposta.equals("no")) {
        return false;
      }

      System.out.println("Resposta inválida. Por favor, responda com 's' ou 'n'.");
    }
  }
}