package crypto.feistel;

public interface Round<T> {
    byte[] doRound(T key, byte[] data);
}