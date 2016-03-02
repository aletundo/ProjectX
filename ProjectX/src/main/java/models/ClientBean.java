package models;

public class ClientBean {

    private int idClient;
    private String name;
    private String mail;

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "ClientBean [idClient=" + idClient + ", name=" + name + ", mail=" + mail + "]";
    }

}
