

		Hola,

		Durante el desarrollo de la prueba se utilizaron los siguientes elementos:

		Sistema operativo: Windows 10
		Apache NetBeans 11.1
		Java 11
		MySQL Server 5.7 (Si se utiliza otra versión, puede que sea necesario 
		emplear un dialecto distinto en application.properties).
		
		El esquema que hay que crear "libros" 
		
		Una vez todo esté instalado, se debe importar el proyecto y realizar una 
		limpieza y construcción (clean and build). En teoría, esto debería permitir que funcione.

		En relación a la inserción y generación de los resúmenes, nunca me ha ocurrido, pero es 
		posible que las columnas se generen en un orden incorrecto. En ese caso, habría que corregir 
		CONSTANTE.CABECERA_CSV_INSERT y CONSTANTE.CABECERA_CSV.

		Por otra parte, se debe crear un directorio "LIBROSAPI" en C:/. Este directorio está 
		almacenado en CONSTANTE.ARCHIVO_URL y puede ser modificado.

		Creo que con eso es todo. Espero recibir feedback cuando sea posible.

		Un saludo.
		