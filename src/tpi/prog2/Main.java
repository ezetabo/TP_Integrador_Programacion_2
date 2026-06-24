package tpi.prog2;

import java.util.ArrayList;
import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Producto;
import tpi.prog2.entities.Usuario;
import tpi.prog2.menu.InputReader;
import tpi.prog2.service.BaseService;
import tpi.prog2.service.CategoriaService;
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

        do {
            try {
                seguirSubmenu = true;
                opcion = BaseService.menu();
                switch (opcion) {
//                        Épica 1 – Gestión de Categorías
                    case 1:
                        do {
                            switch (BaseService.submenu("categorias")) {
                                case 1:
//                                HU-CAT-01 – Listar categorías
                                    BaseService.listar(categorias);
                                    break;
                                case 2:
//                                HU-CAT-02 – Crear categoría
                                    CategoriaService.crear(categorias);                                    
                                    break;
                                case 3:
//                                HU-CAT-03 – Editar categoría
                                    CategoriaService.actualizar(categorias);
                                    break;
                                case 4:
//                                HU-CAT-04 – Eliminar categoría (baja lógica)
                                    CategoriaService.eliminar(categorias);
                                    break;
                                default:
                                    seguirSubmenu = false;
                            }
                        } while (seguirSubmenu);
                        break;
                    case 2:
//                        Épica 2 – Gestión de Productos
                        do {
                            switch (BaseService.submenu("productos")) {
                                case 1:
//                                HU-PROD-01 – Listar productos
                                    BaseService.listar(productos);
                                    break;
                                case 2:
//                                HU-PROD-02 – Crear producto
                                    ProductoService.crear(productos, categorias);                                    
                                    break;
                                case 3:
//                                HU-PROD-03 – Editar producto
                                    ProductoService.actualizar(productos, categorias);
                                    break;
                                case 4:
//                                HU-PROD-04 – Eliminar producto (baja lógica)
                                    ProductoService.eliminar(productos);
                                    break;
                                default:
                                    seguirSubmenu = false;
                            }
                        } while (seguirSubmenu);
                        break;
                    case 3:
//                        Épica 3 – Gestión de Usuarios
                        do {
                            switch (BaseService.submenu("usuarios")) {
                                case 1:
//                                HU-USR-01 – Listar usuarios
                                    BaseService.listar(usuarios);
                                    break;
                                case 2:
//                                HU-USR-02 – Crear usuario
                                    UsuarioService.crear(usuarios);
                                    break;
                                case 3:
//                                HU-USR-03 – Editar usuario

                                    break;
                                case 4:
//                                HU-USR-04 – Eliminar usuario (baja lógica)

                                    break;
                                default:
                                    seguirSubmenu = false;
                            }
                        } while (seguirSubmenu);

                        break;
                    case 4:
//                        Épica 4 – Gestión de Pedidos y Detalles
                        do {
                            switch (BaseService.submenu("pedidos y detalles")) {
                                case 1:
//                                HU-PED-01 – Listar pedidos

                                    break;
                                case 2:
//                                HU-PED-02 – Crear pedido con detalles

                                    break;
                                case 3:
//                                HU-PED-03 – Actualizar estado/forma de pago del pedido

                                    break;
                                case 4:
//                                HU-PED-04 – Eliminar pedido (baja lógica)

                                    break;
                                default:
                                    seguirSubmenu = false;
                            }
                        } while (seguirSubmenu);
                        break;
                    default:
                        System.out.println(">>> Fin del programa <<<");
                }
            } catch (Exception e) {
                InputReader.mostrarError(e);
            }

        } while (opcion != 0);
    }
}
