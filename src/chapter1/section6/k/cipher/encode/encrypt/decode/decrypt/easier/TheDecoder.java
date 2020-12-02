package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * Created by Rene Argento on 26/11/20.
 */
public class TheDecoder {

    public static void main(String[] args) throws IOException {
        DataInputStream inputStream = new DataInputStream(System.in);
        DataOutputStream outputStream = new DataOutputStream(System.out);

        try {
            byte byteValue = inputStream.readByte();

            while (byteValue != -1) {
                if (byteValue != 10 && byteValue != 13) {
                    outputStream.write(byteValue - 7);
                } else {
                    outputStream.write(byteValue);
                }
                byteValue = inputStream.readByte();
            }
        } catch (EOFException exception) {
            // End of file reached
        }
    }
}
