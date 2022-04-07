package br.rnp.automacao.core;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class DSL {


    private WebDriver driver;

    public DSL(WebDriver driver) {
        this.driver = driver;
    }

    /********* Esperar Elemento ************/

    public void esperarElemento(By by, int time) {
        new WebDriverWait(driver, Duration.ofSeconds(time)).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void esperarElementoVisivel(By by, int time) {
        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void esperarLoad() {
        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//div"), "Aguarde..."));
    }

    public boolean elementoExiste(By by){
        return driver.findElement(by).isDisplayed();
    }


    /********* TextField e TextArea ************/

    public void escrever(By by, String texto) {
        this.esperarElemento(by, 5);
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(texto);
    }

    public String obterValorCampo(String id_campo) {
        return driver.findElement(By.id(id_campo)).getAttribute("value");
    }

    /********* Radio e Check  ************/

    public void clicarRadio(By by) {
        this.esperarElemento(by, 5);
        driver.findElement(by).click();
    }

    public boolean isRadioMarcado(String id) {
        return driver.findElement(By.id(id)).isSelected();
    }

    public void clicarCheck(String id) {
        driver.findElement(By.id(id)).click();
    }

    public boolean isCheckMarcado(String id) {
        return driver.findElement(By.id(id)).isSelected();
    }

    /********* Combo ************/

    public void selecionarCombo(String id, String valor) {
        WebElement element = driver.findElement(By.id(id));
        Select combo = new Select(element);
        combo.selectByVisibleText(valor);
    }

    public void deselecionarCombo(String id, String valor) {
        WebElement element = driver.findElement(By.id(id));
        Select combo = new Select(element);
        combo.deselectByVisibleText(valor);
    }

    public String obterValorCombo(String id) {
        WebElement element = driver.findElement(By.id(id));
        Select combo = new Select(element);
        return combo.getFirstSelectedOption().getText();
    }

    public List<String> obterValoresCombo(String id) {
        WebElement element = driver.findElement(By.id("elementosForm:esportes"));
        Select combo = new Select(element);
        List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
        List<String> valores = new ArrayList<String>();
        for (WebElement opcao : allSelectedOptions) {
            valores.add(opcao.getText());
        }
        return valores;
    }

    public int obterQuantidadeOpcoesCombo(String id) {
        WebElement element = driver.findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();
        return options.size();
    }

    public boolean verificarOpcaoCombo(String id, String opcao) {
        WebElement element = driver.findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();
        for (WebElement option : options) {
            if (option.getText().equals(opcao)) {
                return true;
            }
        }
        return false;
    }

    /********* Botao ************/

    public void clicarBotao(By by) {
        this.esperarElemento(by, 10);
        driver.findElement(by).click();
    }

    /********* Link ************/

    public void clicarTextoLink(String link) {
        this.esperarElemento(By.linkText(link), 5);
        driver.findElement(By.linkText(link)).click();
    }

    /********* Textos ************/

    public String obterTexto(By by) {
        return driver.findElement(by).getText();
    }

    public String obterTexto(String id) {
        return obterTexto(By.id(id));
    }

    public String obterValueElemento(By by) {
        return driver.findElement(by).getAttribute("value");
    }

    /********* Alerts ************/

    public String alertaObterTexto() {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public String alertaObterTextoEAceita() {
        Alert alert = driver.switchTo().alert();
        String valor = alert.getText();
        alert.accept();
        return valor;
    }

    public String alertaObterTextoENega() {
        Alert alert = driver.switchTo().alert();
        String valor = alert.getText();
        alert.dismiss();
        return valor;
    }

    public void alertaEscrever(String valor) {
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(valor);
        alert.accept();
    }

    /********* Frames e Janelas ************/

    public void entrarFrame(String id) {
        driver.switchTo().frame(id);
    }

    public void sairFrame() {
        driver.switchTo().defaultContent();
    }

    public void trocarJanela(String id) {
        driver.switchTo().window(id);
    }

    public void trocarAba(Integer indiceAba) {
        List<String> abas = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(abas.get(indiceAba));
    }

}
