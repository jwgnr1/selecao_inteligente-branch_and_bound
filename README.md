# Seleção Inteligente de Projetos (Branch and Bound)

Sistema desenvolvido em Java para otimização e seleção de projetos sob restrições orçamentárias, utilizando a técnica de ramificação e poda (*Branch and Bound*), considerando projetos obrigatórios e regras de incompatibilidade entre eles.

---

## Funcionalidades da Interface em Console

O sistema dispõe de um menu interativo completo no terminal com as seguintes opções:

1. **Listar Projetos**: Exibe todos os projetos cadastrados no catálogo com seus respectivos códigos, nomes, custos, benefícios, status de obrigatoriedade e lista de incompatibilidades.
2. **Adicionar Projeto**: Permite cadastrar novos projetos informando código, nome, custo, benefício e se é obrigatório.
3. **Remover Projeto**: Remove um projeto do catálogo (atualizando automaticamente a remoção de suas incompatibilidades em outros projetos).
4. **Mudar Status Obrigatório**: Alterna o status de obrigatoriedade de qualquer projeto cadastrado.
5. **Adicionar Incompatibilidade**: Define uma restrição de incompatibilidade entre dois projetos.
6. **Remover Incompatibilidade**: Remove a restrição de incompatibilidade entre dois projetos.
7. **Alterar Orçamento**: Permite definir ou modificar o orçamento limite disponível para a seleção.
8. **Executar Seleção (Branch and Bound)**: Roda o algoritmo de otimização, exibindo o benefício máximo obtido, o custo total e a lista detalhada de cada projeto adotado (código, nome, custo e benefício).
