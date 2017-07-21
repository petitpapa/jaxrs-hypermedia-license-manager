package com.petitpapa.business.licenza.control;

import com.petitpapa.business.customer.entity.Client;
import com.petitpapa.business.licenza.entity.Licenza;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ufficio
 */
public class LicenseBuilder {

    public void generateKey(Client client, Licenza l) throws UnsupportedEncodingException {
        String createEntryKeyFrom = createEntryKeyFrom(client.getUserName() + l.getProduct().getName() + String.valueOf(l.getId()) , String.valueOf(l.getLimitDate().getYear()));
        byte[] digest = createDigest(createEntryKeyFrom);
        String digestString = Base64.getEncoder().encodeToString(digest);
        String keyLicense = createKey(digestString);
        l.setGuid(keyLicense);
        Logger.getLogger(LicenseBuilder.class.getName()).log(Level.INFO, "licence key: {0}", keyLicense);
    }

    private byte[] createDigest(String createEntryKeyFrom) throws UnsupportedEncodingException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LicenseBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return md.digest(createEntryKeyFrom.getBytes("UTF-8"));
    }

    private String createEntryKeyFrom(String clientNane, String endYear) {
        return clientNane + endYear;
    }

    private String createKey(String digestString) {
        String r = "";
        String firstFiveKey = "";
        for (char k : digestString.toCharArray()) {
            if(r.length() > 29) break;
            firstFiveKey += k;
            if(!(Character.isLetterOrDigit(k))) continue;
            if (firstFiveKey.length() == 5) {
                r += firstFiveKey+" ";
                firstFiveKey = "";
            }

        }
        return r.trim().toUpperCase().replace(" ", "-");
    }

}
