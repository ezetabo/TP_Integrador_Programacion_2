package tpi.prog2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.DetallePedido;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Producto;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Estado;
import tpi.prog2.enums.FormaPago;
import tpi.prog2.enums.Rol;
import tpi.prog2.exception.EntityNotFoundException;
import tpi.prog2.exception.ServiceException;
import tpi.prog2.menu.InputReader;


/**
 *
 * @author Ezequiel Taboada
 */
public class Main {

    private static final List<Categoria> categorias = new ArrayList<>();
    private static final List<Producto> productos = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<Pedido> pedidos = new ArrayList<>();

    private static Long contadorCategorias = 1L;
    private static Long contadorProductos = 1L;
    private static Long contadorUsuarios = 1L;
    private static Long contadorPedidos = 1L;

    public static void main(String[] args) {
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

            try {
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
            } catch (RuntimeException e) {
                InputReader.mostrarError(e);
            }

        } while (opcion != 0);
    }

    private static void menuCategorias() {
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

    private static void listarCategorias() {
        boolean existenCategorias = false;

        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()) {
                System.out.println(categoria);
                existenCategorias = true;
            }
        }

        if (!existenCategorias) {
            System.out.println("No hay categorías cargadas.");
        }
    }

    private static void crearCategoria() {
        String nombre = InputReader.leerCadena("Nombre: ");
        String descripcion = InputReader.leerCadena("Descripción: ");

        validarNombreCategoriaDisponible(nombre, null);

        Categoria categoria = new Categoria(
                contadorCategorias,
                false,
                LocalDateTime.now(),
                nombre,
                descripcion
        );

        categorias.add(categoria);
        contadorCategorias++;

        System.out.println("Categoría creada con ID: " + categoria.getId());
    }

    private static void editarCategoria() {
        listarCategorias();

        Long id = InputReader.leerId("ID de categoría: ");
        Categoria categoria = buscarCategoriaPorId(id);

        String nombre = InputReader.leerCadenaOpcional("Nuevo nombre (Enter para mantener): ");
        String descripcion = InputReader.leerCadenaOpcional("Nueva descripción (Enter para mantener): ");

        if (!nombre.isEmpty()) {
            validarNombreCategoriaDisponible(nombre, categoria.getId());
            categoria.setNombre(nombre);
        }

        if (!descripcion.isEmpty()) {
            categoria.setDescripcion(descripcion);
        }

        System.out.println("Categoría actualizada correctamente.");
    }

    private static void eliminarCategoria() {
        listarCategorias();

        Long id = InputReader.leerId("ID de categoría: ");
        Categoria categoria = buscarCategoriaPorId(id);

        if (categoriaTieneProductos(categoria)) {
            throw new ServiceException("No se puede eliminar la categoría porque tiene productos asociados.");
        }

        if (!InputReader.confirmar("¿Confirma eliminar la categoría? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }

        categoria.setEliminado(true);
        System.out.println("Categoría eliminada correctamente.");
    }

    private static void menuProductos() {
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

    private static void listarProductos() {
        boolean existenProductos = false;

        for (Producto producto : productos) {
            if (!producto.isEliminado()) {
                System.out.println(producto);
                existenProductos = true;
            }
        }

        if (!existenProductos) {
            System.out.println("No hay productos cargados.");
        }
    }

    private static void crearProducto() {
        listarCategorias();

        String nombre = InputReader.leerCadena("Nombre: ");
        String descripcion = InputReader.leerCadena("Descripción: ");
        double precio = InputReader.leerDoubleEnRango("Precio: ", "Ingrese un precio válido.", 0, Double.MAX_VALUE);
        int stock = InputReader.leerIntEnRango("Stock: ", "Ingrese un stock válido.", 0, Integer.MAX_VALUE);
        String imagen = InputReader.leerCadena("Imagen: ");
        boolean disponible = InputReader.confirmar("¿Disponible? (S/N): ");
        Long categoriaId = InputReader.leerId("ID de categoría: ");
        Categoria categoria = buscarCategoriaPorId(categoriaId);

        Producto producto = new Producto(
                contadorProductos,
                false,
                LocalDateTime.now(),
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoria
        );

        productos.add(producto);
        contadorProductos++;

        System.out.println("Producto creado con ID: " + producto.getId());
    }

    private static void editarProducto() {
        listarProductos();
        listarCategorias();

        Long id = InputReader.leerId("ID de producto: ");
        Producto producto = buscarProductoPorId(id);

        String nombre = InputReader.leerCadenaOpcional("Nuevo nombre (Enter para mantener): ");
        String descripcion = InputReader.leerCadenaOpcional("Nueva descripción (Enter para mantener): ");
        Double precio = InputReader.leerDoubleOpcionalEnRango("Nuevo precio (Enter para mantener): ", "Ingrese un precio válido.", 0, Double.MAX_VALUE);
        Integer stock = InputReader.leerIntOpcionalEnRango("Nuevo stock (Enter para mantener): ", "Ingrese un stock válido.", 0, Integer.MAX_VALUE);
        String imagen = InputReader.leerCadenaOpcional("Nueva imagen (Enter para mantener): ");
        String disponible = InputReader.leerCadenaOpcional("¿Disponible? (S/N o Enter para mantener): ").toUpperCase();
        Integer categoriaId = InputReader.leerIntOpcionalEnRango("ID de categoría (Enter para mantener): ", "Ingrese un ID válido.", 1, Integer.MAX_VALUE);

        if (!nombre.isEmpty()) {
            producto.setNombre(nombre);
        }

        if (!descripcion.isEmpty()) {
            producto.setDescripcion(descripcion);
        }

        if (precio != null) {
            producto.setPrecio(precio);
        }

        if (stock != null) {
            producto.setStock(stock);
        }

        if (!imagen.isEmpty()) {
            producto.setImagen(imagen);
        }

        if (!disponible.isEmpty()) {
            if (!disponible.equals("S") && !disponible.equals("N")) {
                throw new ServiceException("Debe ingresar S, N o Enter para mantener el valor actual.");
            }
            producto.setDisponible(disponible.equals("S"));
        }

        if (categoriaId != null) {
            Categoria categoria = buscarCategoriaPorId((long) categoriaId);
            producto.setCategoria(categoria);
        }

        System.out.println("Producto actualizado correctamente.");
    }

    private static void eliminarProducto() {
        listarProductos();

        Long id = InputReader.leerId("ID de producto: ");
        Producto producto = buscarProductoPorId(id);

        if (!InputReader.confirmar("¿Confirma eliminar el producto? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }

        producto.setEliminado(true);
        System.out.println("Producto eliminado correctamente.");
    }

    private static void menuUsuarios() {
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

    private static void listarUsuarios() {
        boolean existenUsuarios = false;

        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado()) {
                System.out.println(usuario);
                existenUsuarios = true;
            }
        }

        if (!existenUsuarios) {
            System.out.println("No hay usuarios cargados.");
        }
    }

    private static void crearUsuario() {
        String nombre = InputReader.leerCadena("Nombre: ");
        String apellido = InputReader.leerCadena("Apellido: ");
        String mail = InputReader.leerEmail();
        String celular = InputReader.leerCadena("Celular: ");
        String contrasena = InputReader.leerCadena("Contraseña: ");
        Rol rol = InputReader.leerRol();

        validarMailDisponible(mail, null);

        Usuario usuario = new Usuario(
                contadorUsuarios,
                false,
                LocalDateTime.now(),
                nombre,
                apellido,
                mail,
                celular,
                contrasena,
                rol
        );

        usuarios.add(usuario);
        contadorUsuarios++;

        System.out.println("Usuario creado con ID: " + usuario.getId());
    }

    private static void editarUsuario() {
        listarUsuarios();

        Long id = InputReader.leerId("ID de usuario: ");
        Usuario usuario = buscarUsuarioPorId(id);

        String nombre = InputReader.leerCadenaOpcional("Nuevo nombre (Enter para mantener): ");
        String apellido = InputReader.leerCadenaOpcional("Nuevo apellido (Enter para mantener): ");
        String mail = InputReader.leerCadenaOpcional("Nuevo email (Enter para mantener): ");
        String celular = InputReader.leerCadenaOpcional("Nuevo celular (Enter para mantener): ");
        String contrasena = InputReader.leerCadenaOpcional("Nueva contraseña (Enter para mantener): ");
        String cambiarRol = InputReader.leerCadenaOpcional("¿Modificar rol? (S/N): ").toUpperCase();

        if (!nombre.isEmpty()) {
            usuario.setNombre(nombre);
        }

        if (!apellido.isEmpty()) {
            usuario.setApellido(apellido);
        }

        if (!mail.isEmpty()) {
            validarMailDisponible(mail, usuario.getId());
            usuario.setMail(mail);
        }

        if (!celular.isEmpty()) {
            usuario.setCelular(celular);
        }

        if (!contrasena.isEmpty()) {
            usuario.setContrasena(contrasena);
        }

        if (cambiarRol.equals("S")) {
            usuario.setRol(InputReader.leerRol());
        } else if (!cambiarRol.isEmpty() && !cambiarRol.equals("N")) {
            throw new ServiceException("Debe ingresar S, N o Enter para mantener el rol actual.");
        }

        System.out.println("Usuario actualizado correctamente.");
    }

    private static void eliminarUsuario() {
        listarUsuarios();

        Long id = InputReader.leerId("ID de usuario: ");
        Usuario usuario = buscarUsuarioPorId(id);

        if (!InputReader.confirmar("¿Confirma eliminar el usuario? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }

        usuario.setEliminado(true);
        System.out.println("Usuario eliminado correctamente.");
    }

    private static void menuPedidos() {
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

    private static void listarPedidos() {
        boolean existenPedidos = false;

        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()) {
                System.out.println(pedido);
                existenPedidos = true;

                for (DetallePedido detalle : pedido.getDetalles()) {
                    if (!detalle.isEliminado()) {
                        System.out.println("   " + detalle);
                    }
                }
            }
        }

        if (!existenPedidos) {
            System.out.println("No hay pedidos cargados.");
        }
    }

    private static void crearPedido() {
        listarUsuarios();

        Long usuarioId = InputReader.leerId("ID de usuario: ");
        Usuario usuario = buscarUsuarioPorId(usuarioId);

        Estado estado = InputReader.leerEstado();
        FormaPago formaPago = InputReader.leerFormaPago();

        Pedido pedido = new Pedido(contadorPedidos, estado, formaPago, usuario);
        boolean agregarOtro;

        do {
            listarProductosDisponibles();

            Long productoId = InputReader.leerId("ID de producto: ");
            Producto producto = buscarProductoPorId(productoId);

            int cantidad = InputReader.leerIntEnRango(
                    "Cantidad: ",
                    "Ingrese una cantidad válida.",
                    1,
                    Integer.MAX_VALUE
            );

            pedido.addDetallePedido(cantidad, producto);

            agregarOtro = InputReader.confirmar("¿Agregar otro producto? (S/N): ");

        } while (agregarOtro);

        if (!pedido.tieneDetallesActivos()) {
            throw new ServiceException("El pedido debe tener al menos un detalle.");
        }

        descontarStockDelPedido(pedido);
        pedidos.add(pedido);
        contadorPedidos++;

        System.out.println("Pedido creado con ID: " + pedido.getId());
        System.out.println("Total: $" + pedido.getTotal());
    }

    private static void actualizarPedido() {
        listarPedidos();

        Long id = InputReader.leerId("ID de pedido: ");
        Pedido pedido = buscarPedidoPorId(id);

        Estado estado = InputReader.leerEstado();
        FormaPago formaPago = InputReader.leerFormaPago();

        pedido.setEstado(estado);
        pedido.setFormaPago(formaPago);

        System.out.println("Pedido actualizado correctamente.");
    }

    private static void eliminarPedido() {
        listarPedidos();

        Long id = InputReader.leerId("ID de pedido: ");
        Pedido pedido = buscarPedidoPorId(id);

        if (!InputReader.confirmar("¿Confirma eliminar el pedido? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }

        pedido.setEliminado(true);
        pedido.eliminarDetalles();
        System.out.println("Pedido eliminado correctamente.");
    }

    private static void listarProductosDisponibles() {
        boolean existenProductos = false;

        for (Producto producto : productos) {
            if (!producto.isEliminado() && producto.isDisponible() && producto.getStock() > 0) {
                System.out.println(producto);
                existenProductos = true;
            }
        }

        if (!existenProductos) {
            throw new ServiceException("No hay productos disponibles para crear un pedido.");
        }
    }

    private static Categoria buscarCategoriaPorId(Long id) {
        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado() && categoria.getId().equals(id)) {
                return categoria;
            }
        }

        throw new EntityNotFoundException("No existe una categoría activa con ese id.");
    }

    private static Producto buscarProductoPorId(Long id) {
        for (Producto producto : productos) {
            if (!producto.isEliminado() && producto.getId().equals(id)) {
                return producto;
            }
        }

        throw new EntityNotFoundException("No existe un producto activo con ese id.");
    }

    private static Usuario buscarUsuarioPorId(Long id) {
        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado() && usuario.getId().equals(id)) {
                return usuario;
            }
        }

        throw new EntityNotFoundException("No existe un usuario activo con ese id.");
    }

    private static Pedido buscarPedidoPorId(Long id) {
        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado() && pedido.getId().equals(id)) {
                return pedido;
            }
        }

        throw new EntityNotFoundException("No existe un pedido activo con ese id.");
    }

    private static void validarNombreCategoriaDisponible(String nombre, Long idIgnorado) {
        for (Categoria categoria : categorias) {
            boolean mismoNombre = categoria.getNombre().equalsIgnoreCase(nombre.trim());
            boolean mismoId = idIgnorado != null && categoria.getId().equals(idIgnorado);

            if (!categoria.isEliminado() && mismoNombre && !mismoId) {
                throw new ServiceException("Ya existe una categoría con ese nombre.");
            }
        }
    }

    private static void validarMailDisponible(String mail, Long idIgnorado) {
        for (Usuario usuario : usuarios) {
            boolean mismoMail = usuario.getMail().equalsIgnoreCase(mail.trim());
            boolean mismoId = idIgnorado != null && usuario.getId().equals(idIgnorado);

            if (!usuario.isEliminado() && mismoMail && !mismoId) {
                throw new ServiceException("El email ya está en uso.");
            }
        }
    }

    private static boolean categoriaTieneProductos(Categoria categoria) {
        boolean tieneProductos = false;

        for (Producto producto : productos) {
            if (!producto.isEliminado() && producto.getCategoria().equals(categoria)) {
                tieneProductos = true;
                break;
            }
        }

        return tieneProductos;
    }

    private static void descontarStockDelPedido(Pedido pedido) {
        for (DetallePedido detalle : pedido.getDetalles()) {
            if (!detalle.isEliminado()) {
                detalle.getProducto().descontarStock(detalle.getCantidad());
            }
        }
    }
}
