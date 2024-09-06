package cadastroclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;

/**
 *
 * @author Tiago J P Furtado
 */
public class CadastroClient {

    // Códigos ANSI para cores e estilo
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_BOLD = "\u001B[1m";

    public static void main(String[] args) throws IOException, ClassNotFoundException {        
        
        // Instanciar um Socket apontando para localhost, na porta 4321.
        Socket s1 = new Socket("localhost", 4321);
        
        // Encapsular os canais de entrada e saída.
        ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
        
        out.writeObject("op1"); // usuário
        out.writeObject("op1"); // senha
        out.writeObject("L"); // comando       
                  
        // Receber a lista de produtos
        List<Produto> lista = (List<Produto>) in.readObject();

        // Exibir a lista de produtos 
        System.out.println(ANSI_GREEN + "╔══════════════════════════════════════════════════╗");
        System.out.println("║              " + ANSI_BOLD + "LISTA DE PRODUTOS DISPONÍVEIS" + ANSI_RESET + ANSI_GREEN + "              ║");
        System.out.println("╚══════════════════════════════════════════════════╝" + ANSI_RESET);

        int index = 1;
        for (Produto produto : lista) {
            System.out.println(ANSI_BLUE + index + ". " + ANSI_CYAN + produto.getNome() + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "   Quantidade: " + produto.getQuantidade() + " Unidade(s)" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "--------------------------------------------------" + ANSI_RESET);
            index++;
        }

        // Fechar a conexão.
        s1.close();
    }
}
