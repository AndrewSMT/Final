package app.entities;

//user entity

public class User {

    private Long id_user;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private int id_status;

    private int id_type;

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", id_status=" + id_status +
                ", id_type=" + id_type +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public int getId_type() {
        return id_type;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id) {
        this.id_user = id;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
