package it.micael.vanini.cinemille.service;

import java.util.List;

public interface BaseService<M, I> {
    List<M> getAll();

    M getById(I id);

    M insert(M m);

    M update(M m);

    void delete(I id);
}
