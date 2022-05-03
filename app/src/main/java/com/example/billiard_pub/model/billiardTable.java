package com.example.billiard_pub.model;

public class billiardTable {
    private String table1;
    private String table2;
    private String table3;

    public billiardTable(String table1, String table2, String table3) {
        this.table1 = table1;
        this.table2 = table2;
        this.table3 = table3;
    }

    public String getTable1() {
        return table1;
    }

    public void setTable1(String table1) {
        this.table1 = table1;
    }

    public String getTable2() {
        return table2;
    }

    public void setTable2(String table2) {
        this.table2 = table2;
    }

    public String getTable3() {
        return table3;
    }

    public void setTable3(String table3) {
        this.table3 = table3;
    }
}
