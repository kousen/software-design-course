package edu.trincoll.solid.isp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PrinterTest {
    
    @Test
    void basicPrinterOnlyImplementsPrintable() {
        BasicPrinter printer = new BasicPrinter("HP LaserJet");
        
        assertAll(
            () -> assertThat(printer).isInstanceOf(Printable.class),
            () -> assertThat(printer).isNotInstanceOf(Scannable.class),
            () -> assertThat(printer).isNotInstanceOf(Faxable.class),
            () -> assertThat(printer.getName()).isEqualTo("HP LaserJet")
        );
    }
    
    @Test
    void multiFunctionPrinterImplementsAllInterfaces() {
        MultiFunctionPrinter printer = new MultiFunctionPrinter("Canon MX922");
        
        assertAll(
            () -> assertThat(printer).isInstanceOf(Printable.class),
            () -> assertThat(printer).isInstanceOf(Scannable.class),
            () -> assertThat(printer).isInstanceOf(Faxable.class),
            () -> assertThat(printer.getName()).isEqualTo("Canon MX922")
        );
    }
    
    @Test
    void printableInterfaceIsSegregated() {
        Printable[] printers = {
            new BasicPrinter("Simple Printer"),
            new MultiFunctionPrinter("Advanced Printer")
        };
        
        for (Printable printer : printers) {
            printer.print("Test document"); // Should not throw exception
        }
    }
    
    @Test
    void basicPrinterCanPrint() {
        BasicPrinter printer = new BasicPrinter("Epson WorkForce");
        
        assertThat(printer).isInstanceOf(Printable.class);
        printer.print("Test document"); // Should not throw exception
    }
    
    @Test
    void multiFunctionPrinterCanPrint() {
        MultiFunctionPrinter printer = new MultiFunctionPrinter("Brother MFC");
        
        assertThat(printer).isInstanceOf(Printable.class);
        printer.print("Test document"); // Should not throw exception
    }
    
    @Test
    void multiFunctionPrinterCanScan() {
        MultiFunctionPrinter printer = new MultiFunctionPrinter("Xerox WorkCentre");
        
        assertThat(printer).isInstanceOf(Scannable.class);
        String result = printer.scan();
        assertThat(result).contains("Scanned content from Xerox WorkCentre");
    }
    
    @Test
    void multiFunctionPrinterCanFax() {
        MultiFunctionPrinter printer = new MultiFunctionPrinter("Samsung SCX");
        
        assertThat(printer).isInstanceOf(Faxable.class);
        printer.fax("Fax document"); // Should not throw exception
    }
    
    @Test
    void scannableInterfaceIsSegregated() {
        Scannable scanner = new MultiFunctionPrinter("Scanner Device");
        
        String result = scanner.scan();
        assertThat(result).isNotNull();
    }
    
    @Test
    void faxableInterfaceIsSegregated() {
        Faxable faxMachine = new MultiFunctionPrinter("Fax Device");
        
        faxMachine.fax("Important document"); // Should not throw exception
    }
}