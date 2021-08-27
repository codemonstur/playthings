package unit.util;

public enum SimpleIntegerRounds {;

    public static byte[] multiplyByKey(final int key, final byte[] data) {
        for (int i = 0; i < data.length; i++)
            data[i] = (byte) (data[i] * key);
        return data;
    }

    public static byte[] addFive(final int key, final byte[] data) {
        for (int i = 0; i < data.length; i++)
            data[i] = (byte) (data[i] + 5);
        return data;
    }

}
