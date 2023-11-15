

		Hola,

		Durante el desarrollo de la prueba se utilizaron los siguientes elementos:

		Sistema operativo: Windows 10, i7/8gb RAM
		Apache NetBeans 11.1
		Java 11
		MySQL Server 5.7 (Si se utiliza otra versión, puede que sea necesario 
		emplear un dialecto distinto en application.properties).
				
		Una vez todo esté instalado, se debe importar el proyecto al netbeans(o entorno elegido) y realizar una 
		limpieza y construcción (clean and build). En teoría, esto debería permitir que funcione.
		
		Tuve que aumentar el Heap de JVM a 6gb, añadiendo este argumento "-Xmx6g" (se añade sin comillas),
		si se arranca con netbeans una vez importado proyecto , con el boton derecho se accede a las propiedades
		del propyecto. pestaña RUN, a textArea correspondiente a JVM options se añade la variale, se puksa ok.

		En relación a la inserción y generación de los resúmenes, nunca me ha ocurrido, pero es 
		posible que las columnas se generen en un orden incorrecto. En ese caso, habría que corregir 
		CONSTANTE.CABECERA_CSV_INSERT y CONSTANTE.CABECERA_CSV.

		Por otra parte, se debe crear un directorio "LIBROSAPI" en C:/. Este directorio está 
		almacenado en CONSTANTE.ARCHIVO_URL y puede ser modificado.
		
		En la base de datos hay que crear un esquema "libros", antes de ejecutar el proyecto.

		Creo que con eso es todo. Espero recibir feedback cuando sea posible.

		Un saludo.
		