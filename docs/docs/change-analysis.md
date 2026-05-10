
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

- Implementacion
  - 
Aprovechando que teníamos ya implementado un ArrayList de solicitudes se va a realizar el histórico sobre este atributo llamado solicitudes en la clase gesttionsolicitues.java

La implementación planteado ha necesitado modificar tanto solicitudes como gestionsolicitude sy los test planteados, apesar el planteamiento inicialr no ha sido necesario modificar ningún método existente de solicitudes.java debido a que en la primera implementación este caso ya estaba contemplado.

- Solicitudes.java
  -   
  - Historico:Es un nuevo atributo el cual empieza en 0 y cada vez que se hace un cambio de estado se incrementa en 1, siendo la solicitud con el mismo id que tenga el vlaor de historico más alto la más reciente y puediendo hacer una trazabilidad de los cambios.
  - Se ha creado un constructor de copia de solicitudes en el que se incrementa en 1 el valor de historico para poder añadir al Array list del gestion solicitudes.
- gestionsolicitudes.java
  - 
  - cerrarSolicitud():Ha sido modificada debido a que ahora creamos un una solicitud nueva apartir de la última actualizacióon ,para ello obtenemos primeor la solicitud más reciente y en caso de que se puedan hacer los cambios pertienentes se crea una solicitud nueva con el constructor de copia anteriormente mencionad y se añade al arraylist solicitudes.
  - reabrirSolicitudes(): este método se ha crado para cumplir con el cambio de requisitos de poder reabir una solicitud, sigue la misma estructuraa que el método cerrarSolicitud().
- gestionsolicitudesTest
  - 
  - Se han modificado los test:
    - testReabrirSolicitudCerradaDebeSerExitoso()
    - testGestorCerrarSolicitudSoloSiEstaEnProceso() 
  - Se han creado unos test nuevo para comprobar el buen funcionaiento de la nueva función.


Para poder actualizarlos a la nueva dinámica de crear una solicitud con un número mayor de historico.
  


