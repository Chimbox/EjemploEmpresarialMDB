package beans;

import dominio.Persona;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author Alfonso Felix
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "resDestino")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class BeanMensaje implements MessageListener {

    public BeanMensaje() {
    }

    @Override
    public void onMessage(Message message) {

        if (message instanceof TextMessage) {
            try {
                TextMessage msg = (TextMessage) message;

                String texto = msg.getText();

                for (int i = 0; i < 10; i++) {
                    System.out.println("TIPO MENSAJE: Texto, contenido: " + texto);
                    Thread.sleep(1000);
                }

            } catch (JMSException ex) {
                Logger.getLogger(BeanMensaje.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(BeanMensaje.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (message instanceof ObjectMessage) {
            try {
                Object objetoMensaje=((ObjectMessage) message).getObject();
                if (objetoMensaje instanceof Persona) {
                    Persona persona = (Persona) objetoMensaje;

                    String nombre = persona.getNombre();
                    Date fechaReg = persona.getFechaRegistro();

                    for (int i = 0; i < 10; i++) {
                       System.out.println("TIPO MENSAJE: Objeto, tipo Persona. De nombre "+nombre+", registrado el: "+fechaReg);
                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
