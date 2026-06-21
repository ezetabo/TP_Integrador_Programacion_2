package tpi.prog2.menu;

import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.DetallePedido;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Producto;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Estado;
import tpi.prog2.enums.FormaPago;
import tpi.prog2.enums.Rol;
import tpi.prog2.service.CategoriaService;
import tpi.prog2.service.PedidoService;
import tpi.prog2.service.ProductoService;
import tpi.prog2.service.UsuarioService;

/**
 *
 * @author Ezequiel Taboada
 */
public class AppMenu {
    private final CategoriaService categoriaService = new CategoriaService();
    private final ProductoService productoService = new ProductoService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final PedidoService pedidoService = new PedidoService();

    public void iniciar() {
        int opcion;

        do {
            String menu = """
                    
                    === SISTEMA DE PEDIDOS (FOOD STORE) ===
                    1. Categorías
                    2. Productos
                    3. Usuarios
                    4. Pedidos
                    0. Salir
                    Seleccione: """;

            opcion = InputReader.leerIntEnRango(menu, "Ingrese un número válido.", 0, 4);

            switch (opcion) {
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    menuProductos();
                    break;
                case 3:
                    menuUsuarios();
                    break;
                case 4:
                    menuPedidos();
                    break;
                case 0:
                    System.out.println("Sistema finalizado.");
                    break;
            }

        } while (opcion != 0);
    }

    private void menuCategorias() {
        int opcion;

        do {
            String menu = """
                    
                    === GESTIÓN DE CATEGORÍAS ===
                    1. Listar
                    2. Crear
                    3. Editar
                    4. Eliminar
                    0. Volver
                    Seleccione: """;

            opcion = InputReader.leerIntEnRango(menu, "Ingrese un número válido.", 0, 4);

            try {
                switch (opcion) {
                    case 1:
                        listarCategorias();
                        break;
                    case 2:
                        crearCategoria();
                        break;
                    case 3:
                        editarCategoria();
                        break;
                    case 4:
                        eliminarCategoria();
                        break;
                    case 0:
                        break;
                }
            } catch (RuntimeException e) {
                InputReader.mostrarError(e);
            }

        } while (opcion != 0);
    }

