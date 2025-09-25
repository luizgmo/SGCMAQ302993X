package aulas.servlet.getpost;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class GetPost extends HttpServlet {

    ArrayList<InformacaoForm> dados;
    
    @Override
    public void init() throws ServletException {
        super.init();
        dados = new ArrayList<>();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String campoA = request.getParameter("campoA");
        String opcaoA = request.getParameter("opcaoA");
        String opcaoB = request.getParameter("opcaoB");

        System.out.println(" campoA : " + campoA);
        System.out.println(" opcaoA : " + opcaoA);
        System.out.println(" opcaoB : " + opcaoB);

        if (campoA != null) {
            InformacaoForm info = new InformacaoForm();
            info.setCampoA(campoA);
            info.setOpcaoA(opcaoA);
            info.setOpcaoB(opcaoB);
            
            dados.add(info);
        }
        
        // vai gerar uma nova requisicao HTTP vinda do cliente (novos cabeçalhos de requisicao e resposta)
//        response.sendRedirect("/SGCMAQ302993X/aulas/servlet/getpost/getpost_form.html");

        response.setContentType("text/html;charset=UTF-8");

        String html = "<!DOCTYPE html>";
        html += "<html>";
        html += "<head>";
        html += "<title>Dados do Form</title>";
        html += "</head>";
        html += "<body>";
        html += "<h1>Dados do Form</h1>";
        
        html += "<table>";
        html += "<tr>";
        html += "<th>Campo A</th>";
        html += "<th>Opção A</th>";
        html += "<th>Opção B</th>";
        html += "</tr>";
        
        for (InformacaoForm info : dados) {
            html += "<tr>";
            html += "<td>" + info.getCampoA() + "</td>";
            html += "<td>" + info.getOpcaoA() + "</td>";
            html += "<td>" + info.getOpcaoB() + "</td>";
            html += "</tr>";
        }
        
        html += "</table></br></br>";
        html += "<a href=\"/SGCMAQ302993X/aulas/servlet/getpost/getpost_form.html\">Adicionar Informação</a>";
        
        html += "</body>";
        html += "</html>";
        
        PrintWriter pw = response.getWriter();
        pw.write(html);
        pw.close();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        dados.clear();
        dados = null;
    }

}
