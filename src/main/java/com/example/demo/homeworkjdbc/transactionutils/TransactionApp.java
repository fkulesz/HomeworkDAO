package com.example.demo.homeworkjdbc.transactionutils;

import com.example.demo.homeworkjdbc.transactionutils.Transaction;
import com.example.demo.homeworkjdbc.transactionutils.TransactionDao;

import java.util.Date;
import java.util.Scanner;

public class TransactionApp {

    TransactionDao transactionDao = new TransactionDao();
    Scanner scanner = new Scanner(System.in);

    public TransactionApp(){
        while (true) {
            System.out.println("MENU TRANSAKCJI: ");
            System.out.println("0. wyjscie z menu");
            System.out.println("1. wyswietl wszystkie przychody");
            System.out.println("2. wyswietl wszystkie wydatki");
            System.out.println("3. dodaj nową transakcję");
            System.out.println("4. modyfikuj istniejącą transakcję");
            System.out.println("5. usun transakcje");

            String operation = scanner.nextLine();

            switch (operation) {
                case "1":
                    transactionDao.displayTransactions("income");
                    break;
                case "2":
                    transactionDao.displayTransactions("cost");
                    break;
                case "3":
                    transactionDao.addNewTransaction(generateTransaction());
                    break;
                case "4":
                    transactionDao.modifyTransactionByID(selectTransactionId(),generateTransaction());
                    break;
                case "5":
                    transactionDao.deleteTransaction(selectTransactionId());
                    break;
                case "0":
                    transactionDao.close();
                    scanner.close();
                    return;
                default:
                    System.out.println("zła operacja");
                    break;
            }
        }
    }

    private Transaction generateTransaction(){
        System.out.println("Wprowadz rodzaj transakcji:");
        System.out.println("a -> przychod ");
        System.out.println("b -> wydatek");

        String selectedType = scanner.nextLine();
        String type = "";
        switch (selectedType) {
            case "a":
                type = "income";
                break;
            case "b":
                type = "cost";
                break;
            default:
                System.out.println("zły znak");
                break;
        }
        System.out.println("Podaj opis:");
        String description = scanner.nextLine();

        System.out.println("Podaj cene:");
        float amount =  scanner.nextFloat();
        Transaction transaction = new Transaction(type, description, amount, new Date());
        scanner.nextLine();
        return transaction;
    }

    private int selectTransactionId(){
        System.out.println("Podaj ID transakcji do zmiany");
        int selectedTransaction = scanner.nextInt();
        scanner.nextLine();
        return selectedTransaction;
    }
}
