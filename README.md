# TP Integrador - Programación II

# Food Store – Sistema de Gestión de Pedidos

## 1. Descripción del proyecto

Food Store es una aplicación de consola desarrollada en Java para gestionar categorías, productos, usuarios, pedidos y detalles de pedido.

El proyecto está implementado con Programación Orientada a Objetos, relaciones UML, herencia, interfaces, enums, validaciones, baja lógica y manejo de colecciones en memoria.

La persistencia utilizada en esta versión es en memoria mediante `ArrayList`, por lo que los datos cargados durante la ejecución se mantienen mientras el programa está abierto.

## 2. Objetivo

El objetivo del proyecto es implementar el modelo UML de Food Store y transformarlo en una aplicación de consola funcional que permita realizar operaciones CRUD sobre las entidades principales del sistema.

El sistema permite:

- administrar categorías;
- administrar productos;
- administrar usuarios;
- crear y consultar pedidos;
- cargar detalles de pedido;
- calcular subtotales y totales;
- aplicar baja lógica;
- validar entradas desde consola;
- simular un login de administrador.

## 3. Tecnologías utilizadas

- Java
- Programación Orientada a Objetos
- UML
- Colecciones (`ArrayList`)
- Aplicación de consola
- NetBeans
- Git / GitHub

## 4. Estructura del proyecto

La estructura del código fuente es:

```text
src/
└── tpi/
    └── prog2/
        ├── Main.java
        ├── entities/
        │   ├── Base.java
        │   ├── Categoria.java
        │   ├── Producto.java
        │   ├── Usuario.java
        │   ├── Pedido.java
        │   └── DetallePedido.java
        ├── enums/
        │   ├── Estado.java
        │   ├── FormaPago.java
        │   └── Rol.java
        ├── exception/
        │   ├── ServiceException.java
        │   └── EntityNotFoundException.java
        ├── interfaces/
        │   └── Calculable.java
        ├── service/
        │   ├── BaseService.java
        │   ├── CategoriaService.java
        │   ├── ProductoService.java
        │   ├── UsuarioService.java
        │   └── PedidoService.java
        └── utils/
            └── InputReader.java
```

## 5. Arquitectura implementada

El proyecto está organizado por responsabilidades.

### `entities`

Contiene las clases del modelo de dominio. Estas clases representan los objetos principales del sistema y respetan las relaciones UML.

### `enums`

Contiene las enumeraciones utilizadas por el dominio:

- `Rol`
- `Estado`
- `FormaPago`

Cada enum posee una descripción para mostrar textos más claros en consola.

### `interfaces`

Contiene la interfaz `Calculable`, implementada por `Pedido`.

### `service`

Contiene la lógica de operación de cada entidad. Los servicios son clases con métodos estáticos que trabajan sobre las listas recibidas desde `Main`.

### `utils`

Contiene `InputReader`, clase encargada de centralizar la lectura y validación de datos ingresados por consola.

### `exception`

Contiene excepciones propias del proyecto para manejar errores de servicio y entidades no encontradas.

## 6. Modelo de entidades

## 6.1 Base

`Base` es una clase abstracta de la cual heredan todas las entidades principales.

Atributos:

- `id`
- `eliminado`
- `createdAt`

El identificador se genera automáticamente con un contador estático. La baja lógica se implementa con el atributo `eliminado`.

## 6.2 Categoria

Representa una categoría de productos.

Atributos principales:

- `nombre`
- `descripcion`
- `productos`

Relación:

- una categoría puede contener muchos productos.

Métodos importantes:

- `agregarProducto(Producto producto)`
- `quitarProducto(Producto producto)`
- `tieneProductosActivos()`
- `info()`
- `infoConListado()`

## 6.3 Producto

Representa un producto del catálogo.

Atributos principales:

- `nombre`
- `precio`
- `descripcion`
- `stock`
- `imagen`
- `disponible`
- `categoria`

Relación:

- un producto pertenece a una categoría.

El atributo `disponible` se calcula automáticamente según el stock.

## 6.4 Usuario

Representa un usuario del sistema.

Atributos principales:

- `nombre`
- `apellido`
- `mail`
- `celular`
- `contrasenia`
- `rol`
- `pedidos`

Relación:

- un usuario puede tener muchos pedidos.

Métodos importantes:

