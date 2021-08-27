package crypto;

import crypto.padding.PaddingAlgorithm;

import java.util.function.Function;

public interface EncryptionMode {

    byte[] doMode(final byte[] data, final Function<byte[], byte[]> operation);

    public static BlockCipher electronicCodeBook(final BlockCipher cipher, final PaddingAlgorithm padding) {
        return encryptionMode(cipher, (data, operation) -> {
            final int numberOfBlocks = data.length / cipher.blockSize() + 1;
            final int paddingOffset = data.length % cipher.blockSize();

            final byte[] cipherText = new byte[data.length];
            for (int i = 0; i < numberOfBlocks; i++) {
                final byte[] realBlock = new byte[cipher.blockSize()];
                System.arraycopy(data, i * cipher.blockSize(), realBlock, 0, realBlock.length);
                if (numberOfBlocks == i + 1) padding.padBlock(realBlock, paddingOffset);
                System.arraycopy(operation.apply(realBlock), 0, cipherText, i * cipher.blockSize(), realBlock.length);
            }

            return cipherText;
        });
    }

    public static BlockCipher cypherBlockChaining() {
        throw new UnsupportedOperationException("Not implemented");
    }

    private static BlockCipher encryptionMode(final BlockCipher cipher, final EncryptionMode mode) {
        return new BlockCipher() {
            public byte[] encrypt(final byte[] data) {
                return mode.doMode(data, cipher::encrypt);
            }
            public byte[] decrypt(final byte[] data) {
                return mode.doMode(data, cipher::decrypt);
            }
            public int blockSize() {
                return cipher.blockSize();
            }
        };
    }

}
