# Obrador San Rafael — API REST (Spring Boot)

## Requisitos
- Java 17+ | MySQL 8.0 | IntelliJ IDEA con Lombok

## Arrancar
1. MySQL corriendo en localhost:3306
2. IntelliJ: File -> Open -> carpeta PasteleriaERP-API
3. Maven -> Reload Project
4. Settings -> Build -> Compiler -> Annotation Processors -> Enable
5. Ejecutar PasteleriaErpApplication

La BD 'obradorSanRafael' se crea automáticamente con todos los productos.

## Login
- admin / admin123  (Administrador)
- carmen / obrador2024  (Operario)

## Endpoints
- POST /api/auth/login
- GET|POST /api/clientes, /api/proveedores, /api/productos
- GET|POST /api/ventas, /api/compras, /api/recetas, /api/produccion
- GET /api/dashboard
- POST /api/ia/consulta
- GET /api/ia/analisis-completo
- GET /api/ia/compras-recomendadas
- GET /api/ia/prediccion-demanda
