package io.upschool.service.Interface;


import java.util.List;

public interface GenericService<T, U> {
    List<U> getAll();

    U save(T request);
}
