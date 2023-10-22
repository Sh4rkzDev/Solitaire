import java.io.IOException;

public interface Serialization {
    void serialize(String path) throws IOException;

    Solitaire deserialize(String path) throws IOException, ClassNotFoundException;
}
