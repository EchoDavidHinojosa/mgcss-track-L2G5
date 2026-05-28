# API Contract - Solicitudes

Documentación de los endpoints disponibles para la gestión de solicitudes de asistencia técnica.

---

## POST `/api/solicitudes`

Crea una nueva solicitud de asistencia en el sistema. Al crearse, por defecto se le asigna el estado inicial `ABIERTA`.

### Request Body

```json
{
  "descripcion": "Incidencia en impresora de la tercera planta"
}
```

### Response (201 Created)

```json
{
  "id": 1,
  "descripcion": "Incidencia en impresora de la tercera planta",
  "estado": "ABIERTA",
  "tecnicoId": null
}
```

---

## GET `/api/solicitudes/{id}`

Obtiene el detalle completo de una solicitud específica mediante su identificador único.

### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Long | Identificador único de la solicitud |

### Response (200 OK)

```json
{
  "id": 1,
  "descripcion": "Incidencia en impresora de la tercera planta",
  "estado": "ABIERTA",
  "tecnicoId": null
}
```

---

## GET `/api/solicitudes`

Lista todas las solicitudes almacenadas en el sistema.

### Response (200 OK)

```json
[
  {
    "id": 1,
    "descripcion": "Incidencia en impresora de la tercera planta",
    "estado": "EN_PROCESO",
    "tecnicoId": 3
  },
  {
    "id": 2,
    "descripcion": "Fallo de conexión a la VPN",
    "estado": "ABIERTA",
    "tecnicoId": null
  }
]
```

---

## PUT `/api/solicitudes/{id}/tecnico`

Asigna un técnico de soporte específico para que se encargue de resolver la solicitud.

### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Long | Identificador único de la solicitud |

### Request Body

```json
{
  "tecnicoId": 3
}
```

### Response (200 OK)

```json
{
  "id": 1,
  "descripcion": "Incidencia en impresora de la tercera planta",
  "estado": "EN_PROCESO",
  "tecnicoId": 3
}
```

---

## PUT `/api/solicitudes/{id}/estado`

Actualiza manualmente el estado del ciclo de vida de la solicitud (por ejemplo, pasarla a `EN_PROCESO`, `RESUELTA`, etc.).

### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Long | Identificador único de la solicitud |

### Request Body

```json
{
  "estado": "EN_PROCESO"
}
```

### Response (200 OK)

```json
{
  "id": 1,
  "descripcion": "Incidencia en impresora de la tercera planta",
  "estado": "EN_PROCESO",
  "tecnicoId": 3
}
```

---

## PATCH `/api/solicitudes/{id}/reabrir`

Reabre una solicitud que ya había sido marcada como cerrada o resuelta previamente si el problema persiste. Devuelve la solicitud al estado `ABIERTA`.

### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Long | Identificador único de la solicitud |

### Request Body

No requiere cuerpo de petición.

### Response (200 OK)

```json
{
  "id": 1,
  "descripcion": "Incidencia en impresora de la tercera planta",
  "estado": "ABIERTA",
  "tecnicoId": 3
}
```