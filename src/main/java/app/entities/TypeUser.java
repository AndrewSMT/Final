package app.entities;

//Type user entity

public enum TypeUser {
    NOROLE, ADMIN, CLIENT;

    public static TypeUser getTypeUser(User user) {
        int id_type = user.getId_type();
        return TypeUser.values()[id_type];
    }

    public String getName() {
        return name().toLowerCase();
    }

}