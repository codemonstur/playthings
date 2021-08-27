package crypto.feistel;

import crypto.SymmetricBlockCipher;

import java.util.ArrayList;
import java.util.List;

public enum FeistelCipher {;

    public interface KeyStep<T> {
        BlockSizeStep<T> key(T key);
    }
    public interface BlockSizeStep<T> {
        RoundStep<T> blockSize(int blockSize);
    }
    public interface RoundStep<T> {
        RoundStep<T> addRound(Round<T> round);
        SymmetricBlockCipher build();
    }

    public static <T> BlockSizeStep<T> newFeistelCipher(final T key) {
        return new Builder<T>().key(key);
    }

    public static class Builder<T> implements BlockSizeStep<T>, KeyStep<T>, RoundStep<T> {

        private final List<Round<T>> rounds = new ArrayList<>();
        private T key;
        private int halfBlockSize;

        public FeistelCipher.Builder<T> key(final T key) {
            this.key = key;
            return this;
        }
        public FeistelCipher.Builder<T> blockSize(final int blockSize) {
            if (blockSize <= 0 || blockSize % 2 != 0)
                throw new IllegalArgumentException("Block size must be a positive even integer");
            this.halfBlockSize = blockSize / 2;
            return this;
        }
        public FeistelCipher.Builder<T> addRound(final Round<T> round) {
            this.rounds.add(round);
            return this;
        }

        public SymmetricBlockCipher build() {
            final int blockSize = halfBlockSize * 2;
            return new SymmetricBlockCipher() {
                public byte[] encrypt(byte[] data) {
                    if (data.length != halfBlockSize * 2)
                        throw new IllegalArgumentException("Length of data block must be " + (halfBlockSize * 2));

                    // run through the rounds
                    byte[] block = data;
                    for (final var round : rounds) {
                        block = doRound(round, key, data);
                    }

                    // swap left and right
                    final byte[] lastBlock = new byte[data.length];
                    System.arraycopy(block, halfBlockSize, lastBlock, 0, halfBlockSize);
                    System.arraycopy(block, 0, lastBlock, halfBlockSize, halfBlockSize);
                    return lastBlock;
                }
                public int blockSize() {
                    return blockSize;
                }
            };
        }

        private byte[] doRound(final Round<T> round, final T key, final byte[] data) {
            // get the right side
            final byte[] rightSide = new byte[halfBlockSize];
            System.arraycopy(data, halfBlockSize, rightSide, 0, rightSide.length);

            // make the round do its work and XOR
            final byte[] output = round.doRound(key, rightSide);
            for (int i = 0; i < output.length; i++)
                output[i] = (byte) (data[i] ^ output[i]);

            // make a new block by concatenating the right side and the output
            final byte[] newBlock = new byte[data.length];
            System.arraycopy(data, halfBlockSize, newBlock, 0, halfBlockSize);
            System.arraycopy(output, 0, newBlock, halfBlockSize, output.length);
            return newBlock;
        }
    }

}
