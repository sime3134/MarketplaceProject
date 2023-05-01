package org.marketplace.server.common;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * A hasher for hashing and verifying password for users
 */

public class Hasher {

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