- `agregarPedido(Pedido pedido)`
- `tienePedidosActivos()`
- `info()`
- `infoConListado()`

## 6.5 Pedido

Representa una compra realizada por un usuario.

Atributos principales:

- `fecha`
- `estado`
- `total`
- `formaPago`
- `detalles`
- `usuario`

Relaciones:

- un pedido pertenece a un usuario;
- un pedido contiene muchos detalles.

Implementa la interfaz `Calculable`.

Métodos importantes:

- `calcularTotal()`
- `addDetallePedido(int cantidad, Double precioUnitario, Producto producto)`
- `findDetallePedidoByProducto(Producto producto)`
- `deleteDetallePedidoByProducto(Producto producto)`

## 6.6 DetallePedido

Representa una línea dentro de un pedido.

Atributos principales:

- `cantidad`
- `subtotal`
- `producto`

Relación:

- cada detalle está asociado a un producto.

El subtotal se calcula en base al precio del producto y la cantidad.

## 7. Relaciones UML implementadas

Las relaciones implementadas en código son:

```text
Categoria 1 ---- N Producto
Usuario   1 ---- N Pedido
Pedido    1 ---- N DetallePedido
Producto  1 ---- N DetallePedido
```

Aclaración sobre `Producto` y `DetallePedido`:

Aunque un producto puede aparecer en muchos detalles de pedido, la navegación implementada es desde `DetallePedido` hacia `Producto`. Por eso `Producto` no necesita tener una lista de detalles. El detalle guarda el producto que fue comprado.

## 8. Login

El sistema incluye una simulación de login por consola.

Características:

- solicita mail;
- solicita contraseña;
- permite hasta 3 intentos;
- solo permite ingresar a usuarios con rol `ADMIN`;
- no permite ingresar usuarios eliminados;
- informa intentos restantes cuando falla.

Credenciales cargadas por defecto:

```text
Mail: admin@mail.com
Contraseña: admin123
```

## 9. Menú principal

El flujo principal se encuentra en `Main`.

El menú principal permite acceder a:

```text
1. Categorías
2. Productos
3. Usuarios
4. Pedidos
0. Salir
```

Cada opción abre un submenú CRUD con las operaciones:

```text
1. Listar
2. Crear
3. Editar
4. Eliminar
0. Volver al menú principal
```

## 10. Casos de uso implementados

## 10.1 Categorías

### HU-CAT-01 – Listar categorías

El sistema permite listar categorías activas.

También permite listar categorías junto con sus productos.

### HU-CAT-02 – Crear categoría

El sistema solicita:

- nombre;
- descripción.

Validaciones:

- nombre obligatorio;
- descripción obligatoria;
- nombre único.

### HU-CAT-03 – Editar categoría

El sistema permite actualizar:

- nombre;
- descripción.

### HU-CAT-04 – Eliminar categoría

La eliminación es lógica. Antes de eliminar, el sistema valida si la categoría tiene productos activos asociados.

## 10.2 Productos

### HU-PROD-01 – Listar productos

El sistema permite listar productos activos.

### HU-PROD-02 – Crear producto

El sistema solicita:

- nombre;
- precio;
- descripción;
- stock;
- imagen;
- categoría.

Validaciones:

- debe existir al menos una categoría activa;
- nombre obligatorio;
- nombre único;
- precio mayor o igual a 0;
- stock mayor o igual a 0;
- imagen obligatoria;
- categoría activa.

### HU-PROD-03 – Editar producto

El sistema permite actualizar:

- precio;
- stock;
- categoría.

### HU-PROD-04 – Eliminar producto

La eliminación es lógica.

## 10.3 Usuarios

### HU-USR-01 – Listar usuarios

El sistema permite listar usuarios activos.

También permite listar usuarios junto con sus pedidos.

### HU-USR-02 – Crear usuario

El sistema solicita:

- nombre;
- apellido;
- mail;
- celular;
- contraseña;
- rol.

Validaciones:

- nombre obligatorio;
- apellido obligatorio;
- mail obligatorio;
- mail con formato básico;
- mail único;
- celular obligatorio;
- contraseña obligatoria;
- rol obligatorio.

### HU-USR-03 – Editar usuario

El sistema permite actualizar:

- nombre;
- apellido;
- mail;
- celular;
- contraseña;
- rol.

### HU-USR-04 – Eliminar usuario

