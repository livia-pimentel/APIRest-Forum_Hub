package com.liviapimentel.forumhub.infra.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormatacaoTexto {

    private static final List<String> CONECTIVOS = Arrays.asList("de", "da", "do", "das", "dos", "e");

    public static String formatarNomeProprio(String texto) {
        if (texto == null || texto.isBlank()){
            return texto;
        }

        String[] palavras = texto.trim().toLowerCase().split("\\s+");

        return Arrays.stream(palavras)
                .map(palavra -> {
                    if (CONECTIVOS.contains(palavra)) {
                        return palavra;
                    }
                    return palavra.substring(0, 1).toUpperCase() + palavra.substring(1);
                })
                .collect(Collectors.joining(" "));
    }

    public static String formatarSentenca(String texto) {
        if (texto == null || texto.isBlank()) {
            return texto;
        }

        String resultado = texto.trim().toLowerCase();

        return resultado.substring(0, 1).toUpperCase() + resultado.substring(1);
    }
}
