package cadastroclient;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.net.SocketException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Produto;

/**
 *
 * @author Tiago J P Furtado
 */
public class ThreadClient extends Thread {

    private ObjectInputStream entrada;
    private SaidaFrame saidaFrame;
    private Map<String, BigDecimal> valoresTotaisAnteriores;
    private String usuario;

    public ThreadClient(ObjectInputStream entrada, SaidaFrame saidaFrame, String usuario) {
        this.entrada = entrada;
        this.saidaFrame = saidaFrame;
        this.valoresTotaisAnteriores = new HashMap<>();
        this.usuario = usuario;
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    // Recebe os dados enviados pelo servidor
                    Object obj = entrada.readObject();

                    // Se o objeto for do tipo String, apenas adiciona ao JTextArea
                    if (obj instanceof String) {
                        String mensagem = (String) obj;
                        // Atualiza a interface gráfica na thread de eventos do Swing
                        EventQueue.invokeLater(() -> {
                            saidaFrame.adicionarMensagem(mensagem);
                        });
                    } // Se o objeto for do tipo List, presume-se que é uma lista de Produtos
                    else if (obj instanceof List) {
                        List<Produto> produtos = (List<Produto>) obj;
                        // Atualiza a interface gráfica na thread de eventos do Swing
                        EventQueue.invokeLater(() -> {
                            // Exibe a mensagem de sucesso
                            saidaFrame.adicionarMensagem("\n");
                            saidaFrame.adicionarMensagem("Movimentação realizada com sucesso!");
                            saidaFrame.adicionarMensagem("================================================================================");
                            saidaFrame.adicionarMensagem(String.format("%-40s %15s %15s %20s", "Nome do Produto", "Quantidade", "Valor Unitário", "Valor Total"));
                            saidaFrame.adicionarMensagem("================================================================================");

                            // Para cada produto na lista recebida
                            for (Produto produto : produtos) {
                                // Formata o valor unitário
                                String valorUnitarioFormatado = formatarValor(produto.getPrecoVenda());
                                // Calcula o valor total (quantidade * valor unitário)
                                BigDecimal valorTotal = produto.getPrecoVenda().multiply(new BigDecimal(produto.getQuantidade()));
                                String valorTotalFormatado = formatarValor(valorTotal);

                                // Captura a data e hora atual
                                LocalDateTime dataHora = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                String dataHoraFormatada = dataHora.format(formatter);

                                // Verifica se houve alteração no valor total
                                BigDecimal valorTotalAnterior = valoresTotaisAnteriores.get(produto.getNome());
                                if (valorTotalAnterior == null || valorTotal.compareTo(valorTotalAnterior) != 0) {
                                    // Exibe as informações no JTextArea com formatação para alinhamento
                                    saidaFrame.adicionarMensagem(String.format("%-40s %15d %15s %20s",
                                            produto.getNome(),
                                            produto.getQuantidade(),
                                            valorUnitarioFormatado,
                                            valorTotalFormatado));

                                    saidaFrame.adicionarMensagem("================================================================================");

                                    saidaFrame.adicionarMensagem(String.format("Quantidade do produto '%s' alterada para %d.",
                                            produto.getNome(),
                                            produto.getQuantidade()));
                                    saidaFrame.adicionarMensagem(String.format("Valor total do produto '%s' alterado para %s.",
                                            produto.getNome(), valorTotalFormatado));
                                    saidaFrame.adicionarMensagem(String.format("Alteração feita em: %s por %s",
                                            dataHoraFormatada, usuario));
                                    valoresTotaisAnteriores.put(produto.getNome(), valorTotal);
                                    saidaFrame.adicionarMensagem("\n");
                                    saidaFrame.adicionarMensagem("================================================================================");
                                }
                            }
                        });
                    }
                } catch (SocketException se) {
                    // Socket foi fechado, termina o loop
                    System.out.println("Conexão encerrada: " + se.getMessage());
                    break;
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } finally {
            try {
                if (entrada != null) {
                    entrada.close(); // Fecha o ObjectInputStream
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método auxiliar para formatar o valor como R$
    private String formatarValor(BigDecimal valor) {
        NumberFormat formatador = NumberFormat.getCurrencyInstance();
        return formatador.format(valor);
    }
}
