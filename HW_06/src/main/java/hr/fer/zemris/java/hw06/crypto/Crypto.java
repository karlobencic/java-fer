package hr.fer.zemris.java.hw06.crypto;

import java.io.*;
import java.nio.file.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The {@code Crypto} program allows encryption and decryption of files using
 * the AES crypto-algorithm and the 128-bit encryption key or verifying the
 * SHA-256 file digest.
 * 
 * Available commands are:
 * <ul>
 * <li>encrypt <em>inputFile</em></li>
 * <li>decrypt <em>inputFile outputFile</em></li>
 * <li>checksha <em>inputFile outputFile</em></li>
 * </ul>
 * 
 * @author Karlo Bencic
 * 
 */
public class Crypto {

	/** Buffer size for I/O in bytes. */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			String command = args[0];
			if (command.equals("checksha") && args.length == 2) {
				checkSha(args[1]);
			} else if (command.equals("encrypt") && args.length == 3) {
				Aes128(args[1], args[2], CryptoMode.ENCRYPT);
			} else if (command.equals("decrypt") && args.length == 3) {
				Aes128(args[1], args[2], CryptoMode.DECRYPT);
			} else {
				System.out.println("Invalid command.");
			}
		}
	}

	/**
	 * Calculates SHA-256 for the input file and compares it with the given hash
	 * from input. If the digest does not match, it also prints the real file
	 * digest. SHA-256 algorithm generates an almost-unique, fixed size 256-bit
	 * (32-byte) hash. Hash is a one way function – it cannot be decrypted back.
	 *
	 * @param inputFileName
	 *            the input file name
	 */
	private static void checkSha(String inputFileName) {
		System.out.printf("Please provide expected SHA-256 digest for %s:%n> ", inputFileName);

		Scanner sc = new Scanner(System.in);
		byte[] expectedDigest = Util.hexToByte(sc.nextLine());
		sc.close();

		MessageDigest engine;
		try {
			engine = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Specified algorithm doesn't exist.");
			return;
		}

		try (InputStream is = Files.newInputStream(Paths.get(inputFileName), StandardOpenOption.READ)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			while (true) {
				int r = is.read(buffer);
				if (r < 1) {
					break;
				}
				engine.update(buffer, 0, r);
			}
		} catch (IOException ex) {
			System.out.println("Couldn't read file: " + inputFileName);
			return;
		}

		byte[] realDigest = engine.digest();
		boolean matches = Arrays.equals(expectedDigest, realDigest);
		System.out.printf("Digesting completed. Digest of %s %s expected digest. ", inputFileName,
				matches ? "matches" : "does not match");

		if (!matches) {
			System.out.printf("Digest was: %s", Util.byteToHex(realDigest));
		}
	}

	/**
	 * Generates either encrypted or decrypted version of the input file using
	 * AES-128 algorithm based on the crypto mode. This algorithm uses 128-bit
	 * (16 bytes) encryption key.
	 * 
	 *
	 * @param inputFileName
	 *            the input file name
	 * @param outputFileName
	 *            the output file name
	 * @param mode
	 *            the crypto mode
	 */
	private static void Aes128(String inputFileName, String outputFileName, CryptoMode mode) {
		Scanner sc = new Scanner(System.in);
		System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
		byte[] password = Util.hexToByte(sc.nextLine());
		System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n> ");
		byte[] iv = Util.hexToByte(sc.nextLine());
		sc.close();

		Cipher cipher;
		SecretKeySpec keySpec = new SecretKeySpec(password, "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode == CryptoMode.ENCRYPT ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException ex) {
			System.out.println(ex.getMessage());
			return;
		}

		try (InputStream is = Files.newInputStream(Paths.get(inputFileName), StandardOpenOption.READ);
				OutputStream os = Files.newOutputStream(Paths.get(outputFileName), StandardOpenOption.CREATE_NEW)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			while (true) {
				int r = is.read(buffer);
				if (r < 1) {
					break;
				}
				os.write(cipher.update(buffer, 0, r));
			}
			try {
				os.write(cipher.doFinal());
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				System.out.println(e.getMessage());
			}
		} catch (IOException ex) {
			System.out.printf("An error occured during %s.%nError: %s",
					mode == CryptoMode.ENCRYPT ? "encryption" : "decryption", ex.getMessage());
			return;
		}

		System.out.printf("%s completed. Generated file %s based on file %s.",
				mode == CryptoMode.ENCRYPT ? "Encryption" : "Decryption", outputFileName, inputFileName);
	}
}
