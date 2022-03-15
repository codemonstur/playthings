package crypto.impl;

import crypto.BlockCipher;
import crypto.feistel.Round;

import static crypto.feistel.FeistelCipher.newFeistelCipher;
import static lang.BitsAndBytes.*;

// https://www.commonlounge.com/discussion/d95616beecc148daaa23f35178691c35
public enum Blowfish {;

    private static final byte[] ALL_ZERO_BLOCK = { 0, 0, 0, 0, 0, 0, 0, 0 };

    public static BlockCipher newBlowfish(final byte[] key) {
        if (key.length != 16 && key.length != 24 && key.length != 32)
            throw new IllegalArgumentException();

        // Usually called the P-array
        // S-Boxes, what a fancy name. We call these things maps in regular old CS
        final byte[][] roundKeys = initializeRoundKeys(key);
        final int[][] sBoxes = initializeSBoxes();

        // Uses the round number to look up the round key from p. Then applies the S boxes
        final Round feistelFunction = (number, data) -> {
            xorInto(data, roundKeys[number]);
            return toByteArray(((sBoxes[0][data[0]] + sBoxes[1][data[1]]) ^ sBoxes[2][data[2]]) + sBoxes[3][data[3]]);
        };

        final var cipher = newFeistelCipher()
                .blockSize(8)
                .addRepeatedRound(16, feistelFunction)
                .build();

        byte[] keyScheduleBlock = ALL_ZERO_BLOCK;
        for (int i = 0; i < roundKeys.length; i += 2) {
            keyScheduleBlock = cipher.encrypt(keyScheduleBlock);
            System.arraycopy(keyScheduleBlock, 0, roundKeys[i], 0, roundKeys[i].length);
            System.arraycopy(keyScheduleBlock, 4, roundKeys[i+1], 0, roundKeys[i+1].length);
        }
        // FIXME still need to replace the Sboxes here

        return cipher;
    }

    // FIXME there is no where near enough data in this value of PI to fill up the roundkeys and Sboxes
    // Where do I get enough pi?
    // This is supposed to be a nothing-up-my-sleeve number: PI. Do you trust me that this is PI?
    private static final byte[] PI = decodeHex(
        "243f6a8885a308d313198a2e03707344a4093822299f31d0082efa98ec4e6c89452821e6" +
        "38d01377be5466cf34e90c6cc0ac29b7c97c50dd3f84d5b5b54709179216d5d98979fb1b"
    );
    private static byte[][] initializeRoundKeys(final byte[] key) {
        // Usually called the P-array
        final byte[][] roundKeys = new byte[18][];
        for (int i = 0; i < roundKeys.length; i++) {
            roundKeys[0] = new byte[4];
            System.arraycopy(PI, i * 4, roundKeys[0], 0, roundKeys[0].length);
            xorInto(roundKeys[0], 0, key, i * 4 % key.length);
        }
        return roundKeys;
    }
    private static int[][] initializeSBoxes() {
        final int[][] sBoxes = new int[4][256];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 4; j++) {
                sBoxes[j][j+i] = PI[j+i];
            }
        }
        return sBoxes;
    }

}
