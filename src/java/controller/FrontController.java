package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logtrack.ExceptionLogTrack;
import model.TipoUsuario;
import model.Usuario;

@WebServlet(name = "FrontController", urlPatterns = {"/home"})
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String task = request.getParameter("task");

        if (task == null) {
            task = "";
        }

        try {
            switch (task) {
                case "tipousuario":
                    doGetTipoUsuario(request, response);
                    break;
                case "usuarios":
                    doGetUsuarios(request, response);
                    break;
                default:
                    doDefault(request, response);
            }
        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String task = request.getParameter("task");

        if (task == null) {
            task = "";
        }

        try {
            switch (task) {
                case "tipousuario":
                    doPostTipoUsuario(request, response);
                    break;
                case "usuarios":
                    doPostUsuarios(request, response);
                    break;
                default:
                    doDefault(request, response);
            }
        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
    }

    private void doGetTipoUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        if ((action != null) && action.equals("delete")) {
            int id = Integer.valueOf(request.getParameter("id"));
            TipoUsuario tp = new TipoUsuario();
            tp.setId(id);

            tp.delete();
        }

        response.sendRedirect(request.getContextPath() + "/home/app/tipousuario.jsp");
    }

    private void doGetUsuarios(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        if ((action != null) && action.equals("delete")) {
            int id = Integer.valueOf(request.getParameter("id"));
            Usuario user = new Usuario();
            user.setId(id);

            user.delete();
        }

        response.sendRedirect(request.getContextPath() + "/home/app/usuarios.jsp");
    }

    private void doPostTipoUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");

        int id = Integer.valueOf(request.getParameter("id"));
        String nome = request.getParameter("nome");

        String moduloAdministrativo = request.getParameter("modulo_administrativo");
        if (moduloAdministrativo == null) {
            moduloAdministrativo = "N";
        }

        String moduloAgendamento = request.getParameter("modulo_agendamento");
        if (moduloAgendamento == null) {
            moduloAgendamento = "N";
        }

        String moduloAtendimento = request.getParameter("modulo_atendimento");
        if (moduloAtendimento == null) {
            moduloAtendimento = "N";
        }

        TipoUsuario tp = new TipoUsuario();

        tp.setId(id);

        if (action.equals("update")) {
            tp.load();
        }

        tp.setNome(nome);
        tp.setModuloAdministrativo(moduloAdministrativo);
        tp.setModuloAgendamento(moduloAgendamento);
        tp.setModuloAtendimento(moduloAtendimento);

        tp.save();

        response.sendRedirect(request.getContextPath() + "/home/app/tipousuario.jsp");
    }

    private void doPostUsuarios(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");

        int id = Integer.valueOf(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        int tipoUsuarioId = Integer.valueOf(request.getParameter("tipo_usuario_id"));

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

        response.sendRedirect(request.getContextPath() + "/home/app/usuarios.jsp");
    }

    private void doDefault(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect("home/login.jsp");
    }

}
