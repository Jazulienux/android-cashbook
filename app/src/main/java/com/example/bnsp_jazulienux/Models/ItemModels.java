package com.example.bnsp_jazulienux.Models;

public class ItemModels {

    private String cash, keterangan,tgl;
    private int imgItem;

    public ItemModels(String cash, String keterangan, String tgl, int imgItem) {
        this.cash = cash;
        this.keterangan = keterangan;
        this.tgl = tgl;
        this.imgItem = imgItem;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public int getImgItem() {
        return imgItem;
    }

    public void setImgItem(int imgItem) {
        this.imgItem = imgItem;
    }
}
