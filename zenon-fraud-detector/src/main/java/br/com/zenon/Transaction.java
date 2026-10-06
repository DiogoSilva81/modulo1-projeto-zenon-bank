package br.com.zenon;

import java.math.BigDecimal;

public record Transaction (Integer step, TransactionType type, BigDecimal amount, TransactionCustumer origin,
                           TransactionCustumer recipient, boolean isFraud, boolean isFlaggedFraud

){
    public Transaction {
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Amount must be positive");
        if (origin.newBalance().compareTo(origin.oldBalance()) < 0) throw new IllegalArgumentException("Origin balance must be greater than or equal to the new balance");
        if (recipient.newBalance().compareTo(recipient.oldBalance()) < 0) throw new IllegalArgumentException("Recipient balance must be greater than or equal to the new balance");
        if (origin.name().equals(recipient.name())) throw new IllegalArgumentException("Origin and recipient must be different");
        if (origin.name().equals("destino")) throw new IllegalArgumentException("Origin must be different from 'destino'");
        if (recipient.name().equals("destino")) throw new IllegalArgumentException("Recipient must be different from 'destino'");
        if (origin.name().equals("origem")) throw new IllegalArgumentException("Origin must be different from 'origem'");
        if (recipient.name().equals("origem")) throw new IllegalArgumentException("Recipient must be different from 'origem'");
        if (origin.name().equals("banco")) throw new IllegalArgumentException("Origin must be different from 'banco'");
        if (recipient.name().equals("banco")) throw new IllegalArgumentException("Recipient must be different from 'banco'");
        if (origin.name().equals("zenon")) throw new IllegalArgumentException("Origin must be different from 'zenon'");
        if (recipient.name().equals("zenon")) throw new IllegalArgumentException("Recipient must be different from 'zenon'");
    }
}
