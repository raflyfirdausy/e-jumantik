package id.firdausy.rafly.ejumantik.Model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String keyUser, alamat, email, namaLengkap, nomerHp;

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNomerHp() {
        return nomerHp;
    }

    public void setNomerHp(String nomerHp) {
        this.nomerHp = nomerHp;
    }
}
