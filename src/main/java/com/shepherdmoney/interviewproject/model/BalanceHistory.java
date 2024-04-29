package com.shepherdmoney.interviewproject.model;

import java.time.LocalDate;
import java.util.Comparator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BalanceHistory implements Comparable<BalanceHistory> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private LocalDate date;

    private double balance;

    @ManyToOne
    @JoinColumn(name = "creditCardId")
    CreditCard creditCard;


    @Override
    public int compareTo(BalanceHistory o) {
        return this.date.compareTo(o.date);
    }
}
