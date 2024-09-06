# Missão Prática | Nível 5 | Mundo 3

## RPG0018  - Por que não paralelizar

## Servidores e clientes baseados em Socket, com uso de Threads tanto no lado cliente quanto no lado servidor, acessando o banco de dados via JPA.

## Objetivos da prática

* Criar servidores Java com base em Sockets.
* Criar clientes síncronos para servidores com base em Sockets.
* Criar clientes assíncronos para servidores com base em Sockets.
* Utilizar Threads para implementação de processos paralelos.
* No final do exercício, o aluno terá criado um servidor Java baseado em Socket, com acesso ao banco de dados via JPA, além de utilizar os recursos nativos do Java para implementação de clientes síncronos e assíncronos. As Threads serão usadas tanto no servidor, para viabilizar múltiplos clientes paralelos, quanto no cliente, para implementar a resposta assíncrona.

# Materiais necessários para a prática

* SQL Server, com o banco de dados gerado em prática anterior (loja).
* JDK e IDE NetBeans.
* Navegador para Internet, como o Chrome.
* Banco de dados SQL Server com o Management Studio.
### Equipamentos:
  - Computador com acesso à Internet.

  - JDK e IDE NetBeans.

  - Banco de dados SQL Server.

  - Navegador de Internet instalado no computador.

# Desenvolvimento da prática
## 👉 1º Procedimento | Criando o Servidor e Cliente de Teste

1 - Criar o projeto do servidor, utilizando o nome CadastroServer, do tipo
    console, no modelo Ant padrão, para implementar o protocolo
    apresentado a seguir:
    
a - Cliente conecta e envia login e senha.

b - Servidor valida credenciais e, se forem inválidas, desconecta.

c - Com credenciais válidas, fica no ciclo de resposta.

 i - Cliente envia letra L.
ii - Servidor responde com o conjunto de produtos.

2 - Criar a camada de persistência em CadastroServer.

a - Criar o pacote model para implementação das entidades.

b - Utilizar a opção New..Entity Classes from Database.

