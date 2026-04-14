Hola mi nombre es Cesar Ezequiel Reinoso. Este repositorio tiene el codigo del challenge de ingreso a android alkemy.
En "Presentación" hay un breve video donde me presento y muestro la aplicación desarrollada.  

Resumen:

Basicamente la aplicacion muestra una lista de peliculas desde una API donde esta toda la informacion de las peliculas. Al presionar una pelicula cambia la pantalla a una pantalla de detalles donde se puede ver informacion de la pelicula elegida.

En la ultima actualizacion se migro de Views a Compose.

Captura de pantalla del inicio de la app


![Sin título](https://github.com/user-attachments/assets/aefb81a1-e0b2-41c2-887f-4537c63ca05c)


Captura de pantalla de la pantalla de detalles


![Sin título2](https://github.com/user-attachments/assets/495b5eda-204d-4810-a5e8-1a69f3862734)

Update #1

En esta update se agregaron las listas de peliculas top rated, upcoming y now playing. Se hicieron cambios en la base de datos para que se guarden y actualicen las listas de peliculas.

Capturas de pantalla con las nuevas listas

<img width="544" height="944" alt="Captura de pantalla 2026-03-13 152854" src="https://github.com/user-attachments/assets/cc008f4b-e5c6-4dfc-95d9-f2ecb1747df0" />



<img width="537" height="937" alt="Captura de pantalla 2026-03-13 144211" src="https://github.com/user-attachments/assets/7ed53c8f-ed35-49cd-9f07-00859ba5d6bd" />



<img width="529" height="948" alt="Captura de pantalla 2026-03-13 144314" src="https://github.com/user-attachments/assets/fdc51105-b593-4c82-8912-23edad131f16" />






Update #2

Se agrego una nueva actualizacion.

En este update se agregaron dos pantallas, LoginScreen y RegisterScreen. La pantalla de LoginScreen es una pantalla de logueo a la aplicacion, donde se podra loguear con email y contraseña
o acceder con una cuenta de google previamente activada en el movil. La pantalla de RegisterScreen es para registrar el email y contraseña.
El registro por email y contraseña se realiza mediante la api de firebase que guarda la sesion del email iniciado. El registro por acceso con google tambien se guarda la sesion en firebase quedando registrada.
El registro por email y contraseña guarda los datos en firebase que administra las sesiones de los usuarios.
Se utilizo DataStore para guardar el estado de inicio de sesion del usuario, es decir si la app se cierra sin desloguearse y se vuelve a abrir, se salteara el inicio de sesion y se dirigira a la pantalla principal.

Como novedad se uso las tecnologias de Google, Firebase, Credencial Manager para administrar credenciales y DataStore para guardar el estado de la sesion.

Capturas de pantalla de las nuevas pantallas de logueo y registro



<img width="418" height="846" alt="Captura de pantalla 2026-04-14 182758" src="https://github.com/user-attachments/assets/fd8c55fd-19a4-4a59-8ac9-5fcca61f1574" />

<img width="409" height="836" alt="Captura de pantalla 2026-04-14 183040" src="https://github.com/user-attachments/assets/9fd9bb86-0a39-47a8-8889-581f4757dba5" />

<img width="419" height="841" alt="Captura de pantalla 2026-04-14 183132" src="https://github.com/user-attachments/assets/ebb6f0f2-07c1-438a-bcb5-2d7f0a870df1" />

<img width="413" height="848" alt="Captura de pantalla 2026-04-14 183242" src="https://github.com/user-attachments/assets/c70f27e9-cff9-476d-9358-299f28e56353" />

<img width="416" height="852" alt="Captura de pantalla 2026-04-14 185311" src="https://github.com/user-attachments/assets/3bb70c64-3048-4fc3-a291-620490a2638c" />

<img width="411" height="856" alt="Captura de pantalla 2026-04-14 190240" src="https://github.com/user-attachments/assets/527cc3ce-2157-40fe-9f3b-1542f7b9e31f" />

<img width="1490" height="344" alt="Captura de pantalla 2026-04-14 190417" src="https://github.com/user-attachments/assets/3ab6d3a1-8d28-4a0d-91e8-7d45a4f83b87" />

<img width="875" height="455" alt="Captura de pantalla 2026-04-14 190457" src="https://github.com/user-attachments/assets/9a273dd6-f450-4430-8f53-24252ca5c74f" />












