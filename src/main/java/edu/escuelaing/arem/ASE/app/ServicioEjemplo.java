package edu.escuelaing.arem.ASE.app;

import edu.escuelaing.arem.ASE.app.Annotations.*;
@RESTcontroller
public class ServicioEjemplo {

    private static final String template = "Hello, %s!";

    @GetMapping("/hello")
    public static  String hello(){
        return "hello";
    }
    @GetMapping("/moto")
    public static String moto(){
        return "susuki_gsxr_600";
    }
    @GetMapping("/nombre")
        public static String nombre(){
        return "juan_jose";
    }

    @GetMapping("/documento")
    public static String documento(){
        return "1000241337";
    }

    @GetMapping("/pi")
    public static  String pi(){

        return String.valueOf(Math.PI);
    }
    @RequestMapping("/")
    public static String index() {
        return "Greetings from Spring Boot!";
    }

}
