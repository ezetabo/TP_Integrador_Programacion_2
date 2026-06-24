package tpi.prog2.service;

import java.time.LocalDate;
import java.util.List;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Producto;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Estado;
import tpi.prog2.enums.FormaPago;
import tpi.prog2.menu.InputReader;

public class PedidoService {

    public static Pedido crear(LocalDate fecha, Estado estado, FormaPago formaPago) {
        return new Pedido(fecha, estado, formaPago);
    }

    public static void crear(List<Pedido> lista, List<Usuario> usuarios, List<Producto> productos) {
        Usuario u = UsuarioService.obtnerUno(usuarios, "realizar un pedido");
        Pedido pedido = crear(LocalDate.now(), Estado.PENDIENTE, FormaPago.EFECTIVO);
        pedido.setUsuario(u);
        do {
            Producto prod = ProductoService.obtnerUno(productos, "comprar");
            int cantidad = InputReader.leerIntEnRango(String.format("Cuantas unidades de %s quiere?(max: %d): ",
                    prod.getNombre(), prod.getStock()), "ERROR.. El tipo de dato debe ser numerico", 1, prod.getStock());
            prod.setStock(prod.getStock() - cantidad);
            pedido.addDetallePedido(cantidad, prod.getPrecio(), prod);
        } while (ProductoService.existeDisponible(productos)
                && InputReader.confirmar("Desea seguir comprando? (S.si - N.no): "));
        pedido.setFormaPago(InputReader.leerFormaPago());
        System.out.println("El estado actual del pedido es \"PENDIENTE\"");
        if (InputReader.confirmar("Desea cambiarlo? (S.si - N.no): ")) {
            Estado estado = InputReader.leerEstado();
            pedido.setEstado(estado);
        }
        lista.add(pedido);
        System.out.println("Pedido creado exitosamente con ID: "+ pedido.getId()  + " y Estado: "+ pedido.getEstado().getDescripcion() );
    }

    public static void listarConListado(List<Pedido> lista) {
        for (Pedido elemento : lista) {
            if (!elemento.isEliminado()) {
                System.out.println(elemento.infoConListado());
            }
        }
    }

    public static void listarPorUsuario(List<Usuario> usuarios) {
        Usuario u = UsuarioService.obtnerUno(usuarios, "consultar");
        System.out.println(u.infoConListado());
    }
}
