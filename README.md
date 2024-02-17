# Teoria

1.	Explique en detalle cómo Kotlin Coroutine se integra con el ciclo de vida de las actividades y fragmentos en Android, y cómo esto mejora la gestión de tareas asíncronas.
Kotlin Coroutines se integran con el ciclo de vida de actividades y fragmentos en Android a través de CorutineScope que se puede vincular al ciclo de vida de un componente. Esto permite que las tareas asíncronas se suspendan y reanuden automáticamente en respuesta a los eventos del ciclo de vida, lo que mejora la gestión de tareas asíncronas al evitar fugas de memoria y errores relacionados con el ciclo de vida. En resumen, Kotlin Coroutines simplifica la concurrencia en Android al ofrecer un manejo más seguro y eficiente de las tareas asíncronas.
2.	Discuta las diferencias y ventajas de utilizar ViewModel en comparación con onSaveInstanceState para manejar cambios de configuración en una aplicación Android.
ViewModel: lmacena datos de manera persistente, vinculados al ciclo de vida de la actividad o fragmento, lo que facilita la gestión de datos y la separación de la lógica de la UI.
 onSaveInstanceState: Se utiliza para guardar datos temporales durante eventos específicos, como cambios de configuración. No es adecuado para almacenar datos complejos a largo plazo y su uso puede resultar en un código más complicado.
En general, ViewModel ofrece una solución más robusta y mantenible para manejar cambios de configuración en comparación con onSaveInstanceState.
3.	Explique el concepto y la importancia de los Data Binding y cómo se implementa en una aplicación Android para mejorar la eficiencia y la escalabilidad del código.
El Data Binding en Android permite vincular los elementos de la interfaz de usuario con los datos del modelo de manera declarativa, eliminando código boilerplate, mejorando la legibilidad y previniendo errores. Se implementa habilitando el Data Binding en el archivo de diseño XML y utilizando expresiones de Data Binding para establecer relaciones entre la UI y los datos del modelo. Esto mejora la eficiencia y escalabilidad del código al simplificar su mantenimiento y facilitar la depuración.
4.	Describa el proceso de implementación y los beneficios de usar WorkManager para tareas en segundo plano en aplicaciones Android.
WorkManager en Android simplifica la implementación de tareas en segundo plano. Para usarlo debemos agregar primero la dependencia a nuestro fichero build.gradle (en nuestro caso para Kotlin y no para Groovy) y extender la clase Worker sobreescribiendo el método doWork(). Después debemos programar la tarea con OneTimeWorkRequest y encolar con WorkManager.enqueue().
Los beneficios incluyen:
•	Compatibilidad: Se adapta a las capacidades del dispositivo y la versión de Android.
•	Permite tareas únicas o periódicas con restricciones.
•	Continúa ejecutando tareas incluso si la aplicación se cierra.
•	Maneja reinicios del sistema y pérdida de conexión, garantizando la fiabilidad de las tareas.

5.	Analice cómo se pueden utilizar los Flows y StateFlows en Kotlin para gestionar estados y eventos en aplicaciones Android, proporcionando ejemplos de su uso.
Los Flows son secuencias de valores que pueden emitirse de forma asíncrona y que pueden ser observados por diferentes partes de la aplicación. Son útiles para representar secuencias de eventos en tiempo real, como datos de sensores, actualizaciones de bases de datos o eventos de red. Los Flows son parte de la biblioteca Kotlin coroutines. Ejemplo: 
fun fetchData(): Flow<Result<Data>> {
    return flow {
        val data = getDataFromNetwork()
        emit(Result.Success(data))
    }.catch { e ->
        emit(Result.Error(e))
    }
}

lifecycleScope.launch {
    fetchData().collect { result ->
        when (result) {
            is Result.Success -> updateUIWithData(result.data)
            is Result.Error -> showError(result.exception)
        }
    }
}
6.	Explique el concepto de Inyección de Dependencias en Android y discuta cómo Dagger/Hilt facilita este proceso en aplicaciones de gran escala.

