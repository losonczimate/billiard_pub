package com.example.billiard_pub;

public class Appointments {
    private String id;
    private String desc;
    private String kep;
    private boolean foglalte;

    public Appointments(String id, String desc, String kep, boolean foglalte) {
        this.id = id;
        this.desc = desc;
        this.kep = kep;
        this.foglalte = foglalte;
    }
    public Appointments(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKep() {
        return kep;
    }

    public void setKep(String kep) {
        this.kep = kep;
    }

    public boolean isFoglalte() {
        return foglalte;
    }

    public void setFoglalte(boolean foglalte) {
        this.foglalte = foglalte;
    }
}
