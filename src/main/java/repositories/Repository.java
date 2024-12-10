package repositories;

import java.sql.SQLException;
import java.util.List;


/*
* <T> Es un parametro generico que permite que la interfaz sea utilizada con cualquier tipo
* de objeto(entidad) que desees manejar. Esto hace qeu la interfaz sea flexible y retilizable
* para diferentes tipos de datos
* */
public interface Repository <T>{
    /*EL método listar retorna un alista de objetos de tipo genérico T
    * Se usa para obtener todos los registros de una entidad desde la base de datos*/
    List<T> listar() throws SQLException;
    /*
    * El método porId recibe un identificador unico y retorna un objeto de tipo T
    * correspondiente a ese identificador
    * Se usa para buscar un registro especifico por su ID*/
    T porId(Long id) throws SQLException;
    /*Recibe un objeto de tipo T y lo guarda en la base de datos
    * Este método puede ser utilizado para crear o actualizar un registro, dependiendo de si el objeto
    * ya existe en la base de datos*/
    void guardar(T t) throws SQLException;
    /*Recibe un identificado unico y elimina el registro correspondiente de la base de datos*/
    void eliminar(Long idProducto) throws SQLException;
}
