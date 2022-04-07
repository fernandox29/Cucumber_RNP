package br.rnp.automacao.steps;

import br.rnp.automacao.core.DSL;
import io.cucumber.java.After;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Login {

    private WebDriver driver;
    private DSL dsl;

    @After(order = 0) //O zero será o último a ser executado. Para o before o zero é o primeiro.
    public void fechar() {
        driver.quit();
    }

    @Dado ("que o usuário esteja na tela da funcionalidade de Realizar Login no AGHU")
    public void queoUsuarioestejaNaTelaDaFuncionalidadeDeRealizarLoginNoAGHU() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://tst.ebserh.rnp.br/aghu/pages/casca/casca.xhtml");
        dsl = new DSL(driver);
        Assert.assertEquals("Acesse o sistema", dsl.obterTexto(By.xpath("//*[@id=\"login-wraper\"]/h1")));
    }
    @Quando ("o usuário acionar a opção Esqueci minha senha")
    public void oUsuarioAciinarAOpcaoEsqueciMinhaSenha(){
        dsl.clicarBotao(By.id("#"));
    }

    @Então("o sistema direciona o usuário para o sistema Serviços TI e valida a {string}, que mantém todo fluxo de troca de senha na Ebserh")
    public void oSistemaDirecionaOUsuarioParaOSistemaServicosTIQueMantemTodoFluxoDeTrocaDeSenhaNaEbserh (String url){
        dsl.trocarAba(1);
        Assert.assertEquals(url, driver.getCurrentUrl());
    }
}
