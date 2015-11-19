package BaseRelacional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author oracle
 */
public class BaseRelacionalE {

    Connection conexion;
    ResultSet rs;

    public static void main(String[] args) {
        BaseRelacionalE bre = new BaseRelacionalE().conectar();
        bre.listado();
    }

    public BaseRelacionalE conectar() {
        try {
            String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:orcl";
            conexion = DriverManager.getConnection(BaseDeDatos, "hr", "hr");
            if (conexion != null) {
                System.out.println("Conexion exitosa!");
            } else {
                System.out.println("Conexion fallida!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    public Connection getConexion() {
        return conexion;
    }
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    public void listado() {
        try {
            PreparedStatement pS = conexion.prepareStatement("select productos.* from productos");
            rs = (ResultSet) pS.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Products product = new Products();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (rsmd.getColumnType(i) == Types.VARCHAR) {
                        if (rsmd.getColumnName(i).equalsIgnoreCase("CODIGO")) {
                            product.setCodigo(rs.getString(i));
                        } else {
                            product.setDescripcion(rs.getString(i));
                        }
                    } else if (rsmd.getColumnType(i) == Types.NUMERIC) {
                        product.setPrezo(rs.getInt(i));
                    }
                }
                System.out.println(product.toString());
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQLException "+ ex);
        }
    }
}
