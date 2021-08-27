package unit;

import org.junit.Test;
import unit.util.SimpleIntegerRounds;

import static crypto.feistel.FeistelCipher.newFeistelCipher;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;

public class FeistelCipherTest {

    @Test
    public void encryptThenDecrypt() {
        final byte[] input = "01234567890123456789012345678901".getBytes(UTF_8);
        final var cipher = newFeistelCipher(5)
            .blockSize(input.length)
            .addRound(SimpleIntegerRounds::multiplyByKey)
            .addRound(SimpleIntegerRounds::addFive)
            .build();

        final byte[] cipherText = cipher.encrypt(input);
        final byte[] plainText = cipher.decrypt(cipherText);

        assertArrayEquals("Original input and plaintext are not equal", input, plainText);
    }

}
