package tpi.prog2.service;

import java.time.LocalDate;
import java.util.List;
import tpi.prog2.entities.Base;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Producto;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Estado;
import tpi.prog2.enums.FormaPago;
import tpi.prog2.enums.Rol;
import tpi.prog2.exception.ServiceException;
import tpi.prog2.utils.InputReader;

public class BaseService {

    public static void listar(List<? extends Base> lista) {
        for (Base elemento : lista) {
            if (!elemento.isEliminado()) {
                System.out.print(elemento);
            }
        }
    }

    public static boolean tieneActivos(List<? extends Base> lista) {
        if (!lista.isEmpty()) {
            for (Base elemento : lista) {
                if (!elemento.isEliminado()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int submenu(String tipo) {
        String menu = String.format("""
                    
                    === GESTIÓN DE %s ===
                    1. Listar
                    2. Crear
                    3. Editar
                    4. Eliminar
                    0. Volver al menu principal.
                    Seleccione: """, tipo.toUpperCase());

        return InputReader.leerIntEnRango(menu, "Ingrese un número válido.", 0, 4);
    }

    public static int menu() {
        String menu = """
                    
                    === SISTEMA DE PEDIDOS (FOOD STORE) ===
                    1. Categorías
                    2. Productos
                    3. Usuarios
                    4. Pedidos
                    0. Salir
                    Seleccione: """;

        return InputReader.leerIntEnRango(menu, "Ingrese un número válido.", 0, 4);
    }

    public static void cargarDatosIniciales(List<Categoria> categorias, List<Producto> productos,
            List<Usuario> usuarios, List<Pedido> pedidos) {

        try {
            Categoria bebidas = CategoriaService.crear("Bebidas", "Bebidas frías y calientes");
            Categoria panificados = CategoriaService.crear("Panificados", "Productos de panadería");
            Categoria almacen = CategoriaService.crear("Almacén", "Productos básicos de almacén");

            Producto agua = ProductoService.crear("Agua mineral", 1200.0, "Botella de agua mineral 1.5L", 40, "agua.jpg");
            Producto gaseosa = ProductoService.crear("Gaseosa cola", 1800.0, "Botella de gaseosa cola 2.25L", 30, "gaseosa.jpg");
            Producto pan = ProductoService.crear("Pan francés", 950.0, "Pan francés por kilo", 25, "pan.jpg");
            Producto facturas = ProductoService.crear("Facturas", 4200.0, "Docena de facturas surtidas", 15, "facturas.jpg");
            Producto arroz = ProductoService.crear("Arroz", 1600.0, "Paquete de arroz 1kg", 50, "arroz.jpg");
            Producto fideos = ProductoService.crear("Fideos", 1400.0, "Paquete de fideos 500g", 45, "fideos.jpg");

            Usuario usuarioAdmin = UsuarioService.crear("Ezequiel", "Taboada", "ezequiel@foodstore.com", "1122334455", "admin123", Rol.ADMIN);
            Usuario usuarioCliente = UsuarioService.crear("Lucía", "Gómez", "lucia@foodstore.com", "1166778899", "cliente123", Rol.USUARIO);
            Usuario usuarioAdmin2 = UsuarioService.crear("Admin", "Admin", "admin@mail.com", "1122334455", "admin123", Rol.ADMIN);

            Pedido pedido1 = PedidoService.crear(LocalDate.of(2026, 6, 20), Estado.TERMINADO, FormaPago.TARJETA);
            Pedido pedido2 = PedidoService.crear(LocalDate.of(2026, 6, 22), Estado.PENDIENTE, FormaPago.TRANSFERENCIA);
            Pedido pedido3 = PedidoService.crear(LocalDate.of(2026, 6, 22), Estado.CONFIRMADO, FormaPago.EFECTIVO);

            pedido1.setUsuario(usuarioAdmin);
            usuarioCliente.agregarPedido(pedido2);
            usuarioCliente.agregarPedido(pedido3);

            agua.setCategoria(bebidas);
            gaseosa.setCategoria(bebidas);
            pan.setCategoria(panificados);
            facturas.setCategoria(panificados);
            arroz.setCategoria(almacen);
            fideos.setCategoria(almacen);

            pedido1.addDetallePedido(2, 1200.0, agua);
            pedido1.addDetallePedido(1, 900.0, pan);
            pedido2.addDetallePedido(2, 1200.0, gaseosa);
            pedido2.addDetallePedido(3, 6200.0, facturas);
            pedido3.addDetallePedido(2, 1800.0, arroz);
            pedido3.addDetallePedido(1, 1600.0, fideos);

            categorias.add(bebidas);
            categorias.add(panificados);
            categorias.add(almacen);

            pedidos.add(pedido1);
            pedidos.add(pedido2);
            pedidos.add(pedido3);

            usuarios.add(usuarioAdmin);
            usuarios.add(usuarioCliente);
            usuarios.add(usuarioAdmin2);

            productos.add(agua);
            productos.add(gaseosa);
            productos.add(pan);
            productos.add(facturas);
            productos.add(arroz);
            productos.add(fideos);
        } catch (Exception e) {
             throw new ServiceException("Error al intentar cargar datos inciales: " + e.getMessage());
        }

    }

    public static boolean login(List<Usuario> usuarios) {
        try {
            int intentos = 3;
            while (intentos > 0) {
                System.out.println("=== LOGIN ===");
                String mail = InputReader.leerEmail("Mail: ");
                String pass = InputReader.leerCadena("Contraseña: ");
                Usuario u = UsuarioService.obtenerUnoPorMail(usuarios, mail);
                if (u != null && !u.isEliminado() && u.getRol() == Rol.ADMIN && u.getContrasenia().equals(pass)) {
                    System.out.println("Login exitoso.");
                    return true;
                }
                intentos--;
                if (intentos > 0) {
                    System.out.printf("Mail o contraseña incorrectos. Intentos restantes: %d\n", intentos);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al intentar conectar la app: " + e.getMessage());
        }

        return false;
    }
}
