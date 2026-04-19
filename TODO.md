# TODO - Pendencias de Implementacao

Itens concluidos foram removidos. Mantidos apenas os que ainda demandam acao.

## Como interpretar
- `Status`: `Pendente`, `Em andamento`.
- `Prioridade`: `P0` (bloqueante), `P1` (alta), `P2` (media).
- `Criterio de aceite`: condicao objetiva para encerrar a tarefa.

## 1. Plataforma e Infra

### 1.1 Observabilidade local (opcional, mas recomendada)
- Status: Pendente
- Prioridade: P2
- Escopo:
  - Complementar stack ELK local (Kibana/Logstash) se for requisito de execucao dev.
- Criterio de aceite:
  - Logs de ao menos um servico aparecem no Kibana.

## 2. Back-end

### 2.1 Endpoints de negocio completos
- Status: Em andamento
- Prioridade: P1
- Escopo:
  - Revisar cobertura funcional de CRUD e regras (filtros, paginacao, status).
  - Revisar casos de erro padronizados por servico.
- Criterio de aceite:
  - Swagger/contratos e testes cobrindo caminhos felizes e erros principais.

## 3. Testes e Qualidade

### 3.1 E2E Playwright - Camada de usuarios
- Status: Em andamento
- Prioridade: P1
- Escopo:
  - Suite inicial criada em `cmsaws-frontend-user/tests/e2e/user-portal.spec.ts`.
  - Expandir para fluxos reais (home com API, login, contato).
- Criterio de aceite:
  - Execucao `npm run test:e2e` verde em CI e local.
