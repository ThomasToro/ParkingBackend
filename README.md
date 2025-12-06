# ¿Cómo fue desplegado en railway? 

El primer paso que seguimos fue crear el despliege de la base de datos de PostgreSQL en la página de Railway, de este modo, se generó de forma completamente automática unas credenciales internas para la base de datos. 

De la base de datos, en la sección de Variables, agarramos piezas de los siguientes atributos: PGHOST, PGPORT, PGDATABASE, PGUSER y PGPASSWORD.

De forma paralela, subimos el repositorio de Github a Railway también, con lo que ya teníamos hecho, que eran las entidades de Spring Boot, podíamos probar en el momento de conectar la DB con el Backend porque estamos usando ORMs y ya sabemos que esta tecnología nos crea automáticamente las tablas dentro de la base de datos que vamos a usar.

Entonces, para poder conectar a Spring Boot con la base de datos, necesitamos una URL específica, la cual fue armada a mano porque en Railway no se nos proporcionó automáticamente. 
Para armarla, seguimos un tipo de fórmula: 

jdbc:postgresql:// + PGHOST + :+ PGPORT + / + /PGDATABASE

Y reemplazando esos valores que tenemos a nuestra disposición en Railway, obtuvimos esta URL: jdbc:postgresql://postgres.railway.internal:5432/railway

En el Backend desplegado en Railway, pusimos esa URL en una Variable llamada SPRING_DATASOURCE_URL, luego, en SPRING_DATASOUERCE_USERNAME pusimos el valor que estaba en el campo de PGUSER de PostgreSQL, posteriormente, pusimos la variable SPRING_DATASOURCE_PASSWORD en el Backend, poniéndoles como contenido, lo que tenía adentro la variable de Postgres PGPASSWORD, y finalmente, agregamos una cuarta variable al Backend llamada jwt.secret.key, que es la que se encarga de trabajar con el AccessToken que implementamos para proteger las rutas, esa es una forma como de inyectarlo porque no es recomendable subir el .env a Github. 

Finalmente, en el Backend desplegado en Railway, tuvimos que irnos a la sección de Settings y en Root Directory, colocar esta ruta: /parking-backend , debido a que allí adentro está el Dockerfile, entonces eso evita errores a futuro si es que Railway busca el Dokcerfile y no lo encuentra.
Después de eso, ambos contenedores quedaron desplegados en Railway y al revisar, las tablas se habían creado automáticamente dentro del despliegue de Postgres. 


# ¿Cómo compilarlo en local?

Para compilarlo en local, lo que se tiene que hacer es tener el .env en toda la raíz del proyecto, el cual se lo puedo compartir sin lío, luego de ello, lo que debe hacer es irse también a la raíz del proyecto desde el CMD y escribir el comando: 

docker compose up --build , 

allí tendrá que esperar a que se le descarguen todas las dependencias del pom.xml porque es lo más tardado, y cuando acabe ese paso, podrá ver que tanto el contenedor de la base de datos como el contenedor del Backend, han sido desplegados correctamente.

Si quiere ingresar al contenedor de la base de datos (en local), debe escribir este comando en otra instancia del CMD: 

docker exec -it parking_db_container psql -U postgres -d parking_db

Una vez dentro, podrá darse cuenta que las tablas están creadas automáticamente, lo que quiere decir que todo salió de maravilla. 

Es muy importante tener el .env porque como consecuencia de no tenerlo, en local, la app podría no funcionar. 





