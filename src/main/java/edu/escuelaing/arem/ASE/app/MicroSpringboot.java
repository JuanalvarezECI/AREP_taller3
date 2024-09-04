package edu.escuelaing.arem.ASE.app;

import edu.escuelaing.arem.ASE.app.Annotations.GetMapping;
import edu.escuelaing.arem.ASE.app.Annotations.RESTcontroller;
import edu.escuelaing.arem.ASE.app.Annotations.RequestParam;
import edu.escuelaing.arem.ASE.app.Server.SimpleWebServer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MicroSpringboot {
    private static final Map<String, Method> services = new HashMap<>();

    public static void main(String[] args) throws Exception {
        String className = args[0];
        Class<?> clazz = Class.forName(className);

        if (clazz.isAnnotationPresent(RESTcontroller.class)) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    String path = method.getAnnotation(GetMapping.class).value();
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