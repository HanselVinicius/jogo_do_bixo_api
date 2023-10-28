package com.vinicius.br.jogo_do_bixo.models.animals;

import java.time.LocalDate;

public record ListagemAnimalDTO(Long id, String nome, LocalDate lastDayDrawed) {

    public ListagemAnimalDTO(Animal animal) {
        this(animal.getId(), animal.getNome(), animal.getLastDayDrawed());
    }
}
