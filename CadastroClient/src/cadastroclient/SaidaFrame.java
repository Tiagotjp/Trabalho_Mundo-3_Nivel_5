package cadastroclient;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Tiago J P Furtado
 */

/**
 * SaidaFrame é uma classe personalizada que exibe uma janela de diálogo com uma
 * área de texto onde as mensagens são exibidas.
 */
public class SaidaFrame extends JFrame {  // Mudança de JDialog para JFrame

    private JTextArea texto;
    private String mensagemInicial;

    public SaidaFrame(String usuario) {
        // Configurações básicas da janela
        setTitle("Controle de Movimentação");
        setSize(700, 500);  // Define o tamanho da janela
        setLocationRelativeTo(null);  // Centraliza a janela na tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Fecha a janela ao clicar no "X"
        setResizable(true);  // Permite redimensionar a janela

        // Configura a área de texto
        texto = new JTextArea();
        texto.setEditable(false);  // Impede a edição do texto
        texto.setFont(new Font("Arial", Font.PLAIN, 14));  // Define a fonte do texto
        texto.setLineWrap(true);  // Ativa a quebra de linha automática
        texto.setWrapStyleWord(true);  // Ativa a quebra de linha por palavra
        texto.setBackground(new Color(240, 240, 240));  // Define uma cor de fundo suave
        texto.setForeground(new Color(50, 50, 50));  // Define a cor do texto
        texto.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));  // Adiciona uma borda ao redor da área de texto

        // Adiciona um painel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));  // Cor de fundo do painel de título
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Margem ao redor do painel
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Layout do painel de título

        // Adiciona um rótulo ao painel de título
        JLabel titleLabel = new JLabel("Mensagens do Sistema");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));  // Fonte do rótulo
        titleLabel.setForeground(Color.WHITE);  // Cor do texto do rótulo
        titlePanel.add(titleLabel);  // Adiciona o rótulo ao painel de título

        // Adiciona o painel de título à parte superior da janela
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Envolve a área de texto em um painel de rolagem
        JScrollPane scrollPane = new JScrollPane(texto);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Adiciona margem ao redor do texto

        // Adiciona o painel de rolagem à janela
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Adiciona a mensagem inicial com o usuário e a data/hora atuais
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        mensagemInicial = "Usuário: " + usuario + "\nConectado com Sucesso em: " + now.format(formatter) + "\n\n";
        texto.append(mensagemInicial);

        // Cria o painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Margem ao redor do painel

        // Personalização dos botões
        JButton clearButton = createCustomButton("Limpar", new Color(255, 99, 71));
        clearButton.addActionListener(e -> texto.setText(mensagemInicial));
        buttonPanel.add(clearButton);

        JButton printButton = createCustomButton("Imprimir", new Color(60, 179, 113));
        printButton.addActionListener(e -> {
            try {
                texto.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao imprimir: " + ex.getMessage());
            }
        });
        buttonPanel.add(printButton);

        JButton saveButton = createCustomButton("Salvar", new Color(70, 130, 180));
        saveButton.addActionListener(e -> salvarTexto());
        buttonPanel.add(saveButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Torna a janela visível
        setVisible(true);
    }

    /**
     * Retorna a área de texto onde as mensagens são exibidas.
     *
     * @return JTextArea para exibição de mensagens.
     */
    public JTextArea getTextArea() {
        return this.texto;
    }

    public void adicionarMensagem(String mensagem) {
        texto.append(mensagem + "\n");
    }

    private void salvarTexto() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                FileWriter writer = new FileWriter(fileChooser.getSelectedFile().getAbsolutePath());
                writer.write(texto.getText());
                writer.close();
                JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar arquivo: " + ex.getMessage());
        }
    }

    /**
     * Cria um botão personalizado com cores e bordas arredondadas.
     */
    private JButton createCustomButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(backgroundColor.darker(), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
