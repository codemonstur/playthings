package unit.util;

import crypto.feistel.Round;

public enum SimpleIntegerRounds {;

    public static Round multiplyByKey(final int key) {
        return (number, data) -> {
            for (int i = 0; i < data.length; i++)
                data[i] = (byte) (data[i] * key);
            return data;
        };
    }

    public static byte[] addFive(final int number, final byte[] data) {
        for (int i = 0; i < data.length; i++)
            data[i] = (byte) (data[i] + 5);
        return data;
    }

}
