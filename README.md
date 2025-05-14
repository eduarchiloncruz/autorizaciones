# 📦 Autorizaciones - Controllers

Este módulo contiene los controladores REST del sistema de **Autorizaciones**, encargados de gestionar las entidades `Solicitud` y `Requerimiento`. Implementado en **Spring Boot**, con soporte para documentación **OpenAPI 3 (Swagger)**.

---

## ✨ Características

- API RESTful clara y documentada.
- Operaciones CRUD para solicitudes y requerimientos.
- Uso de DTOs para transferir datos entre capas.
- Paginación en consultas de solicitudes.
- Filtro por estado para requerimientos.
- Documentación automática con Swagger.

---

## 📂 Controladores Incluidos

### 📘 `SolicitudController`

Maneja la creación, consulta, actualización y eliminación de solicitudes de autorización.

#### Endpoints:

| Método | Endpoint                   | Descripción                                   |
|--------|----------------------------|-----------------------------------------------|
| POST   | `/solicitud/new`           | Crea una nueva solicitud con requerimientos.  |
| GET    | `/solicitud/{id}`          | Obtiene una solicitud por su ID.              |
| GET    | `/solicitud`               | Lista todas las solicitudes (con paginación). |
| PUT    | `/solicitud/{id}`          | Actualiza una solicitud existente.            |
| DELETE | `/solicitud/{id}`          | Elimina una solicitud por ID.                 |

#### DTOs usados:

- `AutorizacionGrupedDTO`
- `SolicitudDTO`

---

### 📗 `RequerimientoController`

Administra los requerimientos relacionados a las solicitudes.

#### Endpoints:

| Método | Endpoint                              | Descripción                                               |
|--------|----------------------------------------|-----------------------------------------------------------|
| PUT    | `/requerimiento/{idSolicitud}/{num}`  | Cambia el estado de un requerimiento.                    |
| GET    | `/requerimiento/all`                  | Lista todos los requerimientos.                          |
| GET    | `/requerimiento?estado=ESTADO_ENUM`   | Lista requerimientos filtrados por estado (`EstadoEnum`). |
| GET    | `/requerimiento/{idSolicitud}/{num}`  | Obtiene un requerimiento por ID de solicitud y número.    |

#### DTOs usados:

- `RequerimientoDTO`

---

## 🧾 Requisitos

- Java 17+
- Spring Boot
- Swagger/OpenAPI 3

---

## 🧪 Documentación de API

Puedes acceder a la documentación interactiva desde:  
