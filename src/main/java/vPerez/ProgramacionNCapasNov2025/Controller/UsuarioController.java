package vPerez.ProgramacionNCapasNov2025.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vPerez.ProgramacionNCapasNov2025.DAO.ColoniaDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.ColoniaJpaDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.DireccionDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.DireccionJpaDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.EstadoDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.EstadoJpaDAOIMplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.MunicipioDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.MunicipioJpaDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.PaisDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.PaisJpaDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.DAO.UsuarioDAOImplementation;
import vPerez.ProgramacionNCapasNov2025.ML.Colonia;
import vPerez.ProgramacionNCapasNov2025.ML.Direccion;
import vPerez.ProgramacionNCapasNov2025.ML.ErrorCarga;
import vPerez.ProgramacionNCapasNov2025.ML.Pais;
import vPerez.ProgramacionNCapasNov2025.ML.Result;
import vPerez.ProgramacionNCapasNov2025.ML.Rol;
import vPerez.ProgramacionNCapasNov2025.ML.Usuario;
import vPerez.ProgramacionNCapasNov2025.service.ColoniaService;
import vPerez.ProgramacionNCapasNov2025.service.DireccionService;
import vPerez.ProgramacionNCapasNov2025.service.EstadoService;
import vPerez.ProgramacionNCapasNov2025.service.MunicipioService;
import vPerez.ProgramacionNCapasNov2025.service.PaisService;
import vPerez.ProgramacionNCapasNov2025.service.RolService;
import vPerez.ProgramacionNCapasNov2025.service.UsuarioService;
import vPerez.ProgramacionNCapasNov2025.service.ValidationService;

@Controller // Sirve para mapear interacciones
@RequestMapping("Usuario")
public class UsuarioController {

    @Autowired //Inyeccion automatica de dependencias
    private UsuarioDAOImplementation usuarioDaoImplementation;

//    @Autowired
//    private DireccionDAOImplementation direccionDaoImplementation;
    @Autowired
    private PaisDAOImplementation paisDaoImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDaoImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDaoImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDaoImplementation;

    @Autowired
    private ValidationService ValidationService;

    @Autowired
    private DireccionJpaDAOImplementation direccionJpaDAOImplementation;

    @Autowired
    private PaisJpaDAOImplementation paisJpaDAOImplementation;

    @Autowired
    private EstadoJpaDAOIMplementation estadoJpaDAOImplementation;

    @Autowired
    private MunicipioJpaDAOImplementation municipioJpaDAOImplementation;

    @Autowired
    private ColoniaJpaDAOImplementation coloniaJpaDAOImplementation;
//    @Autowired
//private ModelMapper modelMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private PaisService paisService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private ColoniaService coloniaService;

    @GetMapping()
    public String getAll(Model model, RedirectAttributes redirectAtriAttributes) {

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Object principal = aut.getPrincipal();
        String nombreRol = aut.getAuthorities().iterator().next().getAuthority();
        int idUsuario = (Integer) usuarioService.getIdByEmail(aut.getName()).Object;
//        if (nombreRol.equals("ROLE_Usuario")) {
//            return "redirect:/Usuario/detail";
//        } else {

            vPerez.ProgramacionNCapasNov2025.JPA.Result result = usuarioService.getAll(0);
            Page<Usuario> paginacion = (Page<Usuario>) result.Object;
            model.addAttribute("idUsuario", idUsuario);
            model.addAttribute("UsuarioAutenticado", principal);
            model.addAttribute("Usuario", usuarioService.getById(idUsuario).Object);
            model.addAttribute("Usuarios", paginacion.getContent());
            model.addAttribute("paginacion", paginacion);
            model.addAttribute("UsuarioBusqueda", new Usuario());//creando usuario(vacio) para que pueda mandarse la busqueda
            vPerez.ProgramacionNCapasNov2025.JPA.Result resultRoles = rolService.getAll();
//        Result resultRoles = rol
            model.addAttribute("Roles", resultRoles.Objects);
            model.addAttribute("usuariosEstatus", paginacion.getContent());
            return "Index";
//    }
        
    }