![000011111](https://github.com/user-attachments/assets/da5d621e-4644-4e39-ba30-ed68af795077)

c - Selecionar a conexão com o SQL Server, previamente configurada na
    aba Services, divisão Databases, do NetBeans e adicionar todas as tabelas.

d - Acrescentar ao projeto a biblioteca Eclipse Link (JPA 2.1).

e - Acrescentar o arquivo jar do conector JDBC para o SQL Server.

###  Observação! Por não executar o servidor sob o Tomcat, não será necessário ajustar os pacotes para a distribuição do Jakarta.

3 - Criar a camada de controle em CadastroServer:

a - Criar o pacote controller para implementação dos controladores.

b - Utilizar a opção New..JPA Controller Classes from Entity Classes.

c- Na classe UsuarioJpaController, adicionar o método findUsuario,
  tendo como parâmetros o login e a senha, retornando o usuário a
  partir de uma consulta JPA, ou nulo, caso não haja um usuário com as
  credenciais.
  
d - Ao final o projeto ficará como o que é apresentado a seguir.

![00000002222](https://github.com/user-attachments/assets/663ea4e6-1621-4679-b4cd-3cfb2d2b8781)

4 - No pacote principal, cadastroserver, adicionar a Thread de
    comunicação, com o nome CadastroThread.

a - Acrescentar os atributos ctrl e ctrlUsu, dos tipos ProdutoJpaController
    e UsuarioJpaController, respectivamente.
    
b - Acrescentar o atributo s1 para receber o Socket.

c - Definir um construtor recebendo os controladores e o Socket, com a
    passagem dos valores para os atributos internos.

d - Implementar o método run para a Thread, cujo funcionamento será o
    descrito a seguir.

i - Encapsular os canais de entrada e saída do Socket em objetos dos
    tipos ObjectOutputStream (saída) e ObjectInputStream (entrada).

ii - Obter o login e a senha a partir da entrada.

iii - Utilizar ctrlUsu para verificar o login, terminando a conexão caso o
      retorno seja nulo.

iv - Com o usuário válido, iniciar o loop de resposta, que deve obter o
     comando a partir da entrada.

v - Caso o comando seja L, utilizar ctrl para retornar o conjunto de
    produtos através da saída.

5 - Implementar a classe de execução (main), utilizando as características
    que são apresentadas a seguir.

a - Instanciar um objeto do tipo EntityManagerFactory a partir da
    unidade de persistência.

b - Instanciar o objeto ctrl, do tipo ProdutoJpaController.

c - Instanciar o objeto ctrlUsu do tipo UsuarioJpaController.

d - Instanciar um objeto do tipo ServerSocket, escutando a porta 4321.

e - Dentro de um loop infinito, obter a requisição de conexão do cliente,
    instanciar uma Thread, com a passagem de ctrl, ctrlUsu e do Socket
    da conexão, iniciando-a em seguida.

f - Com a Thread respondendo ao novo cliente, o servidor ficará livre para
    escutar a próxima solicitação de conexão.

6 - Criar o cliente de teste, utilizando o nome CadastroClient, do tipo
    console, no modelo Ant padrão, para implementar a funcionalidade
    apresentada a seguir:

a - Instanciar um Socket apontando para localhost, na porta 4321.

b - Encapsular os canais de entrada e saída do Socket em objetos dos
    tipos ObjectOutputStream (saída) e ObjectInputStream (entrada).

c - Escrever o login e a senha na saída, utilizando os dados de algum dos
    registros da tabela de usuários (op1/op1).

d - Enviar o comando L no canal de saída.

e - Receber a coleção de entidades no canal de entrada.

f - Apresentar o nome de cada entidade recebida.

g - Fechar a conexão.

7 - Configurar o projeto do cliente para uso das entidades:

a - Copiar o pacote model do projeto servidor para o cliente.

b - Adicionar a biblioteca Eclipse Link (JPA 2.1).

c - A configuração final pode ser observada a seguir.

![00000003333333](https://github.com/user-attachments/assets/c7fe20b1-49fa-4102-9fd7-de546d30ec56)

8 - Testar o sistema criado, com a execução dos dois projetos:

a - Executar o projeto servidor.

b - Executar, em seguida, o cliente.

c - A saída do cliente deverá ser como a que é apresentada a seguir.

![00000000004444444](https://github.com/user-attachments/assets/179d1696-e0e8-4d66-bdbe-de69825556b1)

## 👉 2º Procedimento | Servidor Completo e Cliente Assíncrono

1 - Criar uma segunda versão da Thread de comunicação, no projeto do
    servidor, com o acréscimo da funcionalidade apresentada a seguir:
    
a - Servidor recebe comando E, para entrada de produtos, ou S, para
    saída.

b - Gerar um objeto Movimento, configurado com o usuário logado e o
    tipo, que pode ser E ou S.

c - Receber o Id da pessoa e configurar no objeto Movimento.

d - Receber o Id do produto e configurar no objeto Movimento.

e - Receber a quantidade e configurar no objeto Movimento.

f - Receber o valor unitário e configurar no objeto Movimento.

g - Persistir o movimento através de um MovimentoJpaController com o
    nome ctrlMov.

h - Atualizar a quantidade de produtos, de acordo com o tipo de
    movimento, através de ctrlProd.

### Observação! Devem ser acrescentados os atributos ctrlMov e ctrlPessoa, dos tipos MovimentoJpaController e PessoaJpaController, alimentados por meio de parâmetros no construtor

2 - Acrescentar os controladores necessários na classe principal, método
    main, e trocar a instância da Thread anterior pela nova Thread no loop de
    conexão.

3 - riar o cliente assíncrono, utilizando o nome CadastroClientV2, do tipo
    console, no modelo Ant padrão, para implementar a funcionalidade
    apresentada a seguir:

a - Instanciar um Socket apontando para localhost, na porta 4321.

b - Encapsular os canais de entrada e saída do Socket em objetos dos
    tipos ObjectOutputStream (saída) e ObjectInputStream (entrada).

c - Escrever o login e a senha na saída, utilizando os dados de algum dos
    registros da tabela de usuários (op1/op1) .

d - Encapsular a leitura do teclado em um BufferedReader.

e - Instanciar a janela para apresentação de mensagens (Passo 4) e a
    Thread para preenchimento assíncrono (Passo 5), com a passagem do
    canal de entrada do Socket.

f- Apresentar um menu com as opções: L – Listar,  X – Finalizar, E –
   Entrada, S – Saída.

g - Receber o comando a partir do teclado.

h - Para o comando L, apenas enviá-lo para o servidor.

i - Para os comandos E ou S, enviar para o servidor e executar os
    seguintes passos:

i - Obter o Id da pessoa via teclado e enviar para o servidor.

ii - Obter o Id do produto via teclado e enviar para o servidor.

iii - Obter a quantidade via teclado e enviar para o servidor.

iv - Obter o valor unitário via teclado e enviar para o servidor.

j - Voltar ao passo f até que o comando X seja escolhido

4 - Criar a janela para apresentação das mensagens:

a - Definir a classe SaidaFrame como descendente de JDialog

b - Acrescentar um atributo público do tipo JTextArea, com o nome texto

c - Ao nível do construtor, efetuar os passos apresentados a seguir:

i - Definir as dimensões da janela via setBounds

ii - Definir o status modal como false

iii - Acrescentar o componente JTextArea na janela

5 - Definir a Thread de preenchimento assíncrono, com o nome
    ThreadClient, de acordo com as características apresentadas a seguir :

a - Adicionar o atributo entrada, do tipo ObjectInputStream, e textArea,
    do tipo JTextArea, que devem ser preenchidos via construtor da
    Thread.

b - Alterar o método run, implementando um loop de leitura contínua.

c - Receber os dados enviados pelo servidor via método readObject.

d - Para objetos do tipo String, apenas adicionar ao JTextArea.

e - Para objetos do tipo List, acrescentar o nome e a quantidade de cada
    produto ao JTextArea.

### Observação! É necessário utilizar invokeLater nos acessos aos componentes do tipo Swing.

6 - Com o projeto CadastroServer em execução, iniciar o sistema do
    cliente, e testar todas as funcionalidades oferecidas.

![0000005555555](https://github.com/user-attachments/assets/008d149b-48b1-498e-b45c-7cdac37d9785)

<img width="539" alt="00000000000666666666" src="https://github.com/user-attachments/assets/21d545d2-9027-40f7-91e3-651dffe5fc57">





