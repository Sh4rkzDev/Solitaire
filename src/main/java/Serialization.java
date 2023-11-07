import java.io.IOException;

public interface Serialization {
    void serialize(String path) throws IOException;

    static Solitaire deserialize(String path) throws IOException, ClassNotFoundException {
        return null;
    }
}
