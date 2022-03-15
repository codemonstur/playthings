package lang;

public enum BitsAndBytes {;

    // Really should live in Integer class, but it's not there so..
    public static byte[] toByteArray(final int data) {
        return new byte[] {
            (byte)((data >> 24) & 0xff),
            (byte)((data >> 16) & 0xff),
            (byte)((data >>  8) & 0xff),
            (byte)((data >>  0) & 0xff),
        };
    }

    public static byte[] decodeHex(final String input) {
        final byte[] data = new byte[input.length() / 2];
        for (int i = 0; i < input.length(); i += 2) {
            data[i / 2] = (byte)
                ((Character.digit(input.charAt(i), 16) << 4)
                + Character.digit(input.charAt(i + 1), 16));
        }
        return data;
    }

    public static void xorInto(final byte[] data, final byte[] key) {
        for (int i = 0; i < 4; i++) data[i] = (byte) (data[i] ^ key[i]);
    }

    public static void xorInto(final byte[] data, final int dataOffset, final byte[] key, final int keyOffset) {
        final int length = Math.min(key.length, data.length);
        for (int i = 0; i < length; i++) data[dataOffset+i] = (byte) (data[dataOffset+i] ^ key[keyOffset+i]);
    }

}
