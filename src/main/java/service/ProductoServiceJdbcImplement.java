package service;

import models.Productos;
import repositories.ProductoRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoServiceJdbcImplement implements ProductoService {
    private ProductoRepositoryJdbcImpl repositoryJdbc;

    public ProductoServiceJdbcImplement(Connection connection) {
        this.repositoryJdbc = new ProductoRepositoryJdbcImpl(connection);
    }

    @Override
    public List<Productos> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Productos> agregarPorId(Long idProducto) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(idProducto));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
