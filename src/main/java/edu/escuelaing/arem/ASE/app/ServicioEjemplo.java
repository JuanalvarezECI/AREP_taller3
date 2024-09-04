package edu.escuelaing.arem.ASE.app;

import edu.escuelaing.arem.ASE.app.Annotations.GetMapping;
import edu.escuelaing.arem.ASE.app.Annotations.RESTcontroller;
import edu.escuelaing.arem.ASE.app.Annotations.RequestParam;

@RESTcontroller
public class ServicioEjemplo {
    private static final String template = "Hello, %s!";
    @GetMapping("c")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }

    @GetMapping("/hello")
    public static  String hello(){
        return "hello";
    }
    @GetMapping("/sumade1mas2")
    public static Integer sumade1mas2(){
        int n1 = 1;
        int n2 = 2;
        return n1+n2;
    }
    @GetMapping("/nombre")
        public static String nombre(){
        return "juan jose";
    }
    @GetMapping("/documento")
    public static String documento(){
        return "1000241337";
    }

    @GetMapping("/pi")
    public static  String pi(){
        return String.valueOf(Math.PI);
    }

}
