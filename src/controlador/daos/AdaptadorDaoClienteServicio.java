/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.daos;

import Controlador.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import lista.controlador.Lista;
import modelo.ServiciosCliente;

/**
 *
 * @author Usuario
 */
public class AdaptadorDaoClienteServicio<T> implements InterfazDao<T>{

    private Class <T> clazz;
    private ConexionDB conexionDB = new ConexionDB();
    Connection conexion = conexionDB.conectar();
    private Lista<T> lista = new Lista<>();
    
    public AdaptadorDaoClienteServicio(Class<T> clazz){
        this.clazz = clazz;
        lista.setClazz(clazz);
    }
    
    /**
     * Inserta los datos en la base de datos
     * @param dato
     * @return true si se ha guardado correctamente
     */
    @Override
    public boolean guardar(T dato) {
        ServiciosCliente servicios = (ServiciosCliente) dato;
        try {
            PreparedStatement pst;
            pst = conexion.prepareStatement("INSERT INTO asignarservicios (Cliente,Tipo,Valor,Uso) values (?,?,?,?)");
            pst.setString(1, servicios.getCliente());
            pst.setString(2, servicios.getNombreServicio());
            pst.setDouble(3, servicios.getPrecio());
            pst.setInt(4, servicios.getUso());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error en el guardado de la base "+e);
            return false;
        }
    }

    /**
     * Modifica los datos de la base de datos gracias a una llave Id
     * @param dato
     * @param ID
     * @return true si se ha guardado correctamente
     */
    @Override
    public boolean modificar(T dato, int ID) {
        try {
            ServiciosCliente servicios = (ServiciosCliente) dato;
            PreparedStatement pst;
            pst = conexion.prepareStatement("UPDATE asignarservicios SET Cliente = '"+servicios.getCliente()+"',Tipo = '"+servicios.getNombreServicio()+"',Valor = '"+servicios.getPrecio()+"', Uso = '"+servicios.getUso()+"' WHERE ID='"+ID+"'");
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Guarda los datos de la base de datos en la lista de Servicios Clientes
     * @return Lista de Servicios CLientes
     */
    @Override
    public Lista<T> listar() {
        Statement st = null;
        ResultSet rs = null;
        lista = new Lista<>();
        try {
            st = conexion.createStatement();
            rs = st.executeQuery("SELECT * FROM asignarservicios");
            while(rs.next()){
                ServiciosCliente servicios = new ServiciosCliente();
                servicios.setIdServicio(rs.getLong("ID"));
                servicios.setCliente(rs.getString("Cliente"));
                servicios.setNombreServicio(rs.getString("Tipo"));
                servicios.setPrecio(rs.getDouble("Valor"));
                servicios.setUso(rs.getInt("Uso"));
                lista.insertarNodo((T) servicios);
            }
            
        } catch (Exception e) {
            System.out.println("Error en listar "+e);
        }
        return (lista);
    }

    @Override
    public boolean modificar(T dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean modificar(String dato, String ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
