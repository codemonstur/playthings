package password;

import fr.cryptohash.*;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.String.format;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public enum Hashing {;

    // These are all NIST SHA3 finalists. These algorithms were chosen in this list
    // because they all have the same digest length. PANAMA is not included because it is
    // considered broken.
    // TODO find an implementation of Blake2 and blake3
    private static final Map<Long, Supplier<Digest>> HASHING_ALGORITHMS = ofEntries(
        entry(0L, SHA256::new), entry(1L, BLAKE256::new),
        entry(2L, BMW256::new), entry(3L, CubeHash256::new),
        entry(4L, ECHO256::new), entry(5L, Fugue256::new),
        entry(6L, Groestl256::new), entry(7L, HAVAL256_3::new),
        entry(8L, HAVAL256_4::new), entry(9L, HAVAL256_5::new),
        entry(10L, Hamsi256::new), entry(11L, JH256::new),
        entry(12L, Keccak256::new), entry(13L, Luffa256::new),
        entry(14L, SHAvite256::new), entry(15L, SIMD256::new),
        entry(16L, Shabal256::new), entry(17L, Skein256::new),
        entry(18L, RadioGatun32::new), entry(19L, RadioGatun64::new)
    );

    public static final List<Long> ALGORITHM_IDS = List.copyOf(HASHING_ALGORITHMS.keySet());

    private static final Map<Long, HashingStrategy> HASHING_STRATEGY = Map.of(
        0L, HashingStrategy::hashSaltThenPass,
        1L, HashingStrategy::hashPassThenSalt
    );
    public static final List<Long> STRATEGY_IDS = List.copyOf(HASHING_STRATEGY.keySet());

    public static byte[] hashPassword(
            final byte[] pass, final long algorithm, final long strategy,
            final long iterations, final byte[] salt)
    {
        if (!HASHING_ALGORITHMS.containsKey(algorithm))
            throw new IllegalArgumentException(format("Unknown hashing algorithm %d", algorithm));
        if (!HASHING_STRATEGY.containsKey(strategy))
            throw new IllegalArgumentException(format("Unknown hashing strategy %d", strategy));

        final Digest digest = HASHING_ALGORITHMS.get(algorithm).get();
        return HASHING_STRATEGY.get(strategy).hash(pass, digest, iterations, salt);
    }

}
