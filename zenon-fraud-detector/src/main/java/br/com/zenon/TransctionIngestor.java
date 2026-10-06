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

    public String getArquivo() {return arquivo;}
    public void setArquivo(String arquivo) {this.arquivo = arquivo;}

    public List<Transaction> transctions(){
        Path path = Path.of(getArquivo());
        try(Stream<String> linhas = Files.lines(path)){
             List<Transaction> trans = linhas
                     // Filter and map lines to transactions
                     .skip(1)
                     .map(TransctionIngestor::criarItem).filter(Objects::nonNull)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .toList();

             return trans;
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    private static Optional<Transaction> criarItem(String linha){
        try{
            String[] campos = linha.split(",");
            Transaction t = new Transaction(
                   Integer.parseInt(campos[0]),
                   TransactionType.valueOf(campos[1]),
                   new BigDecimal(campos[2]),
                   new TransactionCustumer(campos[3], new BigDecimal(campos[4]), new BigDecimal(campos[5])),
                   new TransactionCustumer(campos[6], new BigDecimal(campos[7]), new BigDecimal(campos[8])),
                   Boolean.parseBoolean(campos[9]),
                   Boolean.parseBoolean(campos[10]));
            return Optional.of(t);
        }catch (Exception e){
            System.err.println("Erro ao criar item: " + linha + " - " + e.getMessage());
            return null;
        }
    }
}
