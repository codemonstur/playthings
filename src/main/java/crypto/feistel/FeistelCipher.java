package crypto.feistel;

import crypto.BlockCipher;

import java.util.ArrayList;
import java.util.List;

public enum FeistelCipher {;

    public interface BlockSizeStep {
        FirstRoundStep blockSize(int blockSize);
    }
    public interface FirstRoundStep {
        RoundStep addRepeatedRound(int number, Round round);
        RoundStep addRound(Round round);
    }
    public interface RoundStep {
        RoundStep addRepeatedRound(int number, Round round);
        RoundStep addRound(Round round);
        BlockCipher build();
    }

    public static BlockSizeStep newFeistelCipher() {
        return new Builder();
    }

    public static class Builder implements BlockSizeStep, FirstRoundStep, RoundStep {

        private final List<Round> rounds = new ArrayList<>();
        private int halfBlockSize;

        public FeistelCipher.Builder blockSize(final int blockSize) {
            if (blockSize <= 0 || blockSize % 2 != 0)
                throw new IllegalArgumentException("Block size must be a positive even integer");
            halfBlockSize = blockSize / 2;
            return this;
        }
        public FeistelCipher.Builder addRound(final Round round) {
            rounds.add(round);
            return this;
        }
        public FeistelCipher.Builder addRepeatedRound(final int number, final Round round) {
            for (int i = 0; i < number; i++) rounds.add(round);
            return this;
        }

        public BlockCipher build() {
            final int blockSize = halfBlockSize * 2;
            return new BlockCipher() {
                public byte[] encrypt(byte[] data) {
                    if (data.length != halfBlockSize * 2)
                        throw new IllegalArgumentException("Length of data block must be " + (halfBlockSize * 2));

                    // run through the rounds
                    byte[] block = data;
                    for (int i = 0; i < rounds.size(); i++)
                        block = doRound(rounds.get(i), i, data);

                    // swap left and right
                    final byte[] lastBlock = new byte[data.length];
                    System.arraycopy(block, halfBlockSize, lastBlock, 0, halfBlockSize);
                    System.arraycopy(block, 0, lastBlock, halfBlockSize, halfBlockSize);
                    return lastBlock;
                }
                public byte[] decrypt(byte[] block) {
                    return encrypt(block);
                }
                public int blockSize() {
                    return blockSize;
                }
            };
        }

        private byte[] doRound(final Round round, final int number, final byte[] data) {
            // get the right side
            final byte[] rightSide = new byte[halfBlockSize];
            System.arraycopy(data, halfBlockSize, rightSide, 0, rightSide.length);

            // make the round do its work and XOR
            final byte[] output = round.doRound(number, rightSide);
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
