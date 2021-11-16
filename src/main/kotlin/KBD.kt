import HAL.init
import HAL.clrBits
import HAL.isBit
import HAL.readBits
import HAL.setBits
import isel.leic.utils.Time

object KBD {
    const val NONE = '?'
    private const val timeout = 3000
    private const val MASK_KVAL = 0x10
    private const val MASK_K03 = 0x0F
    private const val MASK_KACK = 0x80
    private val KEYS2 = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '*', '0', '#')
    private val KEYS = charArrayOf('1', '4', '7', '*', '2', '5', '8', '0', '3', '6', '9', '#')

    fun main(args: Array<String>) {
        HAL.init()
        init()
        println(waitKey(timeout.toLong()))
        println(waitKey(timeout.toLong()))
        println(waitKey(timeout.toLong()))
    }

    // Inicia a classe
    fun init() {
        clrBits(0x00)
    }

    //Retorna quando a tecla for premida ou NONE após decorrido ‘timeout’ milisegundos.
    fun waitKey(timeout: Long): Char {
        val begin = Time.getTimeInMillis()
        var key: Char
        while (Time.getTimeInMillis() < begin + timeout) {
            key = getKey()
            if (key != NONE) return key
        }
        return NONE
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {
            if (isBit(MASK_KVAL)) {
                val key = KEYS[readBits(MASK_K03)]
                setBits(MASK_KACK)
                while (isBit(MASK_KVAL)) {
                }
                clrBits(MASK_KACK)
                return key
            }
            return NONE
    }
}