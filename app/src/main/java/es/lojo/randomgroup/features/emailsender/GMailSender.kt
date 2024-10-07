package es.lojo.randomgroup.features.emailsender

import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.Security
import java.util.Properties
import javax.activation.DataHandler
import javax.activation.DataSource
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

private const val DEFAULT_HOST = "smtp.gmail.com"
private const val CLASS_NAME = "GMailSender"

/**
 * Send email class
 * it has been inspirited by: https://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a
 */
class GMailSender(
    private val gmail: String,
    private val password: String
) : Authenticator() {
    private val session: Session

    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init $CLASS_NAME",
            suffix = LoggerTypes.FEATURE
        )

        val props = Properties()
        props.setProperty("mail.transport.protocol", "smtp")
        props.setProperty("mail.host", DEFAULT_HOST)
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.socketFactory.fallback"] = "false"
        props.setProperty("mail.smtp.quitwait", "false")

        session = Session.getDefaultInstance(props, this)
    }

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(gmail, password)
    }

    @Synchronized
    @Throws(Exception::class)
    fun sendMail(subject: String?, body: String, sender: String?, recipients: String) {
        try {
            val message = MimeMessage(session)
            val handler = DataHandler(ByteArrayDataSource(body.toByteArray(), "text/plain"))

            message.apply {
                this.sender = InternetAddress(sender)
                this.subject = subject
                dataHandler = handler
                if (recipients.indexOf(',') > 0) setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipients)
                )
                else setRecipient(Message.RecipientType.TO, InternetAddress(recipients))
                Transport.send(this)
            }

        } catch (e: Exception) {
            InfolojoLogger.log(CLASS_NAME, message = e.message.orEmpty())
        }
    }

    inner class ByteArrayDataSource(private val data: ByteArray, private val type: String?) : DataSource {
        @Throws(IOException::class)
        override fun getInputStream(): InputStream = ByteArrayInputStream(data)
        @Throws(IOException::class)
        override fun getOutputStream(): OutputStream = throw IOException("Not Supported")
        override fun getContentType(): String = type ?: "application/octet-stream"
        override fun getName(): String = "ByteArrayDataSource"
    }

    companion object {
        init {
            Security.addProvider(JSSEProvider())
        }
    }
}