La Inyección de Dependencias (DI) en Android es un patrón de diseño que desacopla las dependencias entre clases, facilitando la modularidad y la prueba unitaria. Dagger y Hilt son frameworks populares que simplifican la implementación de DI en aplicaciones Android. Dagger, basado en anotaciones y generación de código en tiempo de compilación, ofrece alto rendimiento y control total sobre las dependencias. Hilt, construido sobre Dagger y diseñado específicamente para Android, simplifica la configuración y proporciona integración directa con componentes de Android, reduciendo la complejidad y facilitando el desarrollo de aplicaciones de gran escala.

7.	Describa las mejores prácticas para optimizar el rendimiento y la eficiencia de una aplicación Android, incluyendo el manejo de memoria y recursos.
Diseño de interfaz de usuario (UI): Prioriza diseños simples y eficientes, evita anidaciones profundas de vistas y utiliza recursos vectoriales en lugar de imágenes rasterizadas.
Gestión de recursos y memoria: Evita fugas de memoria liberando recursos correctamente, usa referencias débiles para objetos transitorios y gestiona cuidadosamente objetos grandes en memoria como bitmaps.
Optimización de red y almacenamiento: Implementa el almacenamiento en caché y la carga progresiva de contenido multimedia para minimizar las solicitudes de red y el consumo de datos.
Gestión de subprocesos y concurrencia: Utiliza coroutines o AsyncTask para operaciones en segundo plano, evita bloquear el hilo principal y gestiona la concurrencia de manera eficiente.
Optimización de la base de datos: Diseña consultas SQL eficientes, utiliza transacciones para reducir la sobrecarga y considera el uso de bibliotecas de ORM como Room.
Pruebas y perfilado: Realiza pruebas de rendimiento y perfilado regularmente para identificar cuellos de botella y áreas de mejora, utilizando herramientas como Android Profiler y Systrace.

8.	Analice el papel de los LiveData en la arquitectura MVVM de Android y cómo se comparan con otras soluciones para la observación de datos.

En la arquitectura MVVM de Android, LiveData juega un papel clave como medio de comunicación entre el ViewModel y la Vista. Actúa como un observable de datos que notifica automáticamente a los observadores cuando los datos cambian. LiveData simplifica la observación de datos al integrarse con el ciclo de vida de los componentes de Android, lo que previene fugas de memoria. Comparado con otras soluciones como observables regulares, RxJava y Kotlin Flow, LiveData destaca por su facilidad de uso y su integración directa con el desarrollo de aplicaciones Android.
9.	Explique cómo implementar pruebas unitarias y de integración en una aplicación Android, incluyendo el uso de frameworks como Mockito y Espresso.
Para las pruebas unitarias:
Primero debemos agregas las dependencias de JUnit y Mockito al archivo build.gradle. 
Después debemos crear una clase de prueba unitaria que extienda de TestCase o AndroidJUnit4 y definir los métodos de prueba utilizando el anotador @Test. 
Por último creamos objetos simulados de las dependencias utilizando Mockito. Llamando a los métodos que queremos probar y verificar los resultados utilizando assertiones.
Ejemplo con una calculadora:
class CalculatorTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @Test
    fun testAdd() {
        val calculator = Calculator()
        val mockDependency = mock(Dependency::class.java)

        `when`(mockDependency.getValue()).thenReturn(5)

        val result = calculator.add(2, mockDependency)

        assertEquals(7, result)
    }
}


Para las pruebas de integración: 
Agregamos la dependencia de Espresso al archivo build.gradle de la aplicación. Creamos una clase de prueba de integración que extienda de ActivityInstrumentationTestCase2 o AndroidJUnit4. Utilizamos los métodos de Espresso, como onView, perform, y check, para interactuar con la aplicación y verificarmos los resultados.
Ejemplo: 
class MainActivityTest : ActivityInstrumentationTestCase2<MainActivity>(MainActivity::class.java) {

