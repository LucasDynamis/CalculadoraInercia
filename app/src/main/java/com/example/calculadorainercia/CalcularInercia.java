package com.example.calculadorainercia;

public class CalcularInercia {

    // Método para calcular o momento de inércia
    public static double calcularInercia(double raio, double altura, double massa, boolean ehSolido, String tipoObjeto, String eixoRotacao) {
        switch (tipoObjeto) {
            case "Cilindro":
                return calcularInerciaCilindro(raio, altura, massa, ehSolido, eixoRotacao);
            case "Esfera":
                return calcularInerciaEsfera(raio, massa);
            case "Disco":
                return calcularInerciaDisco(raio, massa);
            default:
                return 0;
        }
    }

    // Métodos específicos para cada tipo de objeto
    private static double calcularInerciaCilindro(double raio, double altura, double massa, boolean ehSolido, String eixoRotacao) {
        if (eixoRotacao.equals("Perpendicular")) {
            if (ehSolido) {
                return (1.0 / 12.0) * massa * (3 * raio * raio + altura * altura);
            } else {
                return (1.0 / 2.0) * massa * raio * raio;  // Simplificação para cilindro oco
            }
        } else { // Eixo longitudinal
            if (ehSolido) {
                return (1.0 / 2.0) * massa * raio * raio;
            } else {
                return massa * raio * raio;  // Simplificação para cilindro oco
            }
        }
    }

    private static double calcularInerciaEsfera(double raio, double massa) {
        return (2.0 / 5.0) * massa * raio * raio;
    }

    private static double calcularInerciaDisco(double raio, double massa) {
        return (1.0 / 2.0) * massa * raio * raio;
    }

    // Método para calcular o volume
    public static double calcularVolume(String tipoObjeto, double raio, double altura) {
        switch (tipoObjeto) {
            case "Cilindro":
                return Math.PI * raio * raio * altura;
            case "Esfera":
                return (4.0 / 3.0) * Math.PI * Math.pow(raio, 3);
            case "Disco":
                double espessura = 0.1;  // Suposição de espessura padrão para discos
                return Math.PI * raio * raio * espessura;
            default:
                return 0;
        }
    }
}
