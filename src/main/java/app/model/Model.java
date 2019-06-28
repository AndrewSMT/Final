package app.model;

import app.entities.User;
import app.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    private static Model instance = new Model();
    private List model = new ArrayList();

    public static Model getInstance() {
        return instance;
    }

    private Model() {
    }

    public void add(User user) {
        this.model.add(user);
    }

    public List list() {
        return (List)this.model.stream().map(User::getName).collect(Collectors.toList());
    }
}