    @GetMapping("/navegar")
    public String getList(@RequestParam("pagina") int pagina, Model model, RedirectAttributes redirectAtriAttributes) {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Object principal = aut.getPrincipal();
        String nombreRol = aut.getAuthorities().iterator().next().getAuthority();
//        model permite cargar informacion desde el backend en la vistas(frontend)
        int idUsuario = (Integer) usuarioService.getIdByEmail(aut.getName()).Object;
        if (nombreRol.equals("ROLE_Usuario")) {
            return "redirect:/Usuario/detail/" + idUsuario;
        } else {
            vPerez.ProgramacionNCapasNov2025.JPA.Result result = usuarioService.getAll(pagina);
            Page<Usuario> paginacion = (Page<Usuario>) result.Object;

            model.addAttribute("idUsuario", idUsuario);
            model.addAttribute("UsuarioAutenticado", principal);
            model.addAttribute("Usuario", usuarioService.getById(idUsuario).Object);
            model.addAttribute("paginacion", paginacion);
            model.addAttribute("Usuarios", paginacion.getContent());
            model.addAttribute("UsuarioBusqueda", new Usuario());//creando usuario(vacio) para que pueda mandarse la busqueda
            vPerez.ProgramacionNCapasNov2025.JPA.Result resultRoles = rolService.getAll();
//        Result resultRoles = rol
            model.addAttribute("Roles", resultRoles.Objects);
            model.addAttribute("usuariosEstatus", paginacion.getContent());
            return "Index";
        }

    }

    @GetMapping("UsuarioDireccionForm")
    public String showAlumnoDireccion(Model model, RedirectAttributes redirectAttributes) {
//        Result resultPais = paisDaoImplementation.getAll();
        vPerez.ProgramacionNCapasNov2025.JPA.Result result = rolService.getAll();
        vPerez.ProgramacionNCapasNov2025.JPA.Result resultPais = paisService.getAll();
        model.addAttribute("Roles", result.Objects);
        model.addAttribute("Paises", resultPais.Objects);
        model.addAttribute("Usuario", new Usuario());
        return "UsuarioDireccionForm";
    }

    @PostMapping("add")
    public String addAlumnoDireccion(@Valid @ModelAttribute("Usuario") Usuario usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
            @ModelAttribute("imagen") MultipartFile imagen, @ModelAttribute("imagen") String imagenPerfil) {
//        imagen.getBytes();
        try {
            if (imagen != null && imagen.getSize() > 0) {
                String extension = imagen.getOriginalFilename().split("\\.")[1];
                if (extension.equals("jpg") || extension.equals("jpg") || extension.equals("jpeg")) {
                    usuario.setImagen(Base64.getEncoder().encodeToString(imagen.getBytes()));
                }
            } else {
                usuario.setImagen(imagenPerfil);
            }
        } catch (Exception ex) {
            ex.getCause();
        }
        if (usuario.getIdUsuario() == 0 && usuario.direcciones.get(0).getIdDireccion() == 0) { // agregar usuario direccion

            if (bindingResult.hasErrors()) {
//                Result result = rolDaoImplementation.getAll();
                vPerez.ProgramacionNCapasNov2025.JPA.Result result = rolService.getAll();
                model.addAttribute("Roles", result.Objects);
                model.addAttribute("Usuario", usuario);

                return "UsuarioDireccionForm";
            } else {
                //AGREGADO RECIENTEMENTE SOLO EL IF
                ModelMapper modelMapper = new ModelMapper();

                vPerez.ProgramacionNCapasNov2025.JPA.Usuario usuarioJpa = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.JPA.Usuario.class);

                vPerez.ProgramacionNCapasNov2025.JPA.Result result = usuarioService.add(usuarioJpa);

                if (!result.Correct) {
                    model.addAttribute("ErroresC", "Sucedio un error.");
                    return "UsuarioDireccionForm";
                }

                redirectAttributes.addFlashAttribute("ResultAgregar", "El usuario se agregó con exito"); // Agregado

            }
        } else if (usuario.getIdUsuario() > 0 && usuario.direcciones == null) { // editar usuario
            try {
                usuario.setPassword(usuario.getPassword());
                usuario.direcciones = new ArrayList<>();
                usuario.direcciones.add(new Direccion());
                ModelMapper modelMapper = new ModelMapper();

                vPerez.ProgramacionNCapasNov2025.JPA.Usuario usuarioEntidad = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.JPA.Usuario.class);

                vPerez.ProgramacionNCapasNov2025.JPA.Result resultUpdateUsuario = usuarioService.update(usuarioEntidad);

                if (resultUpdateUsuario.Correct) {
                    resultUpdateUsuario.Object = "Exito al actualizar";
                } else {
                    resultUpdateUsuario.Object = "Error al actualizar";
                }
                redirectAttributes.addFlashAttribute("resultadoUpdate", resultUpdateUsuario);
            } catch (Exception ex) {
                return "redirect:/Usuario/detail/" + usuario.getIdUsuario();
            }
//            return "detalleUsuario";

        } else if ((usuario.getIdUsuario() > 0 && usuario.direcciones.get(0).getIdDireccion() > 0)) { // editar direccion
            try {
                ModelMapper modelMapper = new ModelMapper();
                vPerez.ProgramacionNCapasNov2025.JPA.Usuario usuarioJPA = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.JPA.Usuario.class);
                usuarioJPA.direcciones.get(0).Usuario = new vPerez.ProgramacionNCapasNov2025.JPA.Usuario();
                usuarioJPA.direcciones.get(0).Usuario.setIdUsuario(usuario.getIdUsuario());
//            Result resultUpdateDireccion = direccionJpaDAOImplementation.update(usuarioJPA.direcciones.get(0));
                vPerez.ProgramacionNCapasNov2025.JPA.Result resultUpdateDireccion = direccionService.update(usuarioJPA.direcciones.get(0));
            } catch (Exception ex) {
                ex.getCause();
            }
            return "redirect:/Usuario/detail/" + usuario.getIdUsuario();
