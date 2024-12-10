package repositories;

import models.Categoria;
import models.Productos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImpl implements Repository<Productos> {
    /*Necesitamso una conexion al bbdd la conexion se tiene que pasar al repositorio, se lo
    pasa el service y a su vez el servlet lo obtiene del request de los atributos del request ocn getAtribute
    y se lo pasa al service y el service posteriormente se lo pasa al repositorio
    *   */
    private Connection conn;

    public ProductoRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Productos> listar() throws SQLException {
        List<Productos> productos = new ArrayList<>();
        try(Statement stmt=conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre as categoria FROM producto as p"+
                    " inner join categoria as c ON (p.idcategoria = c.idcategoria) ")){
            while(rs.next()){
                /*Productos p=new Productos();
                p.setIdProducto(rs.getLong("idproducto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCategoria(rs.getString("categoria"));*/
                Productos p = getProductos(rs);
                productos.add(p);
            }

        }
        return productos;
    }



    @Override
    public Productos porId(Long idProducto) throws SQLException {
        Productos productos = null;
        try(PreparedStatement stmt=conn.prepareStatement("SELECT p.* ,c.nombre" +
                " as categoria FROM producto as p " +
                " inner join categoria as c ON(p.idcategoria=c.idcategoria)WHERE p.idproducto=?")){
            stmt.setLong(1, idProducto);
            try(ResultSet rs=stmt.executeQuery()){
                if(rs.next()){
                    productos=getProductos(rs);
                }
            }

        }
        return productos;

    }

    @Override
    public void guardar(Productos productos) throws SQLException {
        String sql;
        if (productos.getIdProducto()!=null && productos.getIdProducto()>0){
            sql="update producto set idcategoria=?, nombre=?, precio=? where idcategoria=? ";
        }else{
            sql="insert into producto (idcategoria, nombre,precio) values (?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1,productos.getCategoria().getIdCategoria());
            stmt.setString(2,productos.getNombre());
            stmt.setDouble(3,productos.getPrecio());
            if(productos.getIdProducto()!=null && productos.getIdProducto()>0){
                stmt.setLong(4,productos.getIdProducto());
            }

            stmt.executeUpdate();


        }

    }

    @Override
    public void eliminar(Long idProducto) throws SQLException {
        String sql="delete from producto where id=?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1,idProducto);
            stmt.executeUpdate();
        }

    }
    private static Productos getProductos(ResultSet rs) throws SQLException {
        Productos p=new Productos();
        p.setIdProducto(rs.getLong("idproducto"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getDouble("precio"));
        Categoria c=new Categoria();
        c.setIdCategoria(rs.getLong("idcategoria"));
        c.setNombre(rs.getString("categoria"));
        p.setCategoria(c);
        return p;
    }
}
