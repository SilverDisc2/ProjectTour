package com.example.project;

public class Expenses {
    private String expenseName;
    private  String amount;
    private  String expenseId;

    public Expenses(String expenseId,String expenseName, String amount) {
        this.expenseName = expenseName;
        this.expenseId=expenseId;
        this.amount = amount;
    }

    public Expenses(String expenseName, String amount) {
        this.expenseName = expenseName;
        this.amount = amount;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getAmount() {
        return amount;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }
}
