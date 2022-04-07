package br.rnp.automacao.runners;


import io.cucumber.java.After;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.junit.runner.RunWith;


    @RunWith(Cucumber.class)
    @CucumberOptions
            (
            features = "src/main/resources/features",
            glue = {"br.rnp.automacao.steps"},
            tags = "not @ignore",
            plugin = { "pretty", "html:target/report-html" }, // Apresenta as informações no resultado da execução.
            monochrome = true, // Tira os caracteres especiais do resultado da execução.
            snippets = SnippetType.CAMELCASE, /* Tira o underline das frase e coloca a primeira letra em maiúsculo. */
            dryRun = false // Quando for true ele ignora o código e testa a estrutura.
    )
    public class Runner {

}


