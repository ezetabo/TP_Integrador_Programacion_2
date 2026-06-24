package tpi.prog2.service;

import java.time.LocalDate;
import java.util.List;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Estado;
import tpi.prog2.enums.FormaPago;
import tpi.prog2.menu.InputReader;

public class PedidoService {

    public static Pedido crear(LocalDate fecha, Estado estado, FormaPago formaPago) {
        return new Pedido(fecha, estado, formaPago);
    }

    public static Pedido crear() {
        LocalDate fecha = InputReader.leerFecha("Ingrese la fecha (dd/MM/yyyy): ");
        Estado estado = InputReader.leerEstado();
        FormaPago formaPago = InputReader.leerFormaPago();

        return crear(fecha, estado, formaPago);
    }

    public static void listarConListado(List<Pedido> lista) {
        for (Pedido elemento : lista) {
            if (!elemento.isEliminado()) {
                System.out.println(elemento.infoConListado());
            }
        }
    }

    public static void listarPorUsuario(List<Usuario>usuarios) {        
        Usuario u = UsuarioService.obtnerUno(usuarios, "consultar");
        System.out.println(u.infoConListado());
    }
}
