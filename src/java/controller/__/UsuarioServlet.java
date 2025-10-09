package controller.__;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logtrack.ExceptionLogTrack;
import model.Usuario;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/home/usuarios"})
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ((action != null) && action.equals("delete")) {
            int id = Integer.valueOf(request.getParameter("id"));
            Usuario user = new Usuario();
            user.setId(id);

            try {
                user.delete();
            } catch (Exception ex) {
                ExceptionLogTrack.getInstance().addLog(ex);
            }
        }

        response.sendRedirect(request.getContextPath() + "/home/app/usuarios.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        int id = Integer.valueOf(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        int tipoUsuarioId = Integer.valueOf(request.getParameter("tipo_usuario_id"));

        try {
            Usuario user = new Usuario();
            user.setId(id);

            if (action.equals("update")) {
                user.load();
            }

            user.setNome(nome);
            user.setCpf(cpf);
            user.setSenha(senha);
            user.setTipoUsuarioId(tipoUsuarioId);

            user.save();
        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
        
        response.sendRedirect(request.getContextPath() + "/home/app/usuarios.jsp");
    }
}