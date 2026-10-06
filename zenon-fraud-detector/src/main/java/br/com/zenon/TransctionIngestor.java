package br.com.zenon;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.zenon.TransactionType.PAYMENT;

public class TransctionIngestor {

    private String arquivo;
    private int limite;
    private Integer nCount = 0;

    public String getArquivo() {return arquivo;}
    public void setArquivo(String arquivo) {this.arquivo = arquivo;}
    public int getLimite() {
        return limite;
    }
    public void setLimite(int limite) {
        this.limite = limite;
    }

    public List<Transaction> transctions(){
        Path path = Path.of(getArquivo());
        try(Stream<String> linhas = Files.lines(path)){
            List<Transaction> trans = linhas
                    .skip(1)
                    .limit(getLimite())
                    .map(this::criarItem)
                    .flatMap(Optional::stream)
                    .toList();
             return trans;
        }catch (IOException e){
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return null;
        }
    }

    private Optional<Transaction> criarItem(String linha){
        this.nCount ++;
        boolean fraude = new Boolean( this.nCount % 2 == 0);
        try{
            String[] campos = linha.split(",");
            Transaction t = new Transaction(
                   Integer.parseInt(campos[0]),
                   TransactionType.valueOf(campos[1]),
                   new BigDecimal(campos[2]),
                   new TransactionCustumer(campos[3], new BigDecimal(campos[4]), new BigDecimal(campos[5])),
                   new TransactionCustumer(campos[6], new BigDecimal(campos[7]), new BigDecimal(campos[8])),
                   Boolean.parseBoolean(campos[9]),
                   //Boolean.parseBoolean(campos[10]));
                   fraude);
            return Optional.of(t);
        }catch (Exception e){
            System.err.println("Erro ao criar item: " + linha + " - " + e.getMessage());
            return null;
        }
    }
}
