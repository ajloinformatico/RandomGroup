package es.lojo.randomgroup.features.emailsender

import java.security.AccessController
import java.security.PrivilegedAction
import java.security.Provider

/**
 * JSSEProvider needed to send emails
 * It has been inspirited by: https://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a
 *
 */
class JSSEProvider : Provider("HarmonyJSSE", 1.0, "Harmony JSSE Provider") {
    init {
        AccessController.doPrivileged(PrivilegedAction<Void?> {
            put(
                "SSLContext.TLS",
                "org.apache.harmony.xnet.provider.jsse.SSLContextImpl"
            )
            put("Alg.Alias.SSLContext.TLSv1", "TLS")
            put(
                "KeyManagerFactory.X509",
                "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl"
            )
            put(
                "TrustManagerFactory.X509",
                "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl"
            )
            null
        })
    }
}
