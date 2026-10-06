package br.com.zenon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Main {
    void  main () {
        var t1 = new Transaction(1, TransactionType.PAYMENT, new BigDecimal("100.00"),
                                 new TransactionCustumer("origem", new BigDecimal("100.00"), new BigDecimal("100.00")),
                                    new TransactionCustumer("destino", new BigDecimal("100.00"), new BigDecimal("100.00")),
                                false, false);

        var t2 = new Transaction(1, TransactionType.CASH_OUT, new BigDecimal("100.00"),
                new TransactionCustumer("origem", new BigDecimal("100.00"), new BigDecimal("100.00")),
                new TransactionCustumer("destino", new BigDecimal("100.00"), new BigDecimal("100.00")),
                false, false);

        IO.println(t1);
        IO.println(t2);

/*
        TransctionIngestor t3 = new TransctionIngestor();
        t3.setLimite(1000);
        //t1.setArquivo("C:\\Java\\POS\\FundamentosJava\\modulo1-projeto-zenon-bank\\zenon-fraud-detector\\data\\PS_20174392719_1491204439457_log.csv");
        t3.setArquivo("C:\\Java\\POS\\FundamentosJava\\modulo1-projeto-zenon-bank\\zenon-fraud-detector\\data\\dados.csv");
        List<Transaction> list = t3.transctions();
        IO.println(list.size());
        list.stream().forEach(IO::println);
*/

        TransctionIngestor t4 = new TransctionIngestor();
        t4.setArquivo("C:\\Java\\POS\\FundamentosJava\\modulo1-projeto-zenon-bank\\zenon-fraud-detector\\data\\PS_20174392719_1491204439457_log.csv");
        t4.setLimite(50000);
        List<Transaction> list2 = t4.transctions();
        FraudAnalyzer analyzer = new FraudAnalyzer(list2);
        analyzer.obterTotalDeTransacoes();
        analyzer.obterTotalDeFraudes();
        analyzer.topFraudes(500);
        analyzer.fraudePorTipo(500, TransactionType.CASH_OUT);
        analyzer.fraudePorTipo(500, TransactionType.TRANSFER);
        analyzer.fraudePorTipo(500, TransactionType.PAYMENT);
        analyzer.fraudePorTipo(500, TransactionType.DEBIT);
        analyzer.fraudePorTipo(500, TransactionType.CASH_IN);
    }

}
