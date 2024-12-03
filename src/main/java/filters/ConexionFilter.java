package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import service.ServiceJdbcException;
import utils.ConexionBaseDatos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//Creamos una anotración diciendole que esta clase le voy
// a utilizar en cualquier parte de mi proyecto que necesite
@WebFilter("/*")
public class ConexionFilter implements Filter {
    /*
    *
    * Una clase Filter en Java es un objeto que realiza tareas de filtrado en las solicitudes o
    * respuestas a un recurso. Los filtros se pueden ejecutar en servidores web compatibles con Java EE.
    Los filtros interceptan las solicitudes y respuestas de manera dinámica para transformar o utilizar
    * la información que contienen. El filtrado se realiza en el método doFilter.
    * */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*
        * Request: peticion que hace el cliente
        * Response: respuesta Http qeu se enviara al cliente
        * filterchain chain: Es una cada de filtros que representa el flujo de procesamiento.
        * LLamar al método chain.dofilter(request, response) dentro del filtro pasa la solicitud
        * al siguiente filtro o al recurso destino(como un servlet o jsp*/


        //Obtenenmos la conexion
        try(Connection conn = ConexionBaseDatos.getConnection()){
            //Verificamos si la conexión está configurada para realizar aucommit(confirmar automaticamente cada instrucción SQL)

            if (conn.getAutoCommit()){
                //Si esta activa, lo desactivamos ocn setSutocommit(false) para controlar manualmente las transacciones
                conn.setAutoCommit(false);
            }
            try{
                //Agregamos la conexión como un atributo en la solicitud.
                //Esto permite que otros componentes(como servlets o DAOS) puedan acceder a la conexión
                //desde el objeto request
                request.setAttribute("conn",conn);
                //Pasa la solicitud y la respuesta al siguiente filtro o al recurso
                //destino(servlet p JSP)
                chain.doFilter(request,response);
                //SI el procesamiento de la solicitud llega hasta aqui son lanzar excepcione, se confirma
                //la transaccion, apñlicando todos los cambioa realizados a la bse de datos
                conn.commit();
                //Si ocurre algun errordurante el procesmiento (dentro del chain.doFilter), se captura la excepcion
            }catch(SQLException /*e no poner antes del paso 8*/| ServiceJdbcException e){
                //Se deshacen los cambion con un rollback() para garantizar la consistencia de los datos
                conn.rollback();
                //Se envia un c+odigo de error HTTP 500 al cliente, indicando un problema interno en el servidor
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
                e.printStackTrace();
            }
            //Si ocurre un error al abrir la conexión o el bloque try la expeción se registra en el log printStackTrace
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
