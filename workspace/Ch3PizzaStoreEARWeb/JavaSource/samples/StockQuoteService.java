/**
 * StockQuoteService.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * o0526.04 v62905175048
 */

package samples;

public interface StockQuoteService extends javax.xml.rpc.Service {
    public samples.StockQuote getStockQuote() throws javax.xml.rpc.ServiceException;

    public java.lang.String getStockQuoteAddress();

    public samples.StockQuote getStockQuote(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
