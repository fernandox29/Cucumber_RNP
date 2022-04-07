package br.rnp.automacao.steps;

import br.rnp.automacao.core.DSL;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;


@Epic("Adicionar Informações do Usuário")
@Feature("Adicionar Informações do Usuário")
public class CadastroPacientes {

    private WebDriver driver;
    private DSL dsl;
    private String campoDocumento;

//    @After(order = 1)
//    public void screenshot(Scenario cenario) {
//        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        try {
//            FileUtils.copyFile(file, new File("target/screenshots/"
//                    + cenario.getName()
//                    + "."
//                    + cenario.getLine()
//                    + ".jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After(order = 0) //O zero será o último a ser executado. Para o before o zero é o primeiro.
//    public void fechar() {
//        driver.quit();
//    }

    @Test
    @Story("Usuário Adiciona contato")
    @Description("Usuário adiciona contato com sucesso")
    @Dado("que eu acesso a aplicação")
    public void queEuAcessoAAplicação() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://tst.ebserh.rnp.br/aghu/pages/casca/casca.xhtml");
        dsl = new DSL(driver);
        dsl.escrever(By.id("usuario:usuario:inputId"), "fernando.xavier");
        dsl.escrever(By.id("password:inputId"), "Senha@123");
        dsl.clicarBotao(By.id("entrar"));
        Thread.sleep(800);
    }

    @Dado("acesso o menu {string}")
    public void acessoOMenu(String string) {
        if (dsl.elementoExiste(By.id("central_pendencias")) == true){
            dsl.clicarBotao(By.xpath("//*[@id=\"central_pendencias\"]//a[1]/span"));
        }
        dsl.clicarTextoLink("Pacientes");
        dsl.clicarTextoLink("Pesquisar Pacientes");
        Assert.assertEquals(string, dsl.obterTexto(By.xpath("//*[@id=\"casca_tabs\"]//div[3]//li[2]//span[2]")));
    }

    @Então("eu realizo uma pesquisa de pacientes com o nome {string}")
    public void euRealizoUmaPesquisaDePacientesComONome(String string) {
        dsl.entrarFrame("i_frame_pesquisar_pacientes");
        dsl.escrever(By.id("nome:nome:inputId"), string);
        dsl.clicarBotao(By.id("btnPesquisaFonetica:button"));
    }

    @Então("clico na opção Incluir Paciente")
    public void clicoNaOpçãoIncluirPaciente() {
        dsl.clicarBotao(By.id("incluirPaciente:button"));
    }

    @Dado("que eu informo um valor no {string} {string} já cadastrado para outro paciente")
    public void queEuInformoUmCampoValorJaCadastradoParaOutroPaciente(String campo, String valor) {
        if (campo.equals("PIS/PASEP")) {
            dsl.escrever(By.id("fld_pispasep:fld_pispasep:inputId_input"), valor);
        } else {
            dsl.escrever(By.id("cpf:cpf:inputId"), valor);
        }
        campoDocumento = campo;
    }

    @E("o sistema deve limpar a informação inserida no {string}")
    public void oSistemaDeveLimparAInformacaoInseridaNoCampoPispasep(String campo) {
        if (campo.equals("PIS/PASEP")) {
            Assert.assertEquals("", dsl.obterTexto(By.id("fld_pispasep:fld_pispasep:inputId_input")));
        } else {
            Assert.assertEquals("", dsl.obterTexto(By.id("cpf:cpf:inputId")));
        }
    }

    @Então("o sistema deve exibir mensagem de alerta como o {string} do paciente")
    public void oSistemaDeveExibirMensagemDeAlertaComoODoPaciente(String string) {
        dsl.esperarElementoVisivel(By.id("messagesInDialog"), 10);
        String texto = ("CPF: O CPF informado já foi cadastrado para o paciente " + string +
                ". Caso não seja a mesma pessoa, atualize a informação para prosseguir.");
        Assert.assertEquals(texto, dsl.obterTexto(By.id("messagesInDialog")));
    }

    @Então("o sistema deve exibir {string} de alerta como o {string} do paciente")
    public void oSistemaDeveExibirMensagemDeAlertaComoONomeDoPaciente(String mensagem, String nome) {
        if (campoDocumento.equals("PIS/PASEP")) {
            dsl.esperarElementoVisivel(By.xpath("//*[@id=\"messagesInDialog\"]/div/ul/li/span"), 5000);
            Assert.assertEquals(mensagem, dsl.obterTexto(By.xpath("//*[@id=\"messagesInDialog\"]/div/ul/li/span")));
        } else {
            dsl.esperarElementoVisivel(By.id("messagesInDialog"), 5000);
            Assert.assertEquals(mensagem, dsl.obterTexto(By.id("messagesInDialog")));
        }
    }

    @Dado("eu informo no campo {string} do paciente o {string}")
    public void euInformoNoCampoDoPacienteO(String campo, String valor) {
        dsl.esperarLoad();
        if (campo.equals("Mãe")) {
            dsl.escrever(By.id("nome_mae:nome_mae:inputId"), valor);
        } else if (campo.equals("Data")) {
            driver.findElement(By.id("data_nasc:data_nasc:inputId_input")).sendKeys(valor);
        }
    }

    @E("o sistema exibe mensagem de crítica com o {string} já cadastrado")
    public void oSistemaExibeMensagemDeCríticaComOJáCadastrado(String string) {
        dsl.esperarLoad();
        dsl.escrever(By.id("horaNascimento:horaNascimento:inputId"), "");
        dsl.esperarElementoVisivel(By.id("modalPacientesSimilares"), 10);
        String resultado = dsl.obterTexto(By.xpath("//*[@id=\"form_pacientes_similares:tabelaPacientesSimilares:resultList_data\"]//td[1]"));
        Assert.assertEquals(string, resultado);
    }

    @Então("clico no botão para continuar")
    public void clicoNoBotãoParaContinuar() {
        dsl.clicarBotao(By.xpath("//div[9]//button[1]/span[2]"));
    }
    @Quando("clico no botão para cancelar")
    public void clicoNoBotãoParaCancelar() {
        dsl.clicarBotao(By.xpath("//*[@id=\"form_pacientes_similares:j_idt2253:button\"]/span[2]"));
    }
    @Quando("clico no botão para Fechar")
    public void clicoNoBotãoParaFechar() {
        dsl.clicarBotao(By.xpath("//*[@id=\"modalPacientesSimilares\"]/div[1]/a/span"));
    }
    @Então("o sistema mantém o preenchimento dos campos {string} {string} {string}")
    public void oSistemaMantémOPreenchimentoDosCampos(String nome, String campo, String valor) {
        dsl.esperarLoad();
        String nomePaciente = dsl.obterValueElemento(By.id("nome:nome:inputId"));
        if (campo.equals("Mãe")) {
            String mae = dsl.obterValueElemento(By.id("nome_mae:nome_mae:inputId"));
            Assert.assertEquals(nome, nomePaciente);
            Assert.assertEquals(valor, mae);
        } else if (campo.equals("Data")) {
            String dataNasci = dsl.obterValueElemento(By.id("data_nasc:data_nasc:inputId_input"));
            Assert.assertEquals(nomePaciente, nome);
            Assert.assertEquals(valor, dataNasci);
        }
    }
    @Então("o sistema deve limpar o último campo preenchido antes da mensagem de alerta sobre a similaridade, mantendo o usuário na tela da funcionalidade e fechando a modal de alerta")
    public void oSistemaDeveLimparOUltimoCampoPreenchidoAntesDaMensagemDeAlertaSobreASimilaridadeMantendoOUsuárioNaTelaDaFuncionalidadeEFechandoAModalDeAlerta() throws InterruptedException {
        dsl.esperarLoad();
        Thread.sleep(5000);
        String mae = dsl.obterValueElemento(By.id("nome_mae:nome_mae:inputId"));
        Assert.assertEquals("", mae);
        }

    @Dado("que o usuário seleciona no campo {string} a opção desejada")
    public void queOUsuárioSelecionaNoCampoAOpçãoDesejada(String pais) {
        dsl.escrever(By.id("paisEndereco:paisEndereco:suggestion_input"), pais);
        dsl.esperarLoad();
        dsl.esperarElementoVisivel(By.xpath("//*[@id=\"paisEndereco:paisEndereco:suggestion_panel\"]//td"), 5);
        dsl.clicarBotao(By.xpath("//*[@id=\"paisEndereco:paisEndereco:suggestion_panel\"]//td"));
    }

    @Dado("o sistema exibir na tela os campos {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void oSistemaExibirNaTelaOsCampos(String municipio, String cep, String uf, String ibge, String logradouro, String numero, String complemento, String bairro, String tipo, String correspondencia, String zona) {
        dsl.esperarLoad();
        dsl.esperarElemento(By.xpath("//fieldset[5]//span[2]//label"), 10);

        String campo1 = dsl.obterTexto(By.xpath("//fieldset[5]//span[2]//label"));
        String campo2 = dsl.obterTexto(By.xpath("//form[3]/fieldset[5]//span[3]/div[1]//label"));
        String campo3 = dsl.obterTexto(By.xpath("//form[3]/fieldset[5]//span[3]/div[2]//div[1]/label"));
        String campo4 = dsl.obterTexto(By.xpath("//fieldset[5]/div//div[1]//div[3]//label"));
        String campo5 = dsl.obterTexto(By.xpath("//form[3]/fieldset[5]//div[2]/span/div[1]//div[1]/label"));
        String campo6 = dsl.obterTexto(By.xpath("//form[3]/fieldset[5]//div[2]/span/div[2]//div[1]/label"));
        String campo7 = dsl.obterTexto(By.xpath("//form[3]/fieldset[5]//div[2]/span/div[3]/div[1]//label"));
        String campo8 = dsl.obterTexto(By.xpath("//form[3]/fieldset[5]//div[3]/span//label"));
        String campo9 = dsl.obterTexto(By.xpath("//div[1]/form[3]/fieldset[5]/div/span[1]/div[3]/div[1]//label"));
        String campo10 = dsl.obterTexto(By.xpath("//div[1]//fieldset[5]//div[3]/div[2]/div[1]/div[1]/label"));
        String campo11 = dsl.obterTexto(By.xpath("//fieldset[5]//div[3]/div[3]//label"));

        Assert.assertEquals(municipio, campo1);
        Assert.assertEquals(cep, campo2);
        Assert.assertEquals(uf, campo3);
        Assert.assertEquals(ibge, campo4);
        Assert.assertEquals(logradouro, campo5);
        Assert.assertEquals(numero, campo6);
        Assert.assertEquals(complemento, campo7);
        Assert.assertEquals(bairro, campo8);
        Assert.assertEquals(tipo, campo9);
        Assert.assertEquals(zona, campo10);
        Assert.assertEquals(correspondencia, campo11);
    }

    @Quando("o usuário preenche os campos {string} {string} {string} {string} e clica sobre o botão Adicionar")
    public void oUsuárioPreencheOsCamposEClicaSobreOBotãoAdicionar(String cep, String numero, String
            complemento, String zona) {
        dsl.escrever(By.id("cep:cep:suggestion_input"), cep);
        dsl.esperarLoad();
        dsl.clicarBotao(By.xpath("//*[@id=\"cep:cep:suggestion_panel\"]//td"));
        dsl.esperarElementoVisivel(By.id("cidade:cidade:sgClear"), 10);
        dsl.escrever(By.id("numeroCadastrado:numeroCadastrado:inputId_input"), numero);
        dsl.escrever(By.id("complementoCadastrado:complementoCadastrado:inputId"), complemento);
        dsl.clicarBotao(By.id("tipoZona:tipoZona:inputId_label"));

        int option = 0;
        if (zona.equals("Urbana")) {
            option = 2;
        } else if (zona.equals("Rural")) {
            option = 3;
        }

        dsl.clicarBotao(By.xpath("//*[@id='tipoZona:tipoZona:inputId_panel']/div/ul/li[" + option + "]"));

        dsl.clicarBotao(By.id("bt_incluirCadastrado:button"));
    }

    @Quando("o usuário preenche os campos estrangeiros {string} {string} {string} {string} e clica sobre o botão Adicionar")
    public void oUsuarioPreencheOsCamposEstrangeirosEClicaSobreOBotaoAdicionar(String logradouro, String cidade, String estado, String tipo) {
        dsl.escrever(By.id("cidadeEstrangeiro:cidadeEstrangeiro:inputId"), cidade);
        dsl.escrever(By.id("estadoEstrangeiro:estadoEstrangeiro:inputId"), estado);
        dsl.escrever(By.id("logradouroLivre:logradouroLivre:inputId"), logradouro);
        // Clica na combobox para selecionar o 'tipo'
        dsl.clicarBotao(By.xpath("//div[@id='tipoCadastrado:tipoCadastrado:inputId']/div[3]/span"));
        int option = 0;
        if (tipo.equals("Comercial")) {
            option = 2;
        } else if (tipo.equals("Residencial")) {
            option = 3;
        } else if (tipo.equals("Contato")) {
            option = 4;
        } else if (tipo.equals("Outros")) {
            option = 5;
        }
        dsl.clicarBotao(By.xpath("//div[@id='tipoCadastrado:tipoCadastrado:inputId_panel']/div/ul/li[" + option + "]"));
        dsl.clicarBotao(By.id("bt_incluirCadastrado:button"));
    }

    @Então("o sistema exibe a mensagem {string}")
    public void oSistemaExibeAMensagem(String mensagem) {
        dsl.esperarElementoVisivel(By.id("messagesInDialog"), 10);
        Assert.assertEquals(mensagem, dsl.obterTexto(By.id("messagesInDialog")));
    }

    @Então("adiciona o endereço na tabela com as colunas {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void adicionaOEndereçoNaTabelaComAsColunas(String acao, String cep, String logradouro, String
            numero, String complemento, String pais, String bairro, String munCidade, String codIbge, String
                                                              ufEstado, String tipo, String zona, String correspondencia) {
        this.verificarItenTabela(acao);
        this.verificarItenTabela(cep);
        this.verificarItenTabela(logradouro);
        this.verificarItenTabela(numero);
        this.verificarItenTabela(complemento);
        this.verificarItenTabela(pais);
        this.verificarItenTabela(bairro);
        this.verificarItenTabela(munCidade);
        this.verificarItenTabela(codIbge);
        this.verificarItenTabela(ufEstado);
        this.verificarItenTabela(tipo);
        this.verificarItenTabela(zona);
        this.verificarItenTabela(correspondencia);
    }

    public boolean verificarItenTabela(String nomeColuna) {
        boolean contrl = false;
        for (int i = 1; i < 13; i++) {
            WebElement coluna = driver.findElement(By.xpath("//thead[@id=\"tabelaEnderecos:resultList_head\"]/tr/th[" + i + "]/span"));
            String verificaColuna = coluna.getText();
            if (verificaColuna.equals(nomeColuna)) {
                contrl = true;
                break;
            } else {
                contrl = false;
            }
        }
        return contrl;
    }

    @Dado("que o sistema exiba no box {string} o novo campo {string}")
    public void queOSistemaExibaNoBoxONovoCampo(String textoBox, String campo) {
        String texto = dsl.obterTexto(By.xpath("//*[@id=\"panelCadastradoResponsavelPaciente\"]/legend"));
        Assert.assertEquals(textoBox, texto);
        String campoTela = dsl.obterTexto(By.xpath("//form[3]/fieldset[6]//div[2]/div[1]/div[1]/label"));
        Assert.assertEquals(campo, campoTela);
    }

    @Então("eu preencho no campo CPF {string}")
    public void euPreenchoNoCampoCpf(String cpf) {
        dsl.escrever(By.id("cpfResponsavel:cpfResponsavel:inputId"), cpf);
        dsl.clicarBotao(By.id("ddd_fone_responsavel:ddd_fone_responsavel:inputId_input"));
        dsl.esperarLoad();
        Assert.assertEquals(cpf, dsl.obterValueElemento(By.id("cpfResponsavel:cpfResponsavel:inputId")));
    }

    @Dado("que eu preencha o campo CPF do Responsável com um valor inválido {string}")
    public void queEuPreenchaOCampoCPFDoResponsávelComUmValorInválido(String cpf) {
        dsl.escrever(By.id("cpfResponsavel:cpfResponsavel:inputId"), cpf);
        dsl.clicarBotao(By.id("ddd_fone_responsavel:ddd_fone_responsavel:inputId_input"));
        dsl.esperarLoad();
            }

    @Então("o sistema deve exibir {string} de alerta")
    public void oSistemaDeveExibirDeAlerta(String alerta) {
        dsl.esperarElementoVisivel(By.id("messagesInDialog"), 10);
        String mensagem = dsl.obterTexto(By.id("messagesInDialog"));
        Assert.assertEquals(alerta, mensagem);
        Assert.assertEquals("", dsl.obterTexto(By.id("cpfResponsavel:cpfResponsavel:inputId")));
    }

    @Dado("o sistema exibe no box Documentos a label do campo Data alterada para {string}")
    public void oSistemaExibeNoBoxDocumentosALabelDoCampoDataAlteradaPara(String campoData) {
        String nomeAtual = dsl.obterTexto(By.xpath("//fieldset[7]//div[1]/span/div[3]//label"));
        Assert.assertEquals(campoData, nomeAtual);
    }
    @Dado("o sistema exibe no box Cartão SUS a label do campo Número alterada para {string}")
    public void oSistemaExibeNoBoxCartãoSUSALabelDoCampoNúmeroAlteradaPara(String cns) {
        String nomeAtual = dsl.obterTexto(By.xpath("//fieldset[10]//div[1]/div[1]/div[1]/label"));
        Assert.assertEquals(cns, nomeAtual);
    }
    @Então("eu preencho no campo Número do CNS com {string}")
    public void euPreenchoNoCampoNúmeroDoCNSCom(String numero) {
        dsl.escrever(By.id("cartaosus:cartaosus:inputId"), numero);
        dsl.clicarBotao(By.id("cartaoNacionalSaudeMae:cartaoNacionalSaudeMae:inputId_input"));
        String numeroCampo = dsl.obterValorCampo("cartaosus:cartaosus:inputId");
        Assert.assertEquals(numero, numeroCampo);
    }

}
