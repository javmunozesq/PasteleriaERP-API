package com.pasteleriaerp.config;

import com.pasteleriaerp.model.*;
import com.pasteleriaerp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component @RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UsuarioRepository  usuarioRepo;
    private final ClienteRepository  clienteRepo;
    private final ProveedorRepository provRepo;
    private final CategoriaRepository catRepo;
    private final ProductoRepository  prodRepo;
    private final RecetaRepository    recetaRepo;
    private final PasswordEncoder     encoder;

    @Override
    public void run(ApplicationArguments args) {
        if (usuarioRepo.count() > 0) return;

        // ── Usuarios ──────────────────────────────────────────────────────
        usuarioRepo.save(Usuario.builder().username("admin")
            .password(encoder.encode("admin123"))
            .nombreCompleto("Rafael Moreno García — Maestro Pastelero")
            .rol(Usuario.Rol.ADMIN).activo(true).build());
        usuarioRepo.save(Usuario.builder().username("carmen")
            .password(encoder.encode("obrador2024"))
            .nombreCompleto("Carmen López Ruiz — Repostera")
            .rol(Usuario.Rol.OPERARIO).activo(true).build());

        // ── Categorías ────────────────────────────────────────────────────
        Categoria basicos    = cat("Ingredientes Básicos",       "Harinas, azúcares, grasas y levaduras");
        Categoria rellenos   = cat("Rellenos y Coberturas",      "Rellenos artesanales, cremas y coberturas");
        Categoria especiales = cat("Ingredientes Especiales",    "Especias, frutos secos y aceites selectos");
        Categoria cordobesas = cat("Especialidades Cordobesas",  "Dulces y pasteles típicos de Córdoba");
        Categoria tartas     = cat("Tartas Artesanales",         "Tartas para celebraciones y encargos");
        Categoria bolleria   = cat("Bollería y Repostería",      "Bollería artesanal elaborada cada mañana");

        // ── Materias Primas ───────────────────────────────────────────────
        Producto harina    = p("Harina de Trigo Especial",       basicos,    "kg",    0.80, 80.0, 15.0);
        Producto azucar    = p("Azúcar Blanquilla",               basicos,    "kg",    0.75, 50.0, 10.0);
        Producto azucarG   = p("Azúcar Glass",                    basicos,    "kg",    1.20, 20.0,  5.0);
        Producto mant      = p("Mantequilla Premium 82%",         basicos,    "kg",    4.50, 25.0,  5.0);
        Producto huevos    = p("Huevos Frescos Clase A",          basicos,    "ud",    0.25,300.0, 50.0);
        Producto sal       = p("Sal Fina",                        basicos,    "kg",    0.50, 10.0,  2.0);
        Producto levadura  = p("Levadura Fresca",                 basicos,    "kg",    3.20,  4.0,  1.0);
        Producto leche     = p("Leche Entera Fresca",             basicos,    "L",     0.90, 25.0,  5.0);
        Producto aceite    = p("Aceite de Oliva Virgen Extra",    especiales, "L",     5.50, 20.0,  4.0);
        Producto almendra  = p("Almendra Molida",                 especiales, "kg",    6.00, 15.0,  4.0);
        Producto canela    = p("Canela Molida",                   especiales, "kg",   12.00,  3.0,  0.5);
        Producto anis      = p("Anís en Grano",                   especiales, "kg",    8.00,  3.0,  0.5);
        Producto miel      = p("Miel de Flores Cordobesa",        especiales, "kg",    8.00,  8.0,  2.0);
        Producto cabAngel  = p("Cabello de Ángel",                rellenos,   "kg",    3.50, 12.0,  3.0);
        Producto cidra     = p("Cidra Dulce",                     rellenos,   "kg",    2.80,  8.0,  2.0);
        Producto nata      = p("Nata para Montar",                rellenos,   "L",     2.50, 15.0,  4.0);
        Producto crema     = p("Crema Pastelera",                 rellenos,   "kg",    3.00,  8.0,  2.0);
        Producto hojaldre  = p("Masa de Hojaldre",                basicos,    "kg",    4.00, 10.0,  2.0);
        Producto atun      = p("Atún en Aceite de Oliva",         especiales, "kg",    8.00,  5.0,  1.0);

        // ── Productos Terminados ──────────────────────────────────────────
        // Especialidades Cordobesas
        Producto manolete    = pt("Manolete Cordobés",                  cordobesas, "ud",     0.65,  2.50);
        Producto pastelMed   = pt("Pastel Cordobés Mediano (6 pers.)",  cordobesas, "ud",     6.50, 18.00);
        Producto pastelGran  = pt("Pastel Cordobés Grande (12 pers.)", cordobesas, "ud",    12.00, 32.00);
        Producto alfajor     = pt("Alfajor Cordobés",                   cordobesas, "ud",     0.50,  1.80);
        Producto roscoVino   = pt("Rosco de Vino",                      cordobesas, "ud",     0.35,  1.20);
        Producto polvoron    = pt("Polvorón Andaluz",                   cordobesas, "ud",     0.30,  0.90);
        Producto tocinoCielo = pt("Tocino de Cielo (ración)",           cordobesas, "ud",     0.80,  2.80);

        // Tartas
        Producto tartaSant  = pt("Tarta de Santiago",             tartas, "ud",  7.50, 22.00);
        Producto tartaQueso = pt("Tarta de Queso Artesana",       tartas, "ud",  6.50, 20.00);
        Producto tartaChoc  = pt("Tarta de Chocolate Artesana",   tartas, "ud",  7.00, 24.00);
        Producto milhojas   = pt("Milhojas de Crema",             tartas, "ud",  1.20,  3.50);

        // Bollería y Repostería
        Producto croissant  = pt("Croissant de Mantequilla",         bolleria, "ud",      0.55,  1.80);
        Producto tortaAce   = pt("Torta de Aceite Cordobesa",        bolleria, "ud",      0.45,  1.50);
        Producto empanada   = pt("Empanada de Atún Hojaldrada",      bolleria, "ud",      0.90,  2.80);
        Producto magdalenas = pt("Magdalenas Tradicionales (12 ud)", bolleria, "bandeja", 1.80,  6.00);
        Producto berlina    = pt("Berlina de Crema",                 bolleria, "ud",      0.70,  2.20);
        Producto ensaimada  = pt("Ensaimada Artesana",               bolleria, "ud",      0.85,  2.50);

        // ── Recetas ───────────────────────────────────────────────────────
        receta("Manolete Cordobés",
            "El dulce más emblemático de Córdoba, con almendra molida y canela",
            manolete,
            new Object[][]{{harina,0.080},{azucar,0.050},{mant,0.040},{huevos,1.0},{almendra,0.030},{canela,0.002},{anis,0.003}});

        receta("Pastel Cordobés Mediano",
            "Pastel tradicional con cabello de ángel y cidra, hojaldre crujiente artesanal",
            pastelMed,
            new Object[][]{{harina,0.300},{mant,0.150},{azucarG,0.100},{cabAngel,0.350},{cidra,0.200},{huevos,2.0},{canela,0.003}});

        receta("Pastel Cordobés Grande",
            "Versión grande del pastel cordobés para 12 personas",
            pastelGran,
            new Object[][]{{harina,0.600},{mant,0.300},{azucarG,0.200},{cabAngel,0.700},{cidra,0.400},{huevos,4.0},{canela,0.006}});

        receta("Croissant de Mantequilla",
            "Croissant hojaldrado de mantequilla francesa 82%, fermentación lenta 18h",
            croissant,
            new Object[][]{{harina,0.120},{mant,0.080},{azucar,0.015},{levadura,0.006},{sal,0.002},{leche,0.050},{huevos,0.2}});

        receta("Magdalenas Tradicionales",
            "Magdalenas esponjosas con aceite de oliva virgen extra cordobés",
            magdalenas,
            new Object[][]{{harina,0.250},{azucar,0.200},{huevos,3.0},{aceite,0.150},{leche,0.100},{levadura,0.008}});

        receta("Torta de Aceite Cordobesa",
            "Torta crujiente con AOVE cordobés y anís, receta tradicional del Obrador",
            tortaAce,
            new Object[][]{{harina,0.150},{aceite,0.080},{azucar,0.045},{anis,0.008},{sal,0.002},{levadura,0.004}});

        receta("Empanada de Atún Hojaldrada",
            "Empanada hojaldrada con atún en aceite de oliva, receta de temporada",
            empanada,
            new Object[][]{{hojaldre,0.200},{atun,0.080},{huevos,0.5}});

        receta("Alfajor Cordobés",
            "Alfajor tradicional con almendra, miel y canela, rebozado en azúcar glass",
            alfajor,
            new Object[][]{{almendra,0.060},{azucarG,0.030},{miel,0.025},{canela,0.002},{anis,0.002}});

        receta("Milhojas de Crema",
            "Milhojas de hojaldre artesanal relleno de crema pastelera",
            milhojas,
            new Object[][]{{hojaldre,0.150},{crema,0.120},{azucarG,0.020}});

        // ── Clientes ──────────────────────────────────────────────────────
        clienteRepo.save(c("Hotel NH Córdoba",              "B14001122","comercial@nhcordoba.es",    "957334455","Av. de la Constitución 7, 14001 Córdoba"));
        clienteRepo.save(c("Cafetería Maimónides",           "B14333444","info@maimonides.es",         "957112233","Plaza Maimónides 3, 14004 Córdoba"));
        clienteRepo.save(c("Restaurante El Caballo Rojo",    "B14667788","pedidos@caballorojo.es",     "957474242","Cardenal Herrero 28, 14003 Córdoba"));
        clienteRepo.save(c("Colegio Loyola Córdoba",         "B14889900","secretaria@loyola.es",       "957223344","Calle Loyola 1, 14001 Córdoba"));
        clienteRepo.save(c("Ana Belén Jiménez García",       "12345678A","anabelen@gmail.com",         "665778899","Cruz Conde 15, 14001 Córdoba"));
        clienteRepo.save(c("Parador de Córdoba",             "A14123456","eventos@parador.es",         "957275900","Av. de la Arruzafa s/n, 14012 Córdoba"));

        // ── Proveedores ───────────────────────────────────────────────────
        provRepo.save(v("Harinas Guadalquivir S.L.",          "B14112233","pedidos@harinasgq.es",      "957445566","Pol. Industrial Torrecilla, Córdoba"));
        provRepo.save(v("Oleícola San Rafael Córdoba S.A.",   "A14445566","ventas@oleicola-sr.es",     "957667788","Ctra. Montilla km 12, Córdoba"));
        provRepo.save(v("Lácteos Campiña Sur S.L.",           "B14778899","comercial@lacteosSur.es",   "957889900","Pol. Los Pedroches, Pozoblanco"));
        provRepo.save(v("Confitería Tradicional Andaluza",    "A14223344","info@confiteria-and.es",    "957334411","Av. de Cádiz 45, Córdoba"));
        provRepo.save(v("Avícola Córdoba S.L.",               "B14556677","pedidos@avicola-co.es",     "957112200","Ctra. de Almadén km 5, Córdoba"));
    }

    // ── Helpers ───────────────────────────────────────────────────────────
    private Categoria cat(String nombre, String desc) {
        return catRepo.save(Categoria.builder().nombre(nombre).descripcion(desc).build());
    }
    private Producto p(String nombre, Categoria cat, String unidad, double precioC, double stock, double stockMin) {
        return prodRepo.save(Producto.builder().nombre(nombre).categoria(cat).unidadMedida(unidad)
            .precioCompra(precioC).precioVenta(0.0).stock(stock).stockMinimo(stockMin)
            .tipo(Producto.Tipo.MATERIA_PRIMA).activo(true).build());
    }
    private Producto pt(String nombre, Categoria cat, String unidad, double precioC, double precioV) {
        return prodRepo.save(Producto.builder().nombre(nombre).categoria(cat).unidadMedida(unidad)
            .precioCompra(precioC).precioVenta(precioV).stock(0.0).stockMinimo(0.0)
            .tipo(Producto.Tipo.PRODUCTO_TERMINADO).activo(true).build());
    }
    private RecetaIngrediente ri(Receta r, Producto prod, double cant) {
        RecetaIngrediente ri = new RecetaIngrediente();
        ri.setReceta(r); ri.setIngrediente(prod); ri.setCantidad(cant); return ri;
    }
    private void receta(String nombre, String desc, Producto pf, Object[][] ings) {
        Receta r = new Receta();
        r.setNombre(nombre); r.setDescripcion(desc); r.setProductoFinal(pf);
        java.util.List<RecetaIngrediente> lista = new java.util.ArrayList<>();
        for (Object[] ing : ings) lista.add(ri(r, (Producto)ing[0], ((Number)ing[1]).doubleValue()));
        r.setIngredientes(lista);
        recetaRepo.save(r);
    }
    private Cliente c(String n, String nif, String email, String tel, String dir) {
        return Cliente.builder().nombre(n).nif(nif).email(email).telefono(tel).direccion(dir).activo(true).build();
    }
    private Proveedor v(String n, String nif, String email, String tel, String dir) {
        return Proveedor.builder().nombre(n).nif(nif).email(email).telefono(tel).direccion(dir).activo(true).build();
    }
}
