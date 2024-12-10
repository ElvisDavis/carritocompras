package service;

import models.Categoria;
import models.Productos;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Productos> listar();
    //implementamos el metodo para añadir
    Optional<Productos> agregarPorId(Long idProducto);
    //Implementamos el método guardar, eliminar y listar la categoria
    void guardar(Productos producto);
    void eliminar(Long id);
    //Listar la categoria
    List<Categoria> listarCategorias();
    Optional<Categoria> porIdCategoria(Long idCategoria);

}