La eliminación es lógica.

## 10.4 Pedidos y detalles

### HU-PED-01 – Listar pedidos

El sistema permite:

- listar pedidos;
- listar pedidos con detalles;
- listar pedidos por usuario.

### HU-PED-02 – Crear pedido con detalles

El sistema permite seleccionar un usuario activo y agregar productos al pedido.

Validaciones:

- debe existir al menos un usuario activo;
- debe existir al menos un producto disponible;
- la cantidad debe ser mayor a 0;
- la cantidad no puede superar el stock disponible;
- el producto no debe estar eliminado.

Al agregar un detalle:

- se usa `addDetallePedido(...)`;
- se calcula el subtotal;
- se actualiza el total del pedido;
- se descuenta stock del producto.

### HU-PED-03 – Actualizar pedido

El sistema permite actualizar:

- estado;
- forma de pago.

### HU-PED-04 – Eliminar pedido

La eliminación es lógica.

## 11. Validaciones implementadas

Las validaciones están distribuidas entre entidades, servicios e `InputReader`.

### En entidades

- campos obligatorios;
- precio mayor o igual a 0;
- stock mayor o igual a 0;
- cantidad mayor a 0;
- subtotal mayor o igual a 0;
- fecha no nula;
- estado no nulo;
- forma de pago no nula;
- producto no nulo;
- usuario no nulo en relaciones;
- listas no nulas.

### En servicios

- nombre único de categoría;
- nombre único de producto;
- mail único de usuario;
- existencia de elementos activos antes de operar;
- confirmación antes de eliminar;
- validación de stock antes de comprar;
- validación de rol ADMIN en login.

### En InputReader

- cadenas no vacías;
- enteros válidos;
- enteros dentro de rango;
- doubles válidos;
- doubles dentro de rango;
- fechas con formato `dd/MM/yyyy`;
- confirmación `S/N`;
- selección de enums por menú;
- teléfono con formato argentino `+54 9 XXXXXXXXXX`;
- email básico con `@` y sin espacios.

## 12. Baja lógica

La baja lógica se realiza modificando el atributo `eliminado`.

No se elimina físicamente el objeto de la lista.

Esto permite conservar relaciones históricas y evitar inconsistencias entre pedidos, usuarios, productos y detalles.

## 13. Datos iniciales

`BaseService` incluye el método `cargarDatosIniciales(...)`, preparado para cargar categorías, productos, usuarios y pedidos de prueba.

En el `Main` actual, la llamada a este método se encuentra comentada. Por defecto se crea el usuario administrador mediante:

```java
usuarios.add(UsuarioService.crearAdmin());
```

Para cargar datos de prueba completos, puede descomentarse la línea correspondiente en `Main`.

## 14. Cómo ejecutar

1. Abrir el proyecto en NetBeans.
2. Ejecutar `Main.java`.
3. Ingresar con el usuario administrador:

```text
Mail: admin@mail.com
Contraseña: admin123
```

4. Utilizar el menú principal.

## 15. Decisiones de diseño

- Se usa una clase abstracta `Base` para centralizar atributos comunes.
- Se usa generación automática de IDs desde `Base`.
- Se implementa baja lógica con `eliminado`.
- Las listas se crean en `Main`.
- Los servicios reciben listas y aplican operaciones sobre ellas.
- `InputReader` centraliza lectura y validación de consola.
- Los métodos `info()`, `infoConListado()` y `toString()` generan salidas ordenadas para consola.
- Los enums tienen descripción para mejorar la presentación.
- `Pedido` implementa `Calculable`.
- `DetallePedido` calcula su subtotal.
- `Pedido` calcula su total recorriendo sus detalles.

## 16. Observaciones finales

El proyecto cumple con el objetivo de implementar una aplicación de consola basada en POO, respetando el modelo UML y las historias de usuario principales.

La solución utiliza memoria como mecanismo de almacenamiento durante la ejecución y mantiene una estructura simple, legible y alineada con el nivel de Programación II.

## 17. Autor

`Ezequiel Taboada`

[REPOSITORIO](https://github.com/ezetabo/TP_Integrador_Programacion_2.git)

[VIDEO](https://youtu.be/VYb3u9eoN1w)

[COMMITS](https://github.com/ezetabo/TP_Integrador_Programacion_2/commits/main/)
