package com.muvlin.app;

public class Calculos {

    private Double impuestos = 19.2;
    private Double tipoCambio = 6.96;
    private Double factorIVA = 0.13;
    private Double factorIT = 0.03;
    private Double factorIUE = 0.032;

    public Double calculaPrecio(Double precioProveedor, Float margenCotizacion) {
        Double precioCalculado = (precioProveedor / ((100 - margenCotizacion)/100)) / ((100 - impuestos)/100);
        Double precioCliente = Math.round(precioCalculado * 100.0) / 100.0;

        Double IVA = (precioCliente - precioProveedor) * factorIVA;
        Double IT = precioCliente * factorIT;
        Double IUE = precioCliente * factorIUE;

        Double ganacia = precioCliente - precioProveedor - IVA - IT - IUE;

        return precioCliente;
    }
}
