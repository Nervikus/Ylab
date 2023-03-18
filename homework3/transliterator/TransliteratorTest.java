package transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();

        String res1 = transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!");
        System.out.println(res1);

        String res2 = transliterator.transliterate("123 Hello-привет! ПРИВЕТ-привет! ПрИвЕт.");
        System.out.println(res2);
    }
}
