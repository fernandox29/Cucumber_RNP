# language: pt

@ignore
Funcionalidade: Cadastro de Paciente

  Como um usuário
  Gostaria de acessar o cadastro de pacientes
  Para validar as regras de validação dos campos

  Contexto:
    Dado que eu acesso a aplicação
    E acesso o menu "Pesquisar Pacientes"
    E eu realizo uma pesquisa de pacientes com o nome "Marcus Andre"
    E clico na opção Incluir Paciente

  @ignore
  Esquema do Cenario: Cadastrar Paciente CPF já cadastrado
    Dado que eu informo um valor no "<campo>" "<valor>" já cadastrado para outro paciente
    Então o sistema deve exibir "<mensagem>" de alerta como o "<nome>" do paciente
    E o sistema deve limpar a informação inserida no "<campo>"
    Exemplos:
      |campo      | valor       |         nome         | mensagem                                                                                                                                                      |
      |CPF        |68665639098  | MARCOS ANDRE DE LIMA | CPF: O CPF informado já foi cadastrado para o paciente MARCOS ANDRE DE LIMA. Caso não seja a mesma pessoa, atualize a informação para prosseguir.             |

  @ignore
  Esquema do Cenario: Cadastrar Pacientes PIS/PASEP já cadastrado
    Dado que eu informo um valor no "<campo>" "<valor>" já cadastrado para outro paciente
    Então o sistema deve exibir "<mensagem>" de alerta como o "<nome>" do paciente
    E o sistema deve limpar a informação inserida no "<campo>"
    Exemplos:
      |campo      | valor       |         nome         | mensagem                                                                                                                                                      |
      |PIS/PASEP  | 12345678    | MARCOS ANDRE DE LIMA | PIS/PASEP: O PIS/PASEP informado já foi cadastrado para o paciente MARCOS ANDRE DE LIMA. Caso não seja a mesma pessoa, atualize a informação para prosseguir. |

  @ignore
  Esquema do Cenario: Editar Pacientes Informações similares a paciente já cadastrado: Continuar mesmo assim
    Dado eu informo no campo "<campo>" do paciente o "<valor>"
    Quando o sistema exibe mensagem de crítica com o "<nomePaciente>" já cadastrado
    Então clico no botão para continuar
    E o sistema mantém o preenchimento dos campos "<nome>" "<campo>" "<valor>"

    Exemplos:
      | campo |     nome     |         valor      |     nomePaciente     |
      |  Mãe  | MARCUS ANDRE | MARIA DA CONCEIÇÃO | MARCOS ANDRE DE LIMA |
      | Data  | MARCUS ANDRE |     06/08/1999     | MARCOS ANDRE DE LIMA |

  @ignore
  Esquema do Cenario: Editar Pacientes Informações similares a paciente já cadastrado:Cancelar
    Dado eu informo no campo "<campo>" do paciente o "<valor>"
    E o sistema exibe mensagem de crítica com o "<nomePaciente>" já cadastrado
    Quando clico no botão para cancelar
    Então o sistema deve limpar o último campo preenchido antes da mensagem de alerta sobre a similaridade, mantendo o usuário na tela da funcionalidade e fechando a modal de alerta

    Exemplos:
      | campo |         valor      |     nomePaciente     |
      |  Mãe  | MARIA DA CONCEIÇÃO | MARCOS ANDRE DE LIMA |

  @ignore
  Esquema do Cenario: Editar Pacientes Informações similares a paciente já cadastrado:Fechar
    Dado eu informo no campo "<campo>" do paciente o "<valor>"
    E o sistema exibe mensagem de crítica com o "<nomePaciente>" já cadastrado
    Quando clico no botão para Fechar
    E o sistema mantém o preenchimento dos campos "<nome>" "<campo>" "<valor>"

    Exemplos:
      | campo |     nome     |         valor      |     nomePaciente     |
      |  Mãe  | MARCUS ANDRE | MARIA DA CONCEIÇÃO | MARCOS ANDRE DE LIMA |

  @ignore
  Esquema do Cenario: Validar cadastro endereço paciente brasileiro
    Dado que o usuário seleciona no campo "<pais>" a opção desejada
    E o sistema exibir na tela os campos "Município" "CEP" "UF" "IBGE" "Logradouro" "Número" "Complemento" "Bairro" "Tipo" "Correspondência" "Zona"
    Quando o usuário preenche os campos "<cep>" "<numero>" "<complemento>" "<zona>" e clica sobre o botão Adicionar
    Então o sistema exibe a mensagem "<mensagem>"
    E adiciona o endereço na tabela com as colunas "Ação" "CEP" "Logradouro" "Número" "Complemento" "País" "Bairro" "Município/Cidade" "Código IBGE" "UF/Estado" "Tipo" "Zona" "Correspondência"
    Exemplos:
      |  pais  |    cep   | numero | complemento |  zona  |                                         mensagem                                          |
      | Brasil | 72460290 |   22   |  Teste RNP  | Urbana |Endereço incluído com sucesso. Para gravar as alterações, o botão GRAVAR deve ser acionado.|

  @ignore
  Esquema do Cenario: Validar cadastro endereço paciente estrangeiro
    Dado que o usuário seleciona no campo "<pais>" a opção desejada
    Quando o usuário preenche os campos estrangeiros "<logradouro>" "<cidade>" "<estado>" "<tipo>" e clica sobre o botão Adicionar
    Então o sistema exibe a mensagem "<mensagem>"
    E adiciona o endereço na tabela com as colunas "Ação" "CEP" "Logradouro" "Número" "Complemento" "País" "Bairro" "Município/Cidade" "Código IBGE" "UF/Estado" "Tipo" "Zona" "Correspondência"
    Exemplos:
      | pais | logradouro                        | cidade   | estado | tipo     | mensagem                                                                                   |
      | USA  |5th Ave New York City NY 10118-4810| New York | NY     | Outros   | Endereço incluído com sucesso. Para gravar as alterações, o botão GRAVAR deve ser acionado.|
      | USA  |5th Ave New York City NY 10118-4810| New York | NY     | Comercial| Endereço incluído com sucesso. Para gravar as alterações, o botão GRAVAR deve ser acionado.|

  @ignore
  Esquema do Cenario: Cadastrar Pacientes - Box Responsável - CPF
    Dado que o sistema exiba no box "Responsável" o novo campo "CPF do Responsável"
    Então eu preencho no campo CPF "<CPF>"
    Exemplos:
      |      CPF     |
      |691.401.740-93|

  @ignore
  Esquema do Cenario: Cadastrar Pacientes - Box Responsável - CPF Inválido
    Dado que eu preencha o campo CPF do Responsável com um valor inválido "<CPF>"
    Então o sistema deve exibir "<mensagem>" de alerta
    Exemplos:
      |     CPF     |              mensagem               |
      | 22222222222 | O valor do CPF informado é inválido |

  Esquema do Cenario: Cadastrar Pacientes - Box Responsável - Documentos e Cartão SUS
    Dado que o sistema exiba no box "Responsável" o novo campo "CPF do Responsável"
    E o sistema exibe no box Documentos a label do campo Data alterada para "Data de Emissão"
    E o sistema exibe no box Cartão SUS a label do campo Número alterada para "Número do CNS"
    Então eu preencho no campo Número do CNS com "<numero>"
    Exemplos:
      | numero |
      |22222222|