    private void listarCategorias() {
        List<Categoria> categorias = categoriaService.listar();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    private void crearCategoria() {
        String nombre = InputReader.leerCadena("Nombre: ");
        String descripcion = InputReader.leerCadena("Descripción: ");

        Categoria categoria = categoriaService.crear(nombre, descripcion);
        System.out.println("Categoría creada con ID: " + categoria.getId());
    }

    private void editarCategoria() {
        listarCategorias();

        Long id = InputReader.leerId("ID de categoría: ");
        String nombre = InputReader.leerCadena("Nuevo nombre: ");
        String descripcion = InputReader.leerCadena("Nueva descripción: ");

        categoriaService.actualizar(id, nombre, descripcion);
        System.out.println("Categoría actualizada correctamente.");
    }

    private void eliminarCategoria() {
        listarCategorias();

        Long id = InputReader.leerId("ID de categoría: ");

        if (!InputReader.confirmar("¿Confirma eliminar la categoría? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }

        categoriaService.eliminarLogico(id);
        System.out.println("Categoría eliminada correctamente.");
    }

    private void menuProductos() {
        int opcion;

        do {
            String menu = """
                    
                    === GESTIÓN DE PRODUCTOS ===
                    1. Listar
                    2. Crear
                    3. Editar
                    4. Eliminar
                    0. Volver
                    Seleccione: """;

            opcion = InputReader.leerIntEnRango(menu, "Ingrese un número válido.", 0, 4);

            try {
                switch (opcion) {
                    case 1:
                        listarProductos();
                        break;
                    case 2:
                        crearProducto();
                        break;
                    case 3:
                        editarProducto();
                        break;
                    case 4:
                        eliminarProducto();
                        break;
                    case 0:
                        break;
                }
            } catch (RuntimeException e) {
                InputReader.mostrarError(e);
            }

        } while (opcion != 0);
    }

    private void listarProductos() {
        List<Producto> productos = productoService.listar();

        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    private void crearProducto() {
        listarCategorias();

        String nombre = InputReader.leerCadena("Nombre: ");
        String descripcion = InputReader.leerCadena("Descripción: ");
        double precio = InputReader.leerDoubleEnRango("Precio: ", "Ingrese un precio válido.", 0, Double.MAX_VALUE);
        int stock = InputReader.leerIntEnRango("Stock: ", "Ingrese un stock válido.", 0, Integer.MAX_VALUE);
        String imagen = InputReader.leerCadena("Imagen: ");
        boolean disponible = InputReader.confirmar("¿Disponible? (S/N): ");
        Long categoriaId = InputReader.leerId("ID de categoría: ");

        Producto producto = productoService.crear(
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoriaId
        );

        System.out.println("Producto creado con ID: " + producto.getId());
    }

    private void editarProducto() {
        listarProductos();
        listarCategorias();

        Long id = InputReader.leerId("ID de producto: ");
        String nombre = InputReader.leerCadena("Nuevo nombre: ");
        String descripcion = InputReader.leerCadena("Nueva descripción: ");
        double precio = InputReader.leerDoubleEnRango("Nuevo precio: ", "Ingrese un precio válido.", 0, Double.MAX_VALUE);
        int stock = InputReader.leerIntEnRango("Nuevo stock: ", "Ingrese un stock válido.", 0, Integer.MAX_VALUE);
        String imagen = InputReader.leerCadena("Nueva imagen: ");
        boolean disponible = InputReader.confirmar("¿Disponible? (S/N): ");
        Long categoriaId = InputReader.leerId("ID de categoría: ");

        productoService.actualizar(
                id,
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoriaId
        );

        System.out.println("Producto actualizado correctamente.");
    }

    private void eliminarProducto() {
        listarProductos();

        Long id = InputReader.leerId("ID de producto: ");

        if (!InputReader.confirmar("¿Confirma eliminar el producto? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }

        productoService.eliminarLogico(id);
        System.out.println("Producto eliminado correctamente.");
    }

    private void menuUsuarios() {
        int opcion;

        do {
            String menu = """
                    
                    === GESTIÓN DE USUARIOS ===
                    1. Listar
                    2. Crear
                    3. Editar
                    4. Eliminar
                    0. Volver
                    Seleccione: """;

            opcion = InputReader.leerIntEnRango(menu, "Ingrese un número válido.", 0, 4);

            try {
                switch (opcion) {
                    case 1:
                        listarUsuarios();
                        break;
                    case 2:
                        crearUsuario();
                        break;
                    case 3:
                        editarUsuario();
                        break;
                    case 4:
                        eliminarUsuario();
                        break;
                    case 0:
                        break;
                }
            } catch (RuntimeException e) {
                InputReader.mostrarError(e);
            }

        } while (opcion != 0);
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listar();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void crearUsuario() {
        String nombre = InputReader.leerCadena("Nombre: ");
        String apellido = InputReader.leerCadena("Apellido: ");
        String mail = InputReader.leerEmail();
        String celular = InputReader.leerCadena("Celular: ");
        String contrasena = InputReader.leerCadena("Contraseña: ");
        Rol rol = InputReader.leerRol();

        Usuario usuario = usuarioService.crear(nombre, apellido, mail, celular, contrasena, rol);
        System.out.println("Usuario creado con ID: " + usuario.getId());
    }

    private void editarUsuario() {
        listarUsuarios();

        Long id = InputReader.leerId("ID de usuario: ");
        String nombre = InputReader.leerCadena("Nuevo nombre: ");
        String apellido = InputReader.leerCadena("Nuevo apellido: ");
        String mail = InputReader.leerEmail();
        String celular = InputReader.leerCadena("Nuevo celular: ");
        String contrasena = InputReader.leerCadena("Nueva contraseña: ");
        Rol rol = InputReader.leerRol();

        usuarioService.actualizar(id, nombre, apellido, mail, celular, contrasena, rol);
        System.out.println("Usuario actualizado correctamente.");
    }

    private void eliminarUsuario() {
        listarUsuarios();

        Long id = InputReader.leerId("ID de usuario: ");

        if (!InputReader.confirmar("¿Confirma eliminar el usuario? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }

        usuarioService.eliminarLogico(id);
        System.out.println("Usuario eliminado correctamente.");
    }

    private void menuPedidos() {
        int opcion;

        do {
            String menu = """
                    
                    === GESTIÓN DE PEDIDOS ===
                    1. Listar
                    2. Crear pedido con detalles
                    3. Actualizar estado / forma de pago
                    4. Eliminar
                    0. Volver
                    Seleccione: """;

            opcion = InputReader.leerIntEnRango(menu, "Ingrese un número válido.", 0, 4);

            try {
                switch (opcion) {
                    case 1:
                        listarPedidos();
                        break;
                    case 2:
                        crearPedido();
                        break;
                    case 3:
                        actualizarPedido();
                        break;
                    case 4:
                        eliminarPedido();
                        break;
                    case 0:
                        break;
                }
            } catch (RuntimeException e) {
                InputReader.mostrarError(e);
            }

        } while (opcion != 0);
    }

    private void listarPedidos() {
        List<Pedido> pedidos = pedidoService.listar();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }

        for (Pedido pedido : pedidos) {
            System.out.println(pedido);

            for (DetallePedido detalle : pedido.getDetalles()) {
                System.out.println("   " + detalle);
            }
        }
    }

    private void crearPedido() {
        listarUsuarios();

        Long usuarioId = InputReader.leerId("ID de usuario: ");
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        Estado estado = InputReader.leerEstado();
        FormaPago formaPago = InputReader.leerFormaPago();

        Pedido pedido = new Pedido(estado, formaPago, usuario);

        boolean agregarOtro;

        do {
            listarProductos();

            Long productoId = InputReader.leerId("ID de producto: ");
            Producto producto = productoService.buscarPorId(productoId);

            int cantidad = InputReader.leerIntEnRango(
                    "Cantidad: ",
                    "Ingrese una cantidad válida.",
                    1,
                    Integer.MAX_VALUE
            );

            pedido.addDetallePedido(cantidad, producto.getPrecio(), producto);

            agregarOtro = InputReader.confirmar("¿Agregar otro producto? (S/N): ");

        } while (agregarOtro);

        Pedido creado = pedidoService.crear(pedido);
        System.out.println("Pedido creado con ID: " + creado.getId());
        System.out.println("Total: $" + creado.getTotal());
    }

    private void actualizarPedido() {
        listarPedidos();

        Long id = InputReader.leerId("ID de pedido: ");
        Pedido pedido = pedidoService.buscarPorId(id);

        Estado estado = InputReader.leerEstado();
        FormaPago formaPago = InputReader.leerFormaPago();

        Pedido actualizado = new Pedido(
                pedido.getId(),
                pedido.isEliminado(),
                pedido.getCreatedAt(),
                pedido.getFecha(),
                estado,
                pedido.getTotal(),
                formaPago,
                pedido.getUsuario(),
                pedido.getDetalles()
        );

        pedidoService.actualizar(actualizado);
        System.out.println("Pedido actualizado correctamente.");
    }

    private void eliminarPedido() {
        listarPedidos();

        Long id = InputReader.leerId("ID de pedido: ");

        if (!InputReader.confirmar("¿Confirma eliminar el pedido? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }

        pedidoService.eliminarLogico(id);
        System.out.println("Pedido eliminado correctamente.");
    }
}