//            return "redirect:/Usuario";

        } else if ((usuario.getIdUsuario() > 0 && usuario.direcciones.get(0).getIdDireccion() == 0)) { // agregar direccion
            ModelMapper modelMapper = new ModelMapper();
//            Direccion direccion = new Direccion();
//            usuario.direcciones.add(direccion);
            try {
                vPerez.ProgramacionNCapasNov2025.JPA.Direccion direccionJpa = modelMapper.map(usuario.direcciones.get(0), vPerez.ProgramacionNCapasNov2025.JPA.Direccion.class);
                direccionJpa.Usuario = new vPerez.ProgramacionNCapasNov2025.JPA.Usuario();
                direccionJpa.Usuario.setIdUsuario(usuario.getIdUsuario());
                vPerez.ProgramacionNCapasNov2025.JPA.Result resultAddDireccion = direccionService.add(direccionJpa);
                if (resultAddDireccion.Correct) {
                    redirectAttributes.addFlashAttribute("resultadoOperacion", resultAddDireccion.Object);
                }
                return "redirect:/Usuario/detail/" + usuario.getIdUsuario();
            } catch (Exception ex) {
                ex.getCause();
                return "redirect:/Usuario/detail/" + usuario.getIdUsuario();

            }
        }

        return "redirect:/Usuario/detail/" + usuario.getIdUsuario();
    }

    @GetMapping("delete/{idUsuario}")
    public String delete(@PathVariable("idUsuario") int idUsuario, RedirectAttributes redirectAttributes
    ) {

        vPerez.ProgramacionNCapasNov2025.JPA.Result resultDelete = usuarioService.delete(idUsuario);
        if (resultDelete.Correct) {
            resultDelete.Object = "El usuario " + idUsuario + " se eliminó correctamente";
        } else {
            resultDelete.Object = "El usuario  no se pudo eliminar";
        }
        redirectAttributes.addFlashAttribute("resultDelete", resultDelete);
        return "redirect:/Usuario";

    }

    @GetMapping("softDelete/{idUsuario}/{estatus}")
    @ResponseBody
    public vPerez.ProgramacionNCapasNov2025.JPA.Result softDelete(@PathVariable("idUsuario") int idUsuario, @PathVariable("estatus") int estatus, RedirectAttributes redirectAttributes
    ) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setEstatus(estatus);

        ModelMapper modelMapper = new ModelMapper();

        vPerez.ProgramacionNCapasNov2025.JPA.Usuario usuarioJpa = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.JPA.Usuario.class);
        vPerez.ProgramacionNCapasNov2025.JPA.Result resultSoftDel = usuarioService.softDelete(usuarioJpa);
