package aulas.servlet.ciclo;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@WebServlet(name = "Ciclo", urlPatterns = {"/aulas/servlet/ciclo"})
public class Ciclo extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        /*
        inicialização do servlet
        
        leitura os parametros iniciais e criacao
        */
        super.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        recebimento de requisicoes HTTP enquanto o servlet estiver ativo
        
        super.service(...) encaminha as requisicoes aos metodos HTTP implementados
        */
//        super.service(req, resp);

        System.out.println("\n --- HeaderNames Request");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            System.out.println(name + " : " + request.getHeader(name));
        }
        
        String html = "<!DOCTYPE html>";
        html += "<html>";
        html += "<head>";
        html += "<title>Servlet Ciclo de Vida</title>";
        html += "</head>";
        html += "<body>";
        html += "<h1>Ciclo de Vida de um Servlet</h1>";
        html += "</body>";
        html += "</html>";
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter pw = response.getWriter();
        pw.write(html);
        pw.close();
        
        System.out.println("\n --- HeaderNames Response");
        for (String name: response.getHeaderNames()) {
            System.out.println(name + " : " + response.getHeader(name));
        } 
        System.out.println("--- HeaderNames Response\n");
    }

    @Override
    public void destroy() {
        /*
        encerramento do servlet, liberacao dos recursos
        
        chamado quando o servlet for encerrado, utilizado para liberacao de recursos (como conexao a banco de  dados)
        */
        super.destroy();
    }

}
