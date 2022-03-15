package password;

import fr.cryptohash.Digest;

import java.util.function.Supplier;

public enum Hmac {;

    public static byte[] hmac(final byte[] data, final byte[] key, final Supplier<Digest> supplier) {
        final Digest digest = supplier.get();

        final int blockLength = digest.getBlockLength();
        final byte[] opad = new byte[blockLength];
        final byte[] ipad = new byte[blockLength];
        for (int i = 0; i < blockLength; i++) {
            final int k = i < key.length ? key[i] : 0;
            opad[i] = (byte)(k ^ 0x5c);
            ipad[i] = (byte)(k ^ 0x36);
        }

        digest.update(ipad);
        final byte[] inner = digest.digest(data);
        digest.update(opad);
        return digest.digest(inner);
    }

}