//        if(resultSoftDel.Correct){
//            redirectAttributes.addFlashAttribute("")
//        }
        return resultSoftDel;
    }

    @GetMapping("direccion/delete/{idDireccion}/{idUsuario}")
    public String deleteDireccion(@PathVariable("idDireccion") int idDireccion, @PathVariable("idUsuario") String idUsuario, RedirectAttributes redirectAttributes
    ) {
        vPerez.ProgramacionNCapasNov2025.JPA.Result resultDelete = direccionService.delete(idDireccion);
        if (resultDelete.Correct) {
            redirectAttributes.addFlashAttribute("resultadoOperacion", resultDelete.Object);
        }

        return "redirect:/Usuario/detail/" + idUsuario;//Lleva al endpoint
//        return "Index; --- LLeva a una plantilla
    }

    @GetMapping("detail/{idUsuario}")
    public String getUsuario(@PathVariable("idUsuario") int idUsuario, Model model,
            RedirectAttributes redirectAttributes
    ) {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = aut.getPrincipal();
//        model permite cargar informacion desde el backend en la vistas(frontend)
        int idUsuarioRegistrado = (Integer) usuarioService.getIdByEmail(aut.getName()).Object;

        vPerez.ProgramacionNCapasNov2025.JPA.Result result = usuarioService.getById(idUsuario);

        vPerez.ProgramacionNCapasNov2025.JPA.Result resultRol = rolService.getAll();
        vPerez.ProgramacionNCapasNov2025.JPA.Result resultPais = paisService.getAll();
        model.addAttribute("Paises", resultPais.Objects);
//        Result resultUsuario = usuarioDaoImplementation.GetById(idUsuario);
        model.addAttribute("Roles", resultRol.Objects);//Agregado 12/12/2025

        model.addAttribute("UsuarioAutenticado", idUsuarioRegistrado);
        model.addAttribute("Usuario", result.Object);

        return "detalleUsuario";
    }

    @GetMapping("detail")
    public String getUsuario(Model model) {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = aut.getPrincipal();
//        model permite cargar informacion desde el backend en la vistas(frontend)
        int idUsuarioRegistrado = (Integer) usuarioService.getIdByEmail(aut.getName()).Object;

        vPerez.ProgramacionNCapasNov2025.JPA.Result result = usuarioService.getById(idUsuarioRegistrado);

        vPerez.ProgramacionNCapasNov2025.JPA.Result resultRol = rolService.getAll();
        vPerez.ProgramacionNCapasNov2025.JPA.Result resultPais = paisService.getAll();
        model.addAttribute("Paises", resultPais.Objects);
//        Result resultUsuario = usuarioDaoImplementation.GetById(idUsuario);
        model.addAttribute("Roles", resultRol.Objects);//Agregado 12/12/2025

        model.addAttribute("UsuarioAutenticado", idUsuarioRegistrado);
        model.addAttribute("Usuario", result.Object);

        return "detalleUsuario";

    }

    @GetMapping("direccionForm/{idUsuario}")
    @ResponseBody
    public vPerez.ProgramacionNCapasNov2025.JPA.Result getDireccion(@PathVariable("idDireccion") int idDireccion, Model model,
            RedirectAttributes redirectAttributes
    ) {
//        Result result = usuarioDaoImplementation.GetDireccionUsuarioById(idUsuario);
//        Result result = usuarioJpaDAOImplementation.getDireccionUsuarioById(idUsuario);
        vPerez.ProgramacionNCapasNov2025.JPA.Result result = direccionService.getById(idDireccion);

        vPerez.ProgramacionNCapasNov2025.JPA.Result resultRol = rolService.getAll();

        redirectAttributes.addFlashAttribute("Roles", resultRol.Objects);//Agregado 12/12/2025
        model.addAttribute("UsuarioD", result.Object);

        return result;
    }

    @GetMapping("getEstadoByPais/{idPais}")
    @ResponseBody
    public vPerez.ProgramacionNCapasNov2025.JPA.Result getEstadoByPais(@PathVariable int idPais
    ) {
//        Result result = estadoJpaDAOImplementation.getByPais(idPais);
        vPerez.ProgramacionNCapasNov2025.JPA.Result result = estadoService.getByPais(idPais);

        return result;
    }

    @GetMapping("getMunicipioByEstado/{idEstado}")
    @ResponseBody
    public vPerez.ProgramacionNCapasNov2025.JPA.Result getMunicipioByEstado(@PathVariable("idEstado") int idEstado
    ) {
//        Result result = municipioJpaDAOImplementation.getByEstado(idEstado);
        vPerez.ProgramacionNCapasNov2025.JPA.Result result = municipioService.getByEstado(idEstado);

        return result;
    }

    @GetMapping("getColoniaByMunicipio/{idMunicipio}")
    @ResponseBody
    public vPerez.ProgramacionNCapasNov2025.JPA.Result getColoniaByMunicipio(@PathVariable("idMunicipio") int idMunicipio
    ) {
//        Result result = coloniaDaoImplementation.getColoniaByMunicipio(idMunicipio);
        vPerez.ProgramacionNCapasNov2025.JPA.Result result = coloniaService.getByMuncipio(idMunicipio);
        return result;
    }

    //Carga la pagina de carga masiva
    @GetMapping("CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("/CargaMasiva")
    public String CargaMasiva(@ModelAttribute MultipartFile archivo, Model model,
            HttpSession sesion) throws IOException {

        //CARGA DE ARCHIVOS
        //divide el nombre del archivo en 2 partes, una es el nombre y la otra es despues del punto(extension) 
        //Para revisar que sea la extensión solicitada
        String extension = archivo.getOriginalFilename().split("\\.")[1];

        //Obteniendo la ruta base la que viene del disco del sistema
        String ruta = System.getProperty("user.dir");

        // Ruta desde el proyecto
        String rutaArchivo = "src\\main\\resources\\archivos";

        //Obteniendo la fecha para que sirva de id
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        //Esta es la ruta absoluta del archivo(donde se va a guardar en el proyecto)
        String rutaAbsoluta = ruta + "/" + rutaArchivo + "/" + fecha + archivo.getOriginalFilename();

        archivo.transferTo(new File(rutaAbsoluta));
//        Files.copy(archivo.getInputStream(), Paths.get(rutaAbsoluta));
        List<Usuario> usuarios = new ArrayList<>();

        //¿Cual archivo debe leer?
        if (extension.equals("txt")) {
            usuarios = LeerArchivo(new File(rutaAbsoluta));
        } else {
            usuarios = LeerArchivoExcel(new File(rutaAbsoluta));
        }

        //validacion de archivo
        List<ErrorCarga> errores = validarDatos(usuarios);
        model.addAttribute("Errores", errores);
        if (!errores.isEmpty()) {
            model.addAttribute("Errores", errores);//Mandando errores
            model.addAttribute("isError", true);

        } else {
            model.addAttribute("isError", false);
            sesion.setAttribute("archivoCargaMasiva", rutaAbsoluta);//Añadiendo atributos a la ruta
        }

        return "CargaMasiva";
    }

    public List<Usuario> LeerArchivo(File archivo) {//
        List<Usuario> usuarios = new ArrayList<>();
        try (
                //                InputStream inputStream = archivo.getInputStream(); //inpuStream lee los bytes de un archivo, en este caso el archivo que le estamos indicando
                //Lee texto desde un archivo de entrada(nuestro input stream):
                //                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

                BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))) {

            bufferedReader.readLine(); //solo lee el encabezado que añadimos al txt
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                //datos representa cada columna(campo)
                String[] datos = linea.split("\\|");

                Usuario usuario = new Usuario();

                usuario.setNombre(datos[0].trim());
                usuario.setApellidoPaterno(datos[1].trim());
                usuario.setApellidoMaterno(datos[2].trim());
                usuario.setEmail(datos[3].trim());
                usuario.setPassword(datos[4].trim());
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                usuario.setFechaNacimiento(formato.parse(datos[5]));
                usuario.rol = new Rol();
                usuario.rol.setIdRol(Integer.valueOf(datos[6].trim())); // le quité lso espacios para que fuese un formato que pueda transformar
                usuario.setSexo(datos[7].trim());
                usuario.setTelefono(datos[8].trim());
                usuario.setCelular(datos[9].trim());
                usuario.setCurp(datos[10].trim());
                usuario.direcciones = new ArrayList<>();
                usuario.direcciones.add(new Direccion());
                usuario.direcciones.get(0).setCalle(datos[11].toString().trim());
                usuario.direcciones.get(0).setNumeroInterior(datos[12].toString().trim());
                usuario.direcciones.get(0).setNumeroExterior(datos[13].toString().trim());
                usuario.direcciones.get(0).colonia = new Colonia();
                usuario.direcciones.get(0).colonia.setIdColonia(Integer.valueOf(datos[14].trim()));

                usuarios.add(usuario);

                System.out.println("leyendo datos: " + linea);
            }

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return usuarios;
    }

    public List<Usuario> LeerArchivoExcel(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();
//Cambió de archivo.getInputStream() a archivo
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
//                if (row.getRowNum() == 0) {
//                    System.out.println("Encabezados");
//                } else {

                Usuario usuario = new Usuario();
                usuario.setNombre(row.getCell(0).toString());
                usuario.setApellidoPaterno(row.getCell(1).toString());
                usuario.setApellidoMaterno(row.getCell(2).toString());
                usuario.setEmail(row.getCell(3).toString());
                usuario.setPassword(row.getCell(4).toString());
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                usuario.setFechaNacimiento(formatoFecha.parse(row.getCell(5).toString()));
                usuario.rol = new Rol();
                int IdRol = Integer.parseInt(row.getCell(6).toString());
                usuario.rol.setIdRol(IdRol);
                //  usuario.rol.setIdRol((Float.valueOf(row.getCell(6).toString().trim())).intValue());
                usuario.setSexo(row.getCell(7).toString());  //datos nulos
                usuario.setTelefono(row.getCell(8).toString());//
                usuario.setCelular(row.getCell(9).toString());
                usuario.setCurp(row.getCell(10).toString());
                usuario.direcciones = new ArrayList<>();
                usuario.direcciones.add(new Direccion());
                usuario.direcciones.get(0).setCalle(row.getCell(11).toString());
                usuario.direcciones.get(0).setNumeroInterior(row.getCell(12).toString());
                usuario.direcciones.get(0).setNumeroExterior(row.getCell(13).toString());
                usuario.direcciones.get(0).colonia = new Colonia();
                usuario.direcciones.get(0).colonia.setIdColonia(Integer.parseInt(row.getCell(14).toString()));
                usuarios.add(usuario);
//                }

            }

        } catch (Exception ex) {
            System.out.println(ex.getCause() + " :" + ex.getLocalizedMessage());
        }
        return usuarios;
    }

    //Lista de errores (contendrá todos los atributos de la clase)
    public List<ErrorCarga> validarDatos(List<Usuario> usuarios) {
        List<ErrorCarga> erroresCarga = new ArrayList<>();//Se almacenarán todos los errores
        int lineaError = 0;

        //Iterando sobre la lista que le pasamos al metodo como argumento
        for (Usuario usuario : usuarios) {
            List<ObjectError> errors = new ArrayList();
            lineaError++;
            BindingResult bindingResultUsuario = ValidationService.validateObjects(usuario);//validando cada usuario
            if (bindingResultUsuario.hasErrors()) {
                errors.addAll(bindingResultUsuario.getAllErrors());
            }
            if (usuario.direcciones.get(0) != null) {
                BindingResult bindingDireccion = ValidationService.validateObjects(usuario.direcciones.get(0));
                if (bindingDireccion.hasErrors()) {
                    errors.addAll(bindingDireccion.getAllErrors());
                }
            }
//            List<ObjectError> errores = bindingResult.getAllErrors(); //Obteniendo los errores y guardandolos

            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;//obteniendo cada error especifico en cada campo(field)
                ErrorCarga errorCarga = new ErrorCarga();//Instancia de DTO ErrorCarga
                errorCarga.linea = lineaError;
                errorCarga.campo = fieldError.getField();//obtiendo el campo del error
                errorCarga.descripcion = fieldError.getDefaultMessage();//guardando mensaje de error
                erroresCarga.add(errorCarga); //Guardando cada error en la lista de errores
            }
        }

