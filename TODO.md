# TODO - Pendencias de Implementacao (Detalhado)

## Como interpretar
- `Status`: `Pendente`, `Em andamento`, `Concluido`.
- `Prioridade`: `P0` (bloqueante), `P1` (alta), `P2` (media).
- `Criterio de aceite`: condicao objetiva para encerrar a tarefa.

## 1. Plataforma e Infra

### 1.1 Kafka local no ambiente de desenvolvimento
- Status: Pendente
- Prioridade: P0
- Escopo:
  - Adicionar `kafka` e `zookeeper` (ou stack KRaft) em `cmsaws-infra-aws/docker/docker-compose.yml`.
  - Configurar portas e variaveis para servicos produtores/consumidores.
  - Ajustar worker para bootstrap server do compose.
- Criterio de aceite:
  - `docker compose up -d` sobe Kafka sem erro.
  - Producao e consumo de mensagem de teste funcionando.

### 1.2 Ingress Kubernetes para front e APIs
- Status: Pendente
- Prioridade: P1
- Escopo:
  - Criar manifests `Ingress` para `frontend-user`, `frontend-admin` e APIs backend.
  - Definir hostnames por ambiente (`dev.local`, etc.).
- Criterio de aceite:
  - Rotas HTTP acessiveis via ingress controller sem port-forward manual.

### 1.3 Observabilidade local (opcional, mas recomendada)
- Status: Pendente
- Prioridade: P2
- Escopo:
  - Complementar stack ELK local (Kibana/Logstash) se for requisito de execucao dev.
- Criterio de aceite:
  - Logs de ao menos um servico aparecem no Kibana.

## 2. Back-end

### 2.1 Integracao real com Kafka no forum/worker
- Status: Pendente
- Prioridade: P0
- Escopo:
  - Garantir producer no fluxo do forum e consumer no `cmsaws-worker-forum`.
  - Cobrir contratos de evento e tratamento de erro/retry.
- Criterio de aceite:
  - Evento criado no service-forum e consumido pelo worker com persistencia esperada.

### 2.2 Endpoints de negocio completos
- Status: Em andamento
- Prioridade: P1
- Escopo:
  - Revisar cobertura funcional de CRUD e regras (filtros, paginacao, status).
  - Revisar casos de erro padronizados por servico.
- Criterio de aceite:
  - Swagger/contratos e testes cobrindo caminhos felizes e erros principais.

## 3. Front-end Usuario

### 3.1 Home real (cards + destaques)
- Status: Pendente
- Prioridade: P0
- Escopo:
  - Implementar listagem de materias.
  - Implementar carrossel com `is_highlight = true`.
  - Implementar estados de loading/erro/vazio.
- Criterio de aceite:
  - Home renderiza dados reais da API com fallback de erro amigavel.

### 3.2 Fluxos de autenticacao e contato
- Status: Pendente
- Prioridade: P1
- Escopo:
  - Login/cadastro e armazenamento de sessao.
  - Formulario de contato com departamentos reais.
- Criterio de aceite:
  - Fluxos funcionam ponta a ponta em ambiente local.

## 4. Front-end Admin

### 4.1 Painel administrativo funcional
- Status: Pendente
- Prioridade: P1
- Escopo:
  - CRUD de materias, categorias e usuarios.
  - Controle de permissao por perfil.
- Criterio de aceite:
  - Operacoes CRUD completas com validacao de permissao.

## 5. Testes e Qualidade

### 5.1 E2E Playwright - Camada de usuarios
- Status: Em andamento
- Prioridade: P1
- Escopo:
  - Suite inicial criada em `cmsaws-frontend-user/tests/e2e/user-portal.spec.ts`.
  - Expandir para fluxos reais (home com API, login, contato).
- Criterio de aceite:
  - Execucao `npm run test:e2e` verde em CI e local.

### 5.2 Pipeline de CI para E2E
- Status: Pendente
- Prioridade: P1
- Escopo:
  - Incluir job Playwright no workflow CI.
  - Publicar relatorio HTML/artifacts.
- Criterio de aceite:
  - PR bloqueado em caso de falha E2E.

## 6. Documentacao

### 6.1 Atualizar roadmap raiz
- Status: Pendente
- Prioridade: P2
- Escopo:
  - Atualizar checkboxes do `README.md` para refletir o estado atual.
- Criterio de aceite:
  - Roadmap sem itens desatualizados.

### 6.2 Guia de execucao local unico
- Status: Pendente
- Prioridade: P2
- Escopo:
  - Documentar passo a passo unico (infra + backend + front + testes).
- Criterio de aceite:
  - Novo colaborador sobe ambiente completo sem ajuste manual nao documentado.
