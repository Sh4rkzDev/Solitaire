import java.io.*;

public class Serializer {
    public static void serialize(String path, Solitaire solitaire) throws IOException {
        ObjectOutputStream obj = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
        obj.writeObject(solitaire);
        obj.close();
    }

    public Solitaire deserialize(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)));
        Solitaire res = (Solitaire) obj.readObject();
        obj.close();
        return res;
    }
}