    @Test
    fun testButtonClick() {
        onView(withId(R.id.button)).perform(click())

        onView(withId(R.id.text)).check(matches(withText("Hello, World!")))
    }
}
10.	Discuta las implicaciones de seguridad en el desarrollo de aplicaciones Android y cómo abordarlas eficazmente a través de buenas prácticas y herramientas.
Las aplicaciones Android pueden estar expuestas a varias amenazas de seguridad, incluyendo:
•	Malware
•	Inyección de código
•	Ataques de denegación de servicio (DoS)
•	Interceptación de comunicaciones
En cuanto al Malware mencionar que en mi opinión debemos prestar especial atención al Spyware y al AdWare como tipos de ataques ya que suelen ser los más comunes en aplicaciones Android o aplicaciones web. También debemos prestar especial atención a la privacidad de los datos y a las pasarelas de pago.
Por otro lado, las implicaciones de seguridad para Android deben pasar por una buena concienciación del usuario: no descargar aplicaciones que no proceden del PlayStore o de Microsoft Store, no dar permiso a ciertas aplicaciones a acceder a nuestro teléfono y tener desactivada la función de desarrolladores si no eres un profesional en el sector.


En base a lo que hemos estudiado en la asignatura podemos realizar estas buenas prácticas para protegernos de las ciberamenazas:
•	Gestión de datos sensibles: Utiliza las reglas de seguridad de Firebase para restringir el acceso a tus datos. Estas reglas permiten controlar quién puede leer y escribir en tu base de datos en tiempo real y en almacenamiento en la nube.
•	Autenticación segura: Utiliza la autenticación segura proporcionada por Firebase Authentication para validar la identidad de los usuarios antes de permitirles acceder a funciones sensibles de tu aplicación.
•	Almacenamiento seguro de credenciales: No almacenes credenciales de acceso u otros datos sensibles directamente en el código fuente de la aplicación. Utiliza servicios seguros de almacenamiento, como Firebase Remote Config o Firebase Remote Config, para almacenar datos sensibles de manera segura en la nube.
•	Utilizar la última versión de Kotlin para evitar vulnerabilidades ya publicadas en la red
•	Concienciación del usuario: como ya comentamos evitando tener ciertas funcionalidades operativas y siempre descargando contenido de plataformas verificadas.
•	Actualización del código: mantener actualizado nuestro código con las actualizaciones que vayan saliendo de FireBase.



# EXAMENEVENTOSE-COMMERCE. Voy a desarrolar la práctica utilizando FireBase como hemos visto en clase con la versión de Kotlin 1.9.0.

ERRORES: 

•	Autenticación de Usuarios: En esta parte no he podido implementar le inicio de sesión con Google porque cuando me descargo el fichero google-services.json de FireBase me aparece vacio el campo "oauth_client": [], por lo que no puedo conectarlo con mi web-client en Kotlin. He intentando de todo, rehacer el proyecto con FireBase, verificar que esta ene l directorio correcto a nivel App, crearlo desde la vista del proyecgto en Android Studio y no me funciona. El resto de la autenticación funciona bien con correo electrónico.


•	Detalles del Producto: Navigation Component no es compatible con FireBase (que es lo visto en el aula). He utilizado Navigation Controller en su lugar que es lo mismo de la clae Navigation.


• Error de compilación: No soy capaz de hacer que compile, me da un error de compilación que viene por utilizar la versión 1.9.0 de Kotlin con FIreBase pero es que si utilizo la 1.7 me da error el JetPackCompose. He intentando modificar los build.gradle que se sincronizan bien pero no soy capaz. Si lo pruebo con un compilador De Adnroid online funicona bien. No hay errores de Sintaxis.

CAPTURAS DE FIREBASE:

•	Autenticación de Usuarios: FireBase con autentificacion activada y método de incio de sesión:
<img width="1425" alt="image" src="https://github.com/juanraa8/EXAMENEVENTOSE-COMMERCE/assets/55549593/1c928b87-22c8-49c1-a800-98d1e9fc45ef">

•	Visualización de Productos: Base de datos creada con colección 1 con Documento: Productos:
<img width="1422" alt="image" src="https://github.com/juanraa8/EXAMENEVENTOSE-COMMERCE/assets/55549593/b75bc0c4-9eb9-47db-9c91-d948ec30717b">





