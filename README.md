## Información
La API contiene:
- CRUD de la entidad Empleado con Reportes en PDF y EXCEL.
- Validaciones y Manejo de Errores, con respuesta en Json y código de estado personalizados para cada error.
- Seguridad basica con Spring Security (Autorización y Autenticación por ROLES).
- Conexion a dos Bases de Datos en simultáneo (PostgreSQL y MySQL).
- Paginación.

Credenciales iniciales:
- Username: kevinB, Password: admin -> roles: ADMIN, USER
- Username: pedroC, Password: 123 -> roles: USER

![][img_1]

[img_1]: ./screenshot/img01_BD.png

---

<br>

### Listar Empleado
Para listar los empleados registrados podemos usar de manera opcional los parametros:
- page : página que se mostrará.
- size : número de elementos por página.
- sort : nombre de la propiedad perteneciente a la entidad, que se tomará para ordenar la lista.

Si no se añaden dichos parametros estos obtienen valores por defecto (page = 0, size = 3, sort = codEmpleado).

Request: 
```
[ GET ]  http://localhost:8080/empleados
[ GET ]  http://localhost:8080/empleados?page=0&size=3&sort=nombre
```
Response: `Estatus code: 200 OK`
```
[
    {
        "codEmpleado": "EM10001",
        "distrito": {
            "codDistrito": "DI01",
            "nombreDistrito": "Comas"
        },
        "estado": {
            "codEstado": "ES2",
            "nombreEstado": "ACTIVO"
        },
        "nombre": "KEVIN",
        "apellidos": "B",
        "dni": "00000000",
        "direccion": "DIRECCION",
        "telefono": "9999999",
        "email": "paledot01@gmail.com",
        "usuario": "kevinB",
        "contrasena": "$2a$10$Jtfxa0EuEjZrfQ4OvR4WbuqD00OBIfIp.5Sv33A7G8ya3xTI542nq",
        "roles": [
            {
                "codRol": "RL01",
                "nombreRol": "ROLE_ADMIN"
            },
            {
                "codRol": "RL02",
                "nombreRol": "ROLE_USER"
            }
        ]
    },
    // ...
]
```

<br>

### Registrar Empleado
Para registrar un empleado, no se incluye el "codEmpleado" ni "roles" porque estos se obtienen de manera interna. Los campos "dni", "direccion", "telefono" y "email" son opcionales, pero si los incluimos en el Body deberan cumplir las resticciones respectivas.

Request: 
```
[ POST ]  http://localhost:8080/empleados
```
```
{
	"codDistrito":  "DI02",
	"codEstado":    "ES2",
	"nombre":       "mateo",
	"apellidos":    "Castillo",
	"dni":          "22224444",
	"direccion":    "Av. Carlos Izaguirre 233",
	"telefono":     "3333444",
	"email":        "mateoC@gmail.com",
	"usuario":      "mateoC",
	"contrasena":   "123"
}
```
Response: `Estatus code: 201 Created`
```
{
    // ... El Empleado con el mismo formato que se muestra al "Listar"
}
```

<br>


### Actualizar Empleado
Para actualizar un empleado debemos enviar solo los campos que queremos modificar. Estos campos enviados tambien deben de cumplir con las restricciones.

Request: 
```
[ PUT ]  http://localhost:8080/empleados/EM10003
```
```
{
    "codDistrito":  "DI05",
    "dni":          "11115555",
    "email":        "mateo@gmail.com"
}
```
Response: `Estatus code: 200 OK`
```
{
    // ... El Empleado actualizado con el mismo formato que se muestra al "Listar"
}
```

<br>

### Eliminar Empleado
Para eliminar un empleado solo enviamos en la URL el codigo de un Empleado que se encuentre registrado.

Request: 
```
[ DELETE ]  http://localhost:8080/empleados/EM10003
```
Response: `Estatus code: 200 OK`

<br>

### Generar Reporte Empleado en PDF

Request: 
```
[ GET ]  http://localhost:8080/empleados/rpt_PDF
```
Response: `Estatus code: 200 OK`

<br>

### Generar Reporte Empleado en EXCEL

Request: 
```
[ GET ]  http://localhost:8080/empleados/rpt_EXCEL
```
Response: `Estatus code: 200 OK`

<br>

---
### Herramientas utilizadas:
- **IntelliJ IDEA [  ]** como IDE principal para el desarrollo de la API.
- **PostgreSQL** como motor de base de datos principal.
- **MySQL** como motor de base de datos secundario.
- **Postman** para probar la API.
- **JasperSoft Studio [ 6.19.0 ]** para la creacion de plantillas para el reporte en PDF y EXCEL.
- **Visual Studio Code** para la edición de este README.md.
- **Git Bash** para subir y actualizar este repositorio.

### Tecnologias y versiones:
- OpenJDK 17 [ Amazon Corretto 17.0.5.8.1 LTS ]
- Apache Maven [ 3.0.1 ]
- Sprint Boot [ 3.0.5 ] 
- Spring Security
- Hibernate (ORM)
- JPQL
- Lombok
---

