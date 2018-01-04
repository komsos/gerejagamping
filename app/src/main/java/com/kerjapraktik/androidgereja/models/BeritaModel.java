package com.kerjapraktik.androidgereja.models;


public class BeritaModel {
    private int idBerita;
    private String judulBerita;
    private String tanggalBerita;
    private String isiBerita;
    private int featured_image;
    private String urlImage;
    //untuk mnampilkan di layout

    public int getIdBerita() {
        return idBerita;
    }

    public void setIdBerita(int idBerita) {
        this.idBerita = idBerita;
    }

    public String getJudulBerita() {
        return judulBerita;
    }

    public void setJudulBerita(String judulBerita) {
        this.judulBerita = judulBerita;
    }

    public String getTanggalBerita() {
        return tanggalBerita;
    }

    public void setTanggalBerita(String tanggalBerita) {
        this.tanggalBerita = tanggalBerita;
    }

    public String getIsiBerita() {
        return isiBerita;
    }

    public void setIsiBerita(String isiBerita) {
        this.isiBerita = isiBerita;
    }

    public int getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(int featured_image) {
        this.featured_image = featured_image;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "BeritaModel{" +
                "idBerita=" + idBerita +
                ", judulBerita='" + judulBerita + '\'' +
                ", tanggalBerita='" + tanggalBerita + '\'' +
                ", isiBerita='" + isiBerita + '\'' +
                ", featured_image=" + featured_image +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }
}
