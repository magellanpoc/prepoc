//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.21 at 06:11:30 PM IST 
//


package beer;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the beer package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: beer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetBeerRequest }
     * 
     */
    public GetBeerRequest createGetBeerRequest() {
        return new GetBeerRequest();
    }

    /**
     * Create an instance of {@link GetBeerResponse }
     * 
     */
    public GetBeerResponse createGetBeerResponse() {
        return new GetBeerResponse();
    }

    /**
     * Create an instance of {@link Beer }
     * 
     */
    public Beer createBeer() {
        return new Beer();
    }

}