<h1 align="center">
  Sistema de Anúncios
</h1>

<br>

## Descrição do projeto

O projeto consiste em um sistema de anúncios onde os usuários podem visualizar anúncios,
ver em detalhes como data da postagem, o anunciante e descrição, e também podem fazer registro 
e login para publicar seus próprios anúncios.
O framework utilizado foi Spring Boot para criar a aplicação web, o mecanismo de
template Thymeleaf para renderizar as páginas HTML. O sistema possui paginação e
pesquisa de anúncios por título e por categorias. Para o login foi utilizado o spring security, 
os usuários que realizam o registro entram com o acesso de anunciante, onde só tem permissão para 
acessar o perfil e publicar seus anúncios, já o usuário com a permissão admin, pode cadastrar categorias e
banir anúncios das publicações.

## Tecnologias utilizadas

- Git : Controle de versionamento do sistema.
- Spring Boot : framework mais usado e mais completo hoje em dia para desenvolvimento Web Java.
- Spring MVC : é um framework que ajuda no desenvolvimento de aplicações web, com ele podemos construir aplicações web robustas e flexíveis.
- Spring Tool Suite : IDE para desenvolvimento Java feito para usar o Spring Boot.
- PostgreSQL :  sistema gerenciador de banco de dados objeto relacional (SGBD), fácil de usar com a sua interface gráfica.
- Thymeleaf: Mecanismo de template para renderização de páginas HTML.
- Spring Data JPA: Para interagir com o banco de dados.
- Spring Security: Para autenticação e autorização.

## Instruções para Executar o sistema
<p>Pré-requisitos</p>

- Git
- Spring Tool Suite
- PgAdmin

## Passos

- Abrir um terminal (Ctrl + Shift + T (No ubuntu)) ou linha de comando(Botão Windows + R, digitar cmd e apertar Enter (no Windows))
- Navegar até onde quiser baixar o repositório
- No terminal, colar essa linha e apertar Enter

```
git clone https://github.com/Alison-silva/sistemaanuncios.git
```

- Abrir O PgAdmin e criar um banco de dados com o nome anunciotb
- Abrir o Spring Tool Suite e clicar em File e depois em Import
- Na nova janela que aparece, escrever no campo do pesquisa maven
- Uma pasta com o nome Maven aparece. Dentro dessa pasta, clicar em Existing Maven Projects e clicar em Next
- Na próxima página, clicar em Browse... e navegar até a pasta sistemaanuncios e clicar abrir.
- Clicar em Finish
- Esperar o download das dependências do Maven
- Clique com o botão direito no projeto e escolhe a opção 'Run as' e depois escolher 'Spring Boot App'
- O projeto está agora rodando.

O sistema poderá ser acessado em [localhost:8080](http://localhost:8080).

Se ele apresentar um erro, abra o pacote src/main/resources e depois abra o arquivo application.properties.
Coloque o usuário e a senha que você atribuiu ao postgres

```
spring.datasource.username= <coloque seu username>
spring.datasource.password= <coloque sua senha>
```

- Após isso entre no PgAdmin, selecione o banco anunciotb, e execute esse SQL:

```
INSERT INTO role(id, desc_role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role(id, desc_role) VALUES (2, 'ROLE_ANUNCIANTE');
```
- OBS: com isso um perfil de acesso é criado após o registro.
- PARA ATIVAR AS OUTRAS OPÇÕES ENTRE NO BANCO DE DADOS E ALTERE SEU ACESSO PARA ROLE_ADMIN


## Funcionalidades

**Visualização de Postagens de Anúncios:**

- Os usuários podem navegar pelo sistema e visualizar todas os anúncios existentes.
- Cada anúncio exibirá o título, foto, categoria e o valor.
- O sistema possui paginação, de oito anúncio por tela, e possui barra de pesquisa por título e categoria, onde poderá filtrar os anúncios.
- clicando no anúncio o usuário poderá visualizar mais detalhes como título, nome do anunciante, imagem, data da publicação e descrição.

**Login e registro de Usuário:**


- Os usuários podem fazer o registro informando esses dados:
```
NOME: nome
E-MAIL: email
TELEFONE: 00 0 0000 0000
LOGIN: admin
SENHA: admin
```

- Os usuários podem fazer login usando as credenciais do sistema:
```
LOGIN: admin
SENHA: admin
```
- Apenas usuários autenticados terão permissão para publicar anúncios e editar seu perfil.

**Publicação de Anúncios:**

- Usuários autenticados podem criar e publicar seus próprios anúncios.
- O formulário de criação de anúncios incluirá título, descrição, imagem, preço e categoria.

**Funções do Admin:**

- O admin pode realizar as mesmas funções dos anunciantes. 
- O admin pode cadastrar categorias, e pode banir anúncios de outros usuários.
