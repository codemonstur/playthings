package password;

import fr.cryptohash.Digest;

public interface HashingStrategy {

    byte[] hash(byte[] pass, Digest digest, long iterations, byte[] salt);

    public static byte[] hashSaltThenPass(
            final byte[] pass, final Digest digest, final long iterations,
            final byte[] salt)
    {
        digest.reset();
        for (long i = 0; i < iterations; i++) {
            digest.update(salt);
            digest.update(pass);
        }
        return digest.digest();
    }
    public static byte[] hashPassThenSalt(
            final byte[] pass, final Digest digest, final long iterations,
            final byte[] salt)
    {
        digest.reset();
        for (long i = 0; i < iterations; i++) {
            digest.update(pass);
            digest.update(salt);
        }
        return digest.digest();
    }

}