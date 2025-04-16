# AgendaDigital - Gerenciador de Contatos

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0%2B-orange)
![Swing](https://img.shields.io/badge/Interface_Gráfica-Java_Swing-yellowgreen)

Sistema completo para gerenciamento de contatos com banco de dados e interface intuitiva.

## ✨ Funcionalidades
- 📥 Adição de contatos com validação
- 🔍 Busca de contatos por nome
- 🗑️ Exclusão segura por ID
- 🟢 Verificação em tempo real da conexão com o banco
- 🔒 Armazenamento seguro de senhas (variáveis de ambiente)
- ⚡ Interface responsiva com SwingWorker

## 🛠️ Tecnologias Utilizadas
- Java 17+
- MySQL 8+
- Biblioteca Swing para GUI
- Driver JDBC para MySQL

## ⚙️ Instalação

### Pré-requisitos
- Java JDK 17+
- Servidor MySQL 8+
- MySQL Connector/J 8.1.0+


### Clonar repositório
```bash
git clone https://github.com/JP-Linux/AgendaDigital.git
```

### Criar banco de dados (MySQL)
```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS agenda;"
```

### Configurar variável de ambiente (Linux/macOS)
```bash
export DB_PASSWORD="sua_senha_mysql"
```

### Windows (PowerShell)
```bash
$env:DB_PASSWORD = "sua_senha_mysql"
```

## 🚀 Como Usar
1. Atualize as credenciais no arquivo `BancoDeDados.java`:
```java
private final String URL = "jdbc:mysql://localhost:3306/agenda";
private final String USUARIO = "seu_usuario";
```

2. Execute a aplicação:
```bash
mvn clean compile exec:java
```

3. Funcionalidades principais:
- Botões para Adicionar/Procurar/Excluir contatos
- Indicador visual de status da conexão
- Confirmação de saída do sistema

## ⚡ Configuração
| Variável de Ambiente | Descrição               |
|----------------------|-------------------------|
| DB_PASSWORD          | Senha do usuário MySQL  |


## 📄 Licença
Este projeto está sob licença MIT - veja [LICENSE](LICENSE) para detalhes

---

**Autor**  
👤 **Jorge Paulo Santos**  
[![GitHub](https://img.shields.io/badge/GitHub-Perfil-lightgrey)](https://github.com/JP-Linux)  
📧 jorgepsan7@gmail.com
