package edu.escuelaing.arem.ASE.app;

import edu.escuelaing.arem.ASE.app.Annotations.*;
import edu.escuelaing.arem.ASE.app.Server.SimpleWebServer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MicroSpringboot{
    private static final Map<String, Method> services = new HashMap<>();

    public static void main(String[] args) throws Exception {
        List<Class<?>> classes = ClasspathScanner.findAnnotatedClasses("edu.escuelaing.arem.ASE.app", RESTcontroller.class);
        for (Class<?> clazz : classes) {
            System.out.println("Loading class: " + clazz.getName());
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    String path = method.getAnnotation(GetMapping.class).value();
                    System.out.println("Registering GET method: " + path);
                    services.put(path, method);
                } else if (method.isAnnotationPresent(RequestMapping.class)) {
                    String path = method.getAnnotation(RequestMapping.class).value();
                    System.out.println("Registering REQUEST method: " + path);
                    services.put(path, method);
                }
            }
        }
        // Start the web server
        SimpleWebServer server = new SimpleWebServer(8080);
        server.start();
    }

    public static String handleRequest(String path, Map<String, String> params) throws Exception {
        Method method = services.get(path);
        if (method != null) {
            Object[] args = new Object[method.getParameterCount()];
            for (int i = 0; i < method.getParameterCount(); i++) {
                if (method.getParameters()[i].isAnnotationPresent(RequestParam.class)) {
                    RequestParam requestParam = method.getParameters()[i].getAnnotation(RequestParam.class);
                    String paramName = requestParam.value();
                    String defaultValue = requestParam.defaultValue();
                    args[i] = params.getOrDefault(paramName, defaultValue);
                }
            }
            return (String) method.invoke(null, args);
        }
        return "404 Not Found";
    }
}