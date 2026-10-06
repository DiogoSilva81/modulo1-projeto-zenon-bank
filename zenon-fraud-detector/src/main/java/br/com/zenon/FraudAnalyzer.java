package br.com.zenon;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class FraudAnalyzer {
    private List<Transaction> transactions;
/*
OK - Apenas transações onde isFraud == true, imprima o tamanho da lista.

Imprima as 3 fraudes de maior valor (amount).

Obter apenas os nomes dos clientes de origem (nameOrig) dessas fraudes e depois gere uma lista sem repetições (Set ou distinct) com os 5 maiores clientes suspeitos.

Calcule o prejuízo total causado pelas fraudes (soma dos amount).

Conte quantas fraudes ocorreram por tipo de transação (CASH_OUT, TRANSFER, etc...).
*/
    public FraudAnalyzer(List<Transaction> transactions){
        this.transactions = transactions;
    }

    public void obterTotalDeTransacoes(){
        IO.println("Total de linhas "+ this.transactions.stream().count());
    }

    public void obterTotalDeFraudes(){
        long totalFraudes = 0;
        List<Transaction> fraudes = this.transactions.stream().filter(transaction -> transaction.isFlaggedFraud()).toList();
        totalFraudes = fraudes.size();
       IO.println("Total de Fraudes: "+ totalFraudes);
    }

    public void topFraudes(Integer limite){
        List<Transaction> topFraude = this.transactions.stream()
                                            .filter(Transaction::isFlaggedFraud)
                                            .sorted(Comparator.comparing(Transaction::amount, Comparator.reverseOrder()))
                                            .limit(limite).toList();
        IO.println("Top "+limite+" Fraudes: ");
        for (Transaction transaction : topFraude) {
            IO.println(transaction.origin().name()+" - "+ String.format("%.2f", transaction.amount()));
        }
        BigDecimal total = topFraude.stream().map(Transaction::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
        IO.println("Prejuízo total: "+ String.format("%.2f", total));

    }

    public void fraudePorTipo(Integer limite, TransactionType tipo){
        List<Transaction> fraudeTipo = this.transactions.stream()
                .limit(limite)
                .filter(transaction -> transaction.type() == tipo)
                .toList();
        IO.println("Fraudes de "+ tipo.name() +": "+ fraudeTipo.size());
     }
}
