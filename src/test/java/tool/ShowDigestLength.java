package tool;

import fr.cryptohash.Digest;
import fr.cryptohash.PANAMA;
import fr.cryptohash.RadioGatun32;
import fr.cryptohash.RadioGatun64;
import unit.util.Functions;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.nio.charset.StandardCharsets.UTF_8;
import static unit.util.Functions.encodeHex;

public class ShowDigestLength {

    public static void main(final String... args) {
        final String input = "Hello, world!";
        showDigest(input, PANAMA::new);
        showDigest(input, RadioGatun32::new);
        showDigest(input, RadioGatun64::new);
    }

    private static void showDigest(final String input, final Supplier<Digest> supplier) {
        final var digest = supplier.get();
        final var output = digest.digest(input.getBytes(UTF_8));
        System.out.println(encodeHex(output) + " - " + digest.getClass().getSimpleName());
    }

}
