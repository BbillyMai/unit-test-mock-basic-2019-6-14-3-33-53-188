package cashregister;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class CashRegisterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    private void restoreStreams() {
        System.setOut((originalOut));
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {

        CashRegister cashRegister = new CashRegister(new Printer());
        Purchase purchase = new Purchase(new Item[]{new Item("Apple", 1.0)});

        cashRegister.process(purchase);

        assertEquals("Apple\t1.0\n", outContent.toString());
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {

        Purchase purchase = mock(Purchase.class);
        CashRegister cashRegister = new CashRegister(new Printer());
        when(purchase.asString()).thenReturn("apple\t1.0\n");

        cashRegister.process(purchase);

        assertEquals("apple\t1.0\n",outContent.toString());
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {

        CashRegister cashRegister = mock(CashRegister.class);
        Purchase purchase = mock(Purchase.class);

        cashRegister.process(purchase);

        verify(cashRegister).process(purchase)
    }

}