//        model.addAttribute("Errores",erroresCarga);
        return erroresCarga;
    }

    @GetMapping("/CargaMasiva/Procesar")
    public String ProcesarArchivo(HttpSession sesion, Model model) {
        //Obteniendo ruta del archivo que se registró en metodo CargaMasiva()
        String ruta = sesion.getAttribute("archivoCargaMasiva").toString();
        String extensionArchivo = new File(ruta).getName().split("\\.")[1];
//        Result result;

        if (extensionArchivo.equals("txt")) {
            List<Usuario> usuarios = LeerArchivo(new File(ruta));
//            usuarioDaoImplementation.AddMany(usuarios);
            ModelMapper modelMapper = new ModelMapper();
            List<vPerez.ProgramacionNCapasNov2025.JPA.Usuario> usuariosJPA = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                vPerez.ProgramacionNCapasNov2025.JPA.Usuario usuarioJPA = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.JPA.Usuario.class);
                usuariosJPA.add(usuarioJPA);
            }
//            usuarioDaoImplementation.AddMany(usuarios);
            vPerez.ProgramacionNCapasNov2025.JPA.Result resultCargaMasiva = usuarioService.addAll(usuariosJPA);

        } else {
            //Guardando usuarios de la lista de usuarios creada con el metodo leer archivo
            List<Usuario> usuarios = LeerArchivoExcel(new File(ruta));
            ModelMapper modelMapper = new ModelMapper();
            List<vPerez.ProgramacionNCapasNov2025.JPA.Usuario> usuariosJPA = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                vPerez.ProgramacionNCapasNov2025.JPA.Usuario usuarioJPA = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.JPA.Usuario.class);
                usuariosJPA.add(usuarioJPA);
            }
