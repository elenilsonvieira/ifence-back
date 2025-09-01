# ifence-back
## Quick Start (H2 - Dev - persistente)

Executa com H2 em arquivo (dados persistem entre reinícios), sem precisar do PostgreSQL:

```powershell
cd ifence-back
$env:SPRING_PROFILES_ACTIVE = "h2"
./mvnw.cmd -DskipTests spring-boot:run
```

- Swagger: http://localhost:8080/swagger-ui/index.html
- H2 Console: http://localhost:8080/h2-console 
	- JDBC URL: `jdbc:h2:file:./data/ifence`
	- User: `sa` (sem senha)
	- Observação: os arquivos do banco serão criados em `ifence-back/data/`
- Usuário seed para login (criado automaticamente no profile `h2`):
	- Email: `admin@ifence.local`
	- Senha: `123456`

No app mobile, ajuste `app.json` (`extra.IFENCE_API_URL`) para o host correto:
- Emulador Android: `http://10.0.2.2:8080/api`
- Dispositivo físico: `http://SEU_IP_LOCAL:8080/api`


### Dependências:

- Git;
- PostgreSQL (versão 12 foi utilizada durante o desenvolvimento).
- Maven (versão 3.8.6 foi utilizada durante o desenvolvimento)[OPCIONAL];

Caso você não possua, ou não queira instalar o Maven, você pode utilizar o wrapper do maven que é provido pelo projeto, que são os arquivos *mvnw* e *mvnw.cmd* na raiz do projeto.

No Windows é utilizado o *mvnw.cmd*, e no Linux o *mvnw*.

### Obtendo o código-fonte:

Com o Git instalado em seu computador, abre um terminal de linha de comando (no Windows o Git Bash ou WSL).

Cole o comando descrito abaixo:

```bash
git clone https://github.com/FilipeFariasC/projeto-dac-2022.1-backend.git
```
### Configurando e executando:

Na linha de comando, entre no diretório do projeto:

```bash
cd /{caminho}/{até}/{repositório}/projeto-dac-2022.1-backend
```

Ainda na linha de comando, instale todas as dependências da aplicação. 
> OBS: usar o comando *mvn* adequado para a sua situação, que nem descrito [acima](#dependências).

```bash
mvn dependency:resolve
```

#### Configurando o PostgreSQL

Na linha de comando, dentro da pasta do repositório, execute o comando:
```
psql -U postgres -W -f ./sql/dev/init.sql
```
Isso criará o usuário no PostgreSQL que será utilizado na aplicação.


#### Executando a aplicação:
Para a execução da aplicação, digite:

```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
```

OBSERVAÇÕES FINAIS: Após seguir todos os passos acima você terá a aplicação funcionando LOCALMENTE, você terá acesso aos endpoints do nosso backend, entretanto para uma experiência completa é necessário a execução em paralelo da aplicação [frontend](https://github.com/elenilsonvieira/ifence-front) ou [mobile](https://github.com/elenilsonvieira/ifence-mobile).