package org.example;

import org.example.ci.CadeadoCLI;
import org.example.processor.CadeadoProcessor;

public class Main {
    public static void main(String[] args) {
        CadeadoCLI cli = new CadeadoCLI();
        CadeadoProcessor processor = new CadeadoProcessor();
        CadeadoHandler handler = new CadeadoHandler(processor, cli);

        handler.iniciar();
    }
}