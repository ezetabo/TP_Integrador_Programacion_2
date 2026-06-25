package tpi.prog2;

import java.util.ArrayList;
import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Producto;
import tpi.prog2.entities.Usuario;
import tpi.prog2.utils.InputReader;
import tpi.prog2.service.BaseService;
import tpi.prog2.service.CategoriaService;
import tpi.prog2.service.PedidoService;
import tpi.prog2.service.ProductoService;
import tpi.prog2.service.UsuarioService;

public class Main {

    public static void main(String[] args) {
        int opcion = 0;
        boolean seguirSubmenu;
        List<Categoria> categorias = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();
        List<Pedido> pedidos = new ArrayList<>();

        BaseService.cargarDatosIniciales(categorias, productos, usuarios, pedidos);
        try {
            if (BaseService.login(usuarios)) {
                do {
                    seguirSubmenu = true;
                    opcion = BaseService.menu();
                    switch (opcion) {
                        // Épica 1 – Gestión de Categorías
                        case 1:
                            do {
                                switch (BaseService.submenu("categorias")) {
                                    case 1:
                                        // HU-CAT-01 – Listar categorías
                                        if (BaseService.tieneActivos(categorias)) {
                                            switch (InputReader.leerIntEnRango("""
                                                1. Listar solo categorias.
                                                2. Listar categorias y sus productos.
                                                Seleccione:
                                                """, "ERROR.. El dato debe ser numerico", 1, 2)) {
                                                case 1:
                                                    BaseService.listar(categorias);
                                                    break;
                                                default:
                                                    CategoriaService.listarConListado(categorias);
                                            }
                                        } else {
                                            System.out.println(" ***No hay categorias activas***");
                                        }

                                        break;
                                    case 2:
                                        // HU-CAT-02 – Crear categoría
                                        CategoriaService.crear(categorias);
                                        break;
                                    case 3:
                                        // HU-CAT-03 – Editar categoría
                                        if (BaseService.tieneActivos(categorias)) {
                                            CategoriaService.actualizar(categorias);
                                        } else {
                                            System.out.println(" ***No hay categorias activas***");
                                        }
                                        break;
                                    case 4:
                                        // HU-CAT-04 – Eliminar categoría (baja lógica)
                                        if (BaseService.tieneActivos(categorias)) {
                                            CategoriaService.eliminar(categorias);
                                            ;
                                        } else {
                                            System.out.println(" ***No hay categorias activas***");
                                        }
                                        break;
                                    default:
                                        seguirSubmenu = false;
                                }
                            } while (seguirSubmenu);
                            break;
                        case 2:
                            // Épica 2 – Gestión de Productos
                            do {
                                switch (BaseService.submenu("productos")) {
                                    case 1:
                                        // HU-PROD-01 – Listar productos
                                        if (BaseService.tieneActivos(productos)) {
                                            BaseService.listar(productos);
                                        } else {
                                            System.out.println(" ***No hay productos activos***");
                                        }
                                        break;
                                    case 2:
                                        // HU-PROD-02 – Crear producto
                                        if (BaseService.tieneActivos(categorias)) {
                                            ProductoService.crear(productos, categorias);
                                        } else {
                                            System.out.println(
                                                    "*** Debe existir al menos una categoria antes de crear un producto***");
                                        }
                                        break;
                                    case 3:
                                        // HU-PROD-03 – Editar producto
                                        if (BaseService.tieneActivos(productos)) {
                                            ProductoService.actualizar(productos, categorias);
                                        } else {
                                            System.out.println(" ***No hay productos activos***");
                                        }
                                        break;
                                    case 4:
                                        // HU-PROD-04 – Eliminar producto (baja lógica)
                                        if (BaseService.tieneActivos(productos)) {
                                            ProductoService.eliminar(productos);
                                        } else {
                                            System.out.println(" ***No hay productos activos***");
                                        }
                                        break;
                                    default:
                                        seguirSubmenu = false;
                                }
                            } while (seguirSubmenu);
                            break;
                        case 3:
                            // Épica 3 – Gestión de Usuarios
                            do {
                                switch (BaseService.submenu("usuarios")) {
                                    case 1:
                                        // HU-USR-01 – Listar usuarios
                                        if (BaseService.tieneActivos(usuarios)) {
                                            switch (InputReader.leerIntEnRango("""
                                                1. Listar solo usuarios.
                                                2. Listar usarios y sus pedidos.
                                                Seleccione:
                                                """, "ERROR.. El dato debe ser numerico", 1, 2)) {
                                                case 1:
                                                    BaseService.listar(usuarios);
                                                    break;
                                                default:
                                                    UsuarioService.listarConListado(usuarios);
                                            }
                                        } else {
                                            System.out.println(" ***No hay usuarios activos***");
                                        }

                                        break;
                                    case 2:
                                        // HU-USR-02 – Crear usuario
                                        UsuarioService.crear(usuarios);
                                        break;
                                    case 3:
                                        // HU-USR-03 – Editar usuario
                                        if (BaseService.tieneActivos(usuarios)) {
                                            UsuarioService.actualizar(usuarios);
                                        } else {
                                            System.out.println(" ***No hay usuarios activos***");
                                        }
                                        break;
                                    case 4:
                                        // HU-USR-04 – Eliminar usuario (baja lógica)
                                        if (BaseService.tieneActivos(usuarios)) {
                                            UsuarioService.eliminar(usuarios);
                                        } else {
                                            System.out.println(" ***No hay usuarios activos***");
                                        }
                                        break;
                                    default:
                                        seguirSubmenu = false;
                                }
                            } while (seguirSubmenu);

                            break;
                        case 4:
                            // Épica 4 – Gestión de Pedidos y Detalles
                            do {
                                switch (BaseService.submenu("pedidos y detalles")) {
                                    case 1:
                                        // HU-PED-01 – Listar pedidos
                                        if (BaseService.tieneActivos(pedidos)) {
                                            switch (InputReader.leerIntEnRango("""
                                            1. Listar solo pedidos.
                                            2. Listar pedidos y sus detalles.
                                            3. Listar pedidos por usuario.
                                            Seleccione:
                                            """, "ERROR.. El dato debe ser numerico", 1, 3)) {
                                                case 1:
                                                    BaseService.listar(pedidos);
                                                    break;
                                                case 2:
                                                    PedidoService.listarConListado(pedidos);
                                                    break;
                                                default:
                                                    PedidoService.listarPorUsuario(usuarios);
                                            }
                                        } else {
                                            System.out.println(" ***No hay pedidos activos***");
                                        }
                                        break;
                                    case 2:
                                        // HU-PED-02 – Crear pedido con detalles
                                        if (BaseService.tieneActivos(usuarios)
                                                && ProductoService.existeDisponible(productos)) {//   
                                            PedidoService.crear(pedidos, usuarios, productos);//                                       
                                        } else {
                                            System.out.println(
                                                    " ***Debe existir al menos un usuario y un producto con stock mayor a 0 antes de crear un pedido***");
                                        }
                                        break;
                                    case 3:
                                        // HU-PED-03 – Actualizar estado/forma de pago del pedido
                                        if (BaseService.tieneActivos(pedidos)) {
                                            PedidoService.actualizar(pedidos);
                                        } else {
                                            System.out.println(" ***No hay pedidos activos***");
                                        }
                                        break;
                                    case 4:
                                        // HU-PED-04 – Eliminar pedido (baja lógica)
                                        if (BaseService.tieneActivos(pedidos)) {
                                            PedidoService.eliminar(pedidos);
                                        } else {
                                            System.out.println(" ***No hay pedidos activos***");
                                        }
                                        break;
                                    default:
                                        seguirSubmenu = false;
                                }
                            } while (seguirSubmenu);
                            break;
                        default:
                            System.out.println(">>> Fin del programa <<<");
                    }

                } while (opcion != 0);
            } else {
                System.out.println("Intentos agotados - ACCESO DENEGADO");
            }
        } catch (Exception e) {
            InputReader.mostrarError(e);
        }
    }
}
