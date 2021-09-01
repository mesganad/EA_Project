package ars.cs.miu.edu.services;


import java.util.List;

public interface AirlinesService<T> {
    public List<T> findAll();
    public T findOne(Long id);
    public T update(T t);
    public void delete (Long id);
    public T add(T t);
}
