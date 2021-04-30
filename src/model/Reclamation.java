package model;

public class Reclamation {

    private int id;
    private String Name;
    private String Email;
    private int Phone;
    private String Message;
    private boolean Resolu;

    public Reclamation(int id, String name, String email, int phone, String message, boolean resolu) {
        this.id = id;
        Name = name;
        Email = email;
        Phone = phone;
        Message = message;
        Resolu = resolu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isResolu() {
        return Resolu;
    }

    public void setResolu(boolean resolu) {
        Resolu = resolu;
    }
}
