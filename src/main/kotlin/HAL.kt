import isel.leic.UsbPort
import isel.leic.utils.Time

// Virtualiza o acesso ao sistema UsbPort

object HAL {
    var data = 0

    fun main(args: Array<String>) {
        init()
        setBits(0xf0) //240
        Time.sleep(50)
        writeBits(0x0f, 12) //240+12=252
        Time.sleep(50)
        println(data)
        clrBits(0xff)
        println(data)
    }

    // Inicia a classe
    fun init() {
        data = 0x00
        out(data)
    }

    private fun `in`(): Int {
        return UsbPort.`in`()
    }

    // Retorna true se o bit tiver o valor lógico ‘1’
    fun isBit(mask: Int): Boolean {
        return readBits(mask) != 0x00
    }

    // Retorna os valores dos bits representados por mask presentes no UsbPort
    fun readBits(mask: Int): Int {
        return UsbPort.`in`() and mask
    }

    // Coloca os bits representados por mask no valor lógico ‘1’
    fun setBits(mask: Int) {
        data = data or mask
        out(data)
    }

    // Escreve nos bits representados por mask o valor de value
    fun writeBits(mask: Int, value: Int) {
        val temp = mask and value
        data = data and mask.inv() or temp
        data = data or temp
        out(data)
    }

    // Coloca os bits representados por mask no valor lógico ‘0’
    fun clrBits(mask: Int) {
        data = data and mask.inv()
        out(data)
    }

    //~data ou data?
    private fun out(data: Int) {
        UsbPort.out(data)
    }
}