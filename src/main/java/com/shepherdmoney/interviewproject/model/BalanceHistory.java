package com.shepherdmoney.interviewproject.model;

import java.time.LocalDate;

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
public class BalanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private LocalDate date;

    private double balance;

    @ManyToOne
    @JoinColumn(name = "creditCardId")
    CreditCard creditCard;

    public int compareTo(BalanceHistory x) {
        return this.date.compareTo(x.date);
    }

}
