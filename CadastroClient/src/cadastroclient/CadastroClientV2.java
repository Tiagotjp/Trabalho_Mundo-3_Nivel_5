package cadastroclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.SwingUtilities;


/**
 *
 * @author Tiago J P Furtado
 */

public class CadastroClientV2 {

    private static volatile boolean isRunning = true;

    // Códigos ANSI para cores
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_BOLD = "\u001B[1m";

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4321); ObjectOutputStream outputStream
                = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream inputStream
                = new ObjectInputStream(socket.getInputStream()); BufferedReader reader
                = new BufferedReader(new InputStreamReader(System.in))) {

            // Leitura do login e senha
            System.out.print("Digite o login: ");
            String login = reader.readLine();
            System.out.print("Digite a senha: ");
            String senha = reader.readLine();

            // Enviar o login e a senha para o servidor.
            outputStream.writeObject(login);
            outputStream.writeObject(senha);

            // Instancia a janela de mensagem
            SaidaFrame saidaFrame = new SaidaFrame(login);
            SwingUtilities.invokeLater(() -> saidaFrame.setVisible(true));

            // Instanciar a Thread para comunicação com o servidor.
            ThreadClient threadClient = new ThreadClient(inputStream, saidaFrame, login);
            threadClient.start();

            while (isRunning) {
                // Menu estilizado e mais chamativo
                System.out.println(ANSI_CYAN + "╔══════════════════════════════════════════════════╗" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "║" + ANSI_RESET + ANSI_PURPLE + ANSI_BOLD + "               Menu de Opções                   " + ANSI_RESET + ANSI_CYAN + "║" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "╠══════════════════════════════════════════════════╣" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "║" + ANSI_RESET + ANSI_BLUE + ANSI_BOLD + "  [L] Listar                                      " + ANSI_RESET + ANSI_CYAN + "║" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "║" + ANSI_RESET + ANSI_BLUE + ANSI_BOLD + "  [E] Entrada                                     " + ANSI_RESET + ANSI_CYAN + "║" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "║" + ANSI_RESET + ANSI_BLUE + ANSI_BOLD + "  [S] Saída                                       " + ANSI_RESET + ANSI_CYAN + "║" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "║" + ANSI_RESET + ANSI_YELLOW + ANSI_BOLD + "  [X] Finalizar                                   " + ANSI_RESET + ANSI_CYAN + "║" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "╚══════════════════════════════════════════════════╝" + ANSI_RESET);
                System.out.print(ANSI_PURPLE + ANSI_BOLD + "Escolha uma opção: " + ANSI_RESET);
                String comando = reader.readLine();

                // Processamento do comando
                if (comando.equalsIgnoreCase("L")) {
                    outputStream.writeObject("L");
                } else if (comando.equalsIgnoreCase("X")) {
                    isRunning = false; // Define a condição de saída da thread
                    break; // Sai do loop principal
                } else if (comando.equalsIgnoreCase("E") || comando.equalsIgnoreCase("S")) {
                    // Enviar o comando para o servidor.
                    outputStream.writeObject(comando);

                    // IdPessoa
                    System.out.print("Digite o ID da pessoa: ");
                    int pessoaId = Integer.parseInt(reader.readLine());
                    outputStream.writeObject(pessoaId);

                    // IdProduto
                    System.out.print("Digite o ID do produto: ");
                    int produtoId = Integer.parseInt(reader.readLine());
                    outputStream.writeObject(produtoId);

                    // Quantidade
                    System.out.print("Digite a quantidade: ");
                    int quantidade = Integer.parseInt(reader.readLine());
                    outputStream.writeObject(quantidade);

                    // Valor Unitário
                    System.out.print("Digite o valor unitário: ");
                    float valorUnitario = Float.parseFloat(reader.readLine());
                    outputStream.writeObject(valorUnitario);
                } else {
                    System.out.println(ANSI_YELLOW + "Comando inválido." + ANSI_RESET);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Encerrar o programa corretamente
            System.exit(0);
        }
    }
}
