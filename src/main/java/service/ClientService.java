package service;

import model.Model;

import java.util.List;

public interface ClientService {
    String createJwt (Model model);
    void addMessage (Model model);
    List<Model> getLastMessages (Model model, int count);
}
