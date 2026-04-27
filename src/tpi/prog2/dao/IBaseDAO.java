/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tpi.prog2.dao;

import java.util.List;

/**
 *
 * @author Administrator
 */
public interface IBaseDAO<T> {

    T crear(T entidad);

    List<T> listar();

    T buscarPorId(Long id);

    void actualizar(T entidad);

    void eliminarLogico(Long id);
}
