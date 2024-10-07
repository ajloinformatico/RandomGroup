package es.lojo.randomgroup.features.emailsender

import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.data.models.EXPECTED
import es.lojo.randomgroup.data.models.EmailSenderModel
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
private const val SENDER = "rrandomgroup@gmail.com"
private const val PASSWORD = "**** **** **** ****"
/**
 * Send email class
 * it has been inspirited by: https://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a
 */
class GMailSender : Authenticator() {
    private val session: Session

    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init $CLASS_NAME",
            suffix = LoggerTypes.FEATURE
        )
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3")

        val props = Properties()
        props.setProperty("mail.transport.protocol", "smtp")
        props.setProperty("mail.host", DEFAULT_HOST)
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.socketFactory.fallback"] = "false"
        props["mail.smtp.quitwait"] = "false"
        props["mail.smtp.ssl.protocols"] = "TLSv1.2"

        session = Session.getDefaultInstance(props, this)
    }

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(SENDER, PASSWORD)
    }

    @Synchronized
    fun sendMail(senderModel: EmailSenderModel): EXPECTED {
        return try {
            val message = MimeMessage(session)
            val handler = DataHandler(ByteArrayDataSource(senderModel.body.toByteArray(), "text/plain"))
            val recipients = senderModel.recipients

            message.apply {
                this.sender = InternetAddress(SENDER)
                this.subject = senderModel.subject
                dataHandler = handler
                if (recipients.indexOf(',') > 0) setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipients)
                )
                else setRecipient(Message.RecipientType.TO, InternetAddress(recipients))
                Transport.send(this)
            }
            EXPECTED.SUCCESS
        } catch (e: Exception) {
            InfolojoLogger.log(CLASS_NAME, message = e.message.orEmpty())
            EXPECTED.FAILURE
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
