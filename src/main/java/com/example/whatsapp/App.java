package com.example.whatsapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    }
}
