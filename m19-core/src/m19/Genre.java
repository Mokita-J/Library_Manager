package m19;
import java.io.Serializable;

public enum Genre {

    SCITECH("Técnica e Científica"),
    FICTION("Ficção"),
    REFERENCE("Referência");
    
    private String string;
    
    Genre(String genre){
        string = genre;
    }

    @Override
    public String toString(){
        return string;
    }

}