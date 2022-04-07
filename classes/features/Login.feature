# language: pt


Funcionalidade: Cadastro de Paciente

  Como um usuário
  Gostaria de acessar o cadastro de pacientes
  Para validar as regras de validação dos campos


  Esquema do Cenario: Realizar Login no AGHU: Esqueci minha senha
    Dado que o usuário esteja na tela da funcionalidade de Realizar Login no AGHU
    Quando o usuário acionar a opção Esqueci minha senha
    Então o sistema direciona o usuário para o sistema Serviços TI e valida a "<url>", que mantém todo fluxo de troca de senha na Ebserh
    Exemplos:
      |                             url                            |
      |https://servicosti.ebserh.gov.br/#/solicitar-alteracao-senha|