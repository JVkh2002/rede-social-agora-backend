package dio.springbootweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    boolean emailCadastrado = true;

    @PostMapping("/verificarEmail")
    public String VerifyEmail(@RequestParam String email){

        // Verificar se o e-mail já está cadastrado no banco de dados

        String sql = "SELECT COUNT(*) FROM cliente WHERE email_celular = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);


        if (count > 0) {
            emailCadastrado = true;
            return "redirect:http://localhost:3000/";
        } else {
            emailCadastrado = false;
            return "redirect:http://localhost:3000/login";
        }

    }

    @GetMapping("/verificarEmailGet")
    public ResponseEntity<Map<String, Object>> VerifyEmailGet(){


        Map<String, Object> response = new HashMap<>();
        response.put("redirectUrl", "http://localhost:3000/login");

        if (emailCadastrado) {
            response.put("emailCadastrado", emailCadastrado);
            return ResponseEntity.ok(response);
        } else {
            response.put("emailCadastrado", emailCadastrado);
            return ResponseEntity.ok(response);
        }

    }
}
