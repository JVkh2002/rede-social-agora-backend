package dio.springbootweb.controller;

import dio.springbootweb.model.Credenciais;
import dio.springbootweb.repository.CredenciaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.HashMap;

@Controller
public class FormController {

    @Autowired
    private CredenciaisRepository credenciaisRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> handleSubmit(@RequestParam String email, @RequestParam String usuario,
                               @RequestParam String senha, @RequestParam String hashtag,
                               @RequestParam String confirmar_senha) {

        boolean emailNovo = false;
        boolean senhasIguais = true;
        boolean senhaVazia = false;
        boolean usuarioVazio = false;
        boolean hashtagVazia = false;


        String sql = "SELECT COUNT(*) FROM credenciais WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);


        if (count == 0) {
            emailNovo = true;
        }

        if(senha.equals(confirmar_senha) && emailNovo && !senha.isEmpty() && !usuario.isEmpty()
                && !hashtag.isEmpty()) {

            Credenciais credenciais = new Credenciais();
            credenciais.setEmail(email);
            credenciais.setSenha(senha);
            credenciais.setUsuario(usuario);
            credenciais.setHashtag(hashtag);
            credenciaisRepository.save(credenciais);

            System.out.println("operação realizada com sucesso");
        }

        if (!senha.equals(confirmar_senha)){
            senhasIguais = false;
        };

        if (!emailNovo) {
            System.out.println("email já cadastrado");
        }

        if (senha.isEmpty()) {
            senhaVazia = true;
        }

        if (usuario.isEmpty()) {
            usuarioVazio = true;
        }

        if (hashtag.isEmpty()) {
            hashtagVazia = true;
        }


        // Faça o que for necessário com os dados recebidos
        System.out.println("usuario: " + usuario);
        System.out.println("hashtag: " + hashtag);
        System.out.println("Senha: " + senha);
        System.out.println("Confirmar senha: " + confirmar_senha);
        System.out.println("email: " + email);
        System.out.println();

        Map<String, Object> response = new HashMap<>();
        response.put("emailnovo", emailNovo);
        response.put("usuarioVazio", usuarioVazio);
        response.put("hashtagVazio", hashtagVazia);
        response.put("senhaVazio", senhaVazia);
        response.put("senhasIguais", senhasIguais);


        return ResponseEntity.ok(response);


    }

    @PostMapping ("/verificarLogin")
    public ResponseEntity<Map<String, Object>> VerificarDadosLogin(@RequestParam String email,
                                                                   @RequestParam String senha) {

        String sqlEmail = "SELECT COUNT(*) FROM credenciais WHERE email = ?";
        int countEmail = jdbcTemplate.queryForObject(sqlEmail, Integer.class, email);

        String sqlSenha = "SELECT COUNT(*) FROM credenciais WHERE email = ? and senha = ?";
        int countSenha = jdbcTemplate.queryForObject(sqlSenha, Integer.class, email, senha);

        System.out.println(countSenha);

        boolean emailRegistrado = false;
        boolean senhaValida = false;

        if (countEmail > 0) {
            emailRegistrado = true;
        }

        if (countSenha > 0) {
            senhaValida = true;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("emailRegistrado", emailRegistrado);
        response.put("senhaValida", senhaValida);

        if (emailRegistrado && senhaValida) {
            String sqlUsuario = "SELECT usuario FROM credenciais WHERE email = ? LIMIT 1";
            String usuario = jdbcTemplate.queryForObject(sqlUsuario, String.class, email);

            String sqlHashtag = "SELECT hashtag FROM credenciais WHERE email = ? LIMIT 1";
            String hashtag = jdbcTemplate.queryForObject(sqlHashtag, String.class, email);

            response.put("usuarioRegistrado", usuario);
            response.put("hashtagRegistrada", hashtag);
        }


        return ResponseEntity.ok(response);

    }

}