//            usuarioDaoImplementation.AddMany(usuarios);
//            usuarioJpaDAOImplementation.addMany(usuariosJPA);
            usuarioService.addAll(usuariosJPA);

        }
        sesion.removeAttribute("archivoCargaMasiva");
//        new File(ruta).delete();//Ya cuando se terminaron las operaciones con el archivo, se elimina de la carpeta

        return "redirect:/Usuario";
    }

    @GetMapping("/Search")
    public String buscarUsuarios(@ModelAttribute("Usuario") Usuario usuario, Model model) {
        model.addAttribute("UsuarioBusqueda", new Usuario());//creando usuario(vacio) para que pueda mandarse la busqueda
//        Result resultSearch = usuarioDaoImplementation.search(usuario);

        ModelMapper modelMapper = new ModelMapper();
        vPerez.ProgramacionNCapasNov2025.JPA.Usuario usuarioJPA = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.JPA.Usuario.class);
        vPerez.ProgramacionNCapasNov2025.JPA.Result resultSearch = usuarioService.getDinamico(usuarioJPA);
        if (resultSearch.Objects.size() == 0) {
            model.addAttribute("Empty", "No hay resultados para tu busqueda");
        } else {

            vPerez.ProgramacionNCapasNov2025.JPA.Result resultRoles = rolService.getAll();
            model.addAttribute("Roles", resultRoles.Objects);
            model.addAttribute("Usuarios", resultSearch.Objects);
            model.addAttribute("usuariosEstatus", resultSearch.Objects);
        }
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Object principal = aut.getPrincipal();
        String nombreRol = aut.getAuthorities().iterator().next().getAuthority();
//        model permite cargar informacion desde el backend en la vistas(frontend)
        int idUsuario = (Integer) usuarioService.getIdByEmail(aut.getName()).Object;
        model.addAttribute("idUsuario", idUsuario);
        model.addAttribute("UsuarioAutenticado", principal);
        model.addAttribute("Usuario", usuarioService.getById(idUsuario).Object);

        return "Index";

    }

}
