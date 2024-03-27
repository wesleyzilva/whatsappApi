package com.example.whatsapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Iniciando o programa.");

        // Ler números de telefone e mensagem do arquivo
        List<String> telefones = lerArquivo("telefones.txt");
        String mensagem = lerArquivo("mensagem.txt").get(0); // Supõe que haja apenas uma linha na mensagem

        // Configurar o WebDriver do Edge
        System.setProperty("webdriver.edge.driver", "C:/dev/edgedriver/msedgedriver.exe");
        WebDriver driver = new EdgeDriver();

        // Iterar sobre os números de telefone
        for (String telefone : telefones) {
            // Construir a URL do WhatsApp
            String url = "https://api.whatsapp.com/send?phone=" + telefone + "&text=" + mensagem;
            logger.info("URL da API: {}", url);

            // Navegar para a URL do WhatsApp
            driver.get(url);

            // Aguardar até que o botão "Iniciar conversa" esteja visível
            WebDriverWait wait = new WebDriverWait(driver, 60);
            WebElement iniciarConversaButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'button')]")));

            // Clicar no botão "Iniciar conversa"
            iniciarConversaButton.click();

            // Aguardar 5 segundos antes de enviar a próxima mensagem
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.error("Erro ao aguardar.", e);
                Thread.currentThread().interrupt();
            }
        }

        // Fechar o navegador
        driver.quit();

        logger.info("Programa concluído.");
    }

    private static List<String> lerArquivo(String nomeArquivo) {
        try {
            logger.info("Lendo arquivo: {}", nomeArquivo);
            return Files.readAllLines(Paths.get(nomeArquivo));
        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo {}.", nomeArquivo, e);
            return List.of();
        }
    }
}
