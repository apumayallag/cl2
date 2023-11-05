package com.cibertec;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.*;

import java.util.Date;
import java.util.List;

public class App {

    static EntityManagerFactory factory = null;
    static EntityManager manager = null;
    static EntityTransaction tx = null;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory("default");
        manager = factory.createEntityManager();
        tx = manager.getTransaction();

        tx.begin();

        Cliente cliente = new Cliente();
        cliente.setNombres("Alexis");
        cliente.setApellidoPaterno("Pumayalla");
        cliente.setApellidoMaterno("Giove");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("123456789");
        cuenta.setSaldo(8000d);
        cuenta.setActiva(true);

        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setMoneda("PEN");
        tipoCuenta.setDescripcion("Cuenta Sueldo");

        Movimiento movimiento = new Movimiento();
        movimiento.setDescripcion("Deposito sueldo");
        movimiento.setFecha(new Date());
        movimiento.setMonto(5000d);

        Rol rolT = new Rol();
        rolT.setDescripcion("Titular");
        rolT.setActivo(true);

        Rol rolM = new Rol();
        rolM.setDescripcion("Mancomunado");
        rolM.setActivo(true);


        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.getMovimientos().add(movimiento);
        cuenta.setCliente(cliente);
        movimiento.setCuenta(cuenta);

        cliente.getCuentas().add(cuenta);
        cliente.getRoles().add(rolT);
        cliente.getRoles().add(rolM);

        manager.persist(cliente);

        tx.commit();

        List<Cliente> lst = manager.createQuery("from Cliente", Cliente.class).getResultList();
        for (Cliente bean : lst) {
            System.out.println(bean.toString());
        }
        manager.close();
        factory.close();
    }
}
