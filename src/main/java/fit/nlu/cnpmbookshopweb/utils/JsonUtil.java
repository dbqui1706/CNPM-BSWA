package fit.nlu.cnpmbookshopweb.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtil {
    public static <T> T get(HttpServletRequest request, Class<T> tClass) throws IOException {
        request.setCharacterEncoding("UTF-8");
        return new Gson().fromJson(request.getReader(), tClass);
    }

    public static <T> void out(HttpServletResponse response, T object, int status){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.print(json);
        writer.flush();
    }
}
