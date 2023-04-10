## Listar Empleado
Para listar los empleados registrados podemos usar de manera opcional los parametros:
- page : página que se mostrará.
- size : número de elementos por página.
- sort : propiedad que pertenece a la entidad que se tomará para ordenar la lista.

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

![][img_1]

[img_1]: ./screenshot/img01_BD.png

