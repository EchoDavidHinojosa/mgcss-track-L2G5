
Cambios esperados de la modificación de solicitudes para actuallizarse a los nuevos requisitos siendo estos:
Los usuarios podrán reabrir elementos cerrados.

El sistema conservará el historial de transiciones de estado.

Se mejorará la trazabilidad y la flexibilidad del flujo de trabajo.

Entendiendo el último punto como un resultado de la implementación de los dos primeros.

Las reglas modificadas serán:

- Se podrán reabrir las solicitudes.
- Se debe mantener un histórico de las solicitudes

Las funciones que podrán sufrir cambios son :

- Solicitudes.java
  - setEstado(estadoSolicitud estado)
- gestionsolicitudes.java
  - Hay que crear un método con sus test correspondiendtes de abrir solicitud
  - Hay que añadir la función de tener el histórico
- solicitudTest
  - Hay que añadir un test para estadoSolictud al reabrir las solicitudes
- gestionsolicitudesTes
  - Ya contamos con un test que contempla asignar las solicitudes a una arraylist pero debemos de confirmar su buen funcionamiento después de las modificaicones.

