package tpi.prog2;

import java.util.ArrayList;
import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Producto;
import tpi.prog2.entities.Usuario;
import tpi.prog2.service.BaseService;

public class Main {

    public static void main(String[] args) {
        int opcion = 0;
        List<Categoria> categorias = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();
        List<Pedido> pedidos = new ArrayList<>();
        do {
            try {
                opcion = BaseService.menu();
                switch (opcion) {
                    case 1:
                        switch (opcion) {
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                            case 4:

                                break;                          
                        }
                        break;
                    case 2:
                        switch (opcion) {
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                            case 4:

                                break;                           
                        }
                        break;
                    case 3:
                        switch (opcion) {
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                            case 4:

                                break;                           
                        }
                        break;
                    case 4:
                        switch (opcion) {
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                            case 4:

                                break;                            
                        }
                        break;
                    default:
                        System.out.println(">>> Fin del programa <<<");
                        ;
                }
            } catch (Exception e) {
            }

        } while (opcion != 0);
    }
}
