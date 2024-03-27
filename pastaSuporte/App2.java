import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Iniciando o programa.");

        try {
            List<String> telefones = lerArquivo("numeros.txt");
            String mensagem = lerArquivo("mensagem.txt").get(0); // Supõe que haja apenas uma linha na mensagem

            for (String telefone : telefones) {
                enviarMensagem(telefone, mensagem);
            }
        } catch (IOException e) {
            logger.error("Erro ao ler arquivos.", e);
        }

        logger.info("Programa concluído.");
    }

    private static List<String> lerArquivo(String nomeArquivo) throws IOException {
        logger.info("Lendo arquivo: {}", nomeArquivo);
        Path path = Paths.get(nomeArquivo);
        return Files.readAllLines(path);
    }

    private static void enviarMensagem(String telefone, String mensagem) {
        logger.info("Enviando mensagem para o telefone {}: {}", telefone, mensagem);
        // Código para enviar mensagem via API do WhatsApp
        String url = "https://api.whatsapp.com/send?phone=" + telefone + "&text=" + mensagem;
        logger.info("URL da API: {}", url);

        // Inicializar o driver do Chrome
        System.setProperty("webdriver.chrome.driver", "C:/dev/chromedriver/");
        WebDriver driver = new ChromeDriver();

        // Abrir o WhatsApp Web
        driver.get("https://web.whatsapp.com");

        // Aguardar até que o usuário faça login
        // (Você pode implementar um mecanismo para esperar até que um elemento específico seja visível)

        // Abrir a conversa com o número de telefone especificado
        driver.get(url);

        // Aguardar até que a página seja carregada completamente
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));

        // Clicar no botão "Iniciar conversa"
        driver.findElement(By.cssSelector("[title^='Novo']")).click();

        // Fechar o navegador
        driver.quit();
    }
}
