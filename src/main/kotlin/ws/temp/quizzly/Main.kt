package ws.temp.quizzly


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.ibm.icu.text.Transliterator
import org.slf4j.Logger
import org.wasabifx.wasabi.protocol.http.Request
import org.wasabifx.wasabi.protocol.http.Response


fun main(args: Array<String>) {
    Application()

    // todo: generate configure file
//    File("./").list().forEach { logger.info(it) }
}

fun Logger.toJson(obj: Any) {
    info("\n" + ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .writeValueAsString(obj))
}

fun Long.toHexString(): String {
    return java.lang.Long.toHexString(this)
}

fun Request.dumpParams(logger: Logger) {
    logger.info("-----------header-----------")
    rawHeaders.forEach { logger.info("${it.key}: ${it.value}") }
    logger.info("-----------header-----------")
    logger.info("-----------query params-----------")
    queryParams.forEach { logger.info("${it.key}: ${it.value}") }
    logger.info("-----------query params-----------")
    logger.info("-----------body params-----------")
    bodyParams.forEach { logger.info("${it.key}: ${it.value}") }
    logger.info("-----------body params-----------")
}

fun Response.sendJson(obj: Any?) {
    send(obj ?: "{}", "application/json")
}

/**
 * ICU4Jに渡すラッパーメソッド
 */
fun String.transliterate(id: String): String {
    return Transliterator.getInstance(id)?.transliterate(this) ?: ""
}

/**
 * Unicode正規化
 *
 * TODO カタカナからひらがなへの変換の必要性を考える
 */
fun String.normalizeText(): String {
    return this
            .transliterate("Any-NFKC")
            .transliterate("Katakana-Hiragana")
            .replace(Regex("[,\n]"), " ")
}

/**
 * テキストを区切り文字に従って分割する
 *
 *
 */
fun String.splitText(): List<String> {
    return this.split(Regex("[ ,\n]")).filter(String::isNotEmpty)
}
