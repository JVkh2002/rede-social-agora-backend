package dio.springbootweb.model;


import javax.persistence.*;

@Entity
public class Credenciais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    )
    private int id;

    private String email;
    private String senha;
    private String usuario;

    private String hashtag;


    public String getEmail() {
        return email;
    };

    public void setEmail(String email) {
        this.email = email;
    };

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getUsuario(){
        return usuario;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public String getHashtag(){
        return hashtag;
    }

    public void setHashtag(String hashtag){
        this.hashtag = hashtag;
    }
}


