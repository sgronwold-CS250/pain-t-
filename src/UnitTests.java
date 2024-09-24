import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTests {
    @Test
    public void testColor2Hex1() {
        int white = 0xFFFFFF;

        assertTrue(Eyedropper.color2HexString(white).equals("0xFFFFFF"));
    }

    @Test
    public void testColor2Hex2() {
        int yellow = 0xFFFF00;

        assertTrue(Eyedropper.color2HexString(yellow).equals("0xFFFF00"));
    }

    @Test
    public void testColor2Hex3() {
        int black = 0x000000;

        assertTrue(Eyedropper.color2HexString(black).equals("0x000000"));
    }
}
