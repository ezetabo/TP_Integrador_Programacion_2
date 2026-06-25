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
            pedido.setEstado(InputReader.leerEstado());
        }
        lista.add(pedido);
        System.out.println("Pedido creado exitosamente con ID: " + pedido.getId() + " y Estado: " + pedido.getEstado().getDescripcion());
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

    public static Pedido obtnerUno(List<Pedido> lista, String accion) {
        int largo = lista.size();
        for (int i = 0; i < largo; i++) {
            Pedido p = lista.get(i);
            if (!p.isEliminado()) {
                System.out.println((i + 1) + p.info());
            }
        }
        int index = InputReader.leerIntEnRango("Seleccione el numero de pedido que quiere " + accion + ": ",
                "ERROR... El dato debe ser numerico", 1, largo) - 1;
        return lista.get(index);
    }

    public static void actualizar(List<Pedido> lista) {
        boolean volver = true;
        String menu = """
                        1. Actualizar Estado.
                        2. Actualizar Forma de Pago.                       
                        0. Volver al menu de pedidos.
                        Seleccione: 
                        """;
        Pedido p = obtnerUno(lista, "actualizar");
        do {
            int opcion = InputReader.leerIntEnRango(menu, "ERROR.. El dato debe ser numerico", 0, 2);
            switch (opcion) {
                case 1:
                    System.out.println("ACTUAL: [" + p.getEstado().getDescripcion() + "]");
                    p.setEstado(InputReader.leerEstado());
                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 2:
                    System.out.println("ACTUAL: [" + p.getFormaPago().getDescripcion() + "]");
                    p.setFormaPago(InputReader.leerFormaPago());
                    System.out.println("---Actualizacion exitosa---");
                    break;
                default:
                    volver = false;
            }
        } while (volver);
    }
    
        public static void eliminar(List<Pedido> lista) {
        Pedido p = obtnerUno(lista, "eliminar");
        System.out.println("[" + p.info() + "]");

        int borrar = InputReader.leerIntEnRango("Seguro que desea eliminar este pedido? \n1.SI\n2.NO\nSeleccione: ",
                "ERROR.. El dato debe ser numerico.", 1, 2);
        if (borrar == 1) {
            p.setEliminado(true);
            System.out.println("---Eliminacion exitosa---");
        } else {
            System.out.println("---Eliminacion cancelada---");
        }
    }
}
