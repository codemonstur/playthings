package crypto.feistel;

public interface Round {
    byte[] doRound(int number, byte[] data);
}