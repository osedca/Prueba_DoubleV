### 
**Contenido para `pruebas.md`**
**Este archivo debe describir las pruebas realizadas en el proyecto.**  

```md
# Pruebas del Proyecto - Prueba DoubleV

## Pruebas Unitarias
**Se implementaron pruebas unitarias con `JUnit` para validar la lógica de negocio.**
Ejemplo de prueba en `UsuarioServiceTest.java`:
```java
@Test
public void cuandoSeCreaUsuario_debeRetornarUsuarioConId() {
    Usuario usuario = new Usuario("Oscar", "Camacho", "123456");
    Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);
    assertNotNull(usuarioGuardado.getId());
}
