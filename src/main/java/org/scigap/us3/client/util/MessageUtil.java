package org.scigap.us3.client.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.scigap.us3.message.Message;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class MessageUtil {
	 public static String extractMessage(String message, String tag) throws Exception {
		 XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
	  pullParserFactory.setNamespaceAware(true);
	  XmlPullParser pullParser = pullParserFactory.newPullParser();
	   pullParser.setInput(new StringReader(message));
        int eventType = 0;
        
        do {
            eventType = pullParser.next();

            if (eventType == XmlPullParser.START_TAG) {
                String tagName = pullParser.getName();
//                if (log.isTraceEnabled()) log.trace("start tagName: " + tagName);
                if (tag.equals(tagName)) {
                    do {
                    	eventType = pullParser.next();
                        if (eventType == XmlPullParser.TEXT) {
                        	message = pullParser.getText();
                        } else if (eventType == XmlPullParser.END_TAG) {
                            tagName = pullParser.getName();
                        }
                    } while (!((eventType == XmlPullParser.TEXT) || ((eventType == XmlPullParser.END_TAG) && (tag.equals(tagName)))));
               }
          }
        } while (eventType != XmlPullParser.END_DOCUMENT);
        return message;
        
    }
	public static String readRequestMessage(Message message){
		
		StringWriter sw = null;
		try {
			JAXBContext carContext = JAXBContext.newInstance(Message.class);
			Marshaller requestMarshaller = carContext.createMarshaller();
			sw = new StringWriter();
			requestMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					   new Boolean(true));
			requestMarshaller.marshal(message, sw);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new WebApplicationException(e);
		}
    return sw.toString();
	}